import org.junit.Test;
import textanalysis.ReadJsonFile;
import textanalysis.TextAnalysis;
import textanalysis.TextAnalysis2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by sssd on 2017/7/17.
 */
public class TextAnalysisTest2{
    @Test
    public void main()throws  Exception{

        //读取json文本，返回带标签的文本
        StringBuffer jsonBuffer = new StringBuffer();
        TextAnalysis2 textAnalysis2 = new TextAnalysis2();
        String jsonPath = "C:\\Users\\sssd\\Desktop\\data\\example.json";
        Map<Integer,String> testText= ReadJsonFile.readJsonFile(jsonPath);

        //将json文本分词，
        String[] jsonTexts = testText.get(1).toString().split("\n");
        for(int i = 0 ; i < jsonTexts.length; i++){
            jsonBuffer.append(TextAnalysis2.getSplitWord(jsonTexts[i])).append("\n");
        }
        jsonBuffer.deleteCharAt(jsonBuffer.length()-1);

        //加载积极和消极的词语
        String negWordPath  =  "C:\\Users\\sssd\\Desktop\\data\\NTUSD_negative_simplified.txt";
        String posWOrdPath = "C:\\Users\\sssd\\Desktop\\data\\NTUSD_positive_simplified.txt";
        Set negSet = textAnalysis2.readSet(negWordPath);
        Set posSet = textAnalysis2.readSet(posWOrdPath);

        // 分别和中文情感词进行对比
        List negList = textAnalysis2.textCompare(jsonBuffer,negSet);
        List posList = textAnalysis2.textCompare(jsonBuffer,posSet);

        List predictList = new ArrayList();
        for(int i =0;i<negList.size();i++){
            if(Integer.parseInt(negList.get(i).toString()) > Integer.parseInt(posList.get(i).toString())){
                predictList.add("N");
            }else if(Integer.parseInt(negList.get(i).toString()) < Integer.parseInt(posList.get(i).toString())){
                predictList.add("P");
            }else {
                predictList.add("C");
            }
        }

        System.out.println(predictList);
    }

}
