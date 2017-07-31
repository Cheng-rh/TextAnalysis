package textanalysis;

import com.google.common.io.Files;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by sssd on 2017/7/31.
 */
public class WordScordPredict3  implements PredictModel{
    protected TextAnalysis textAnalysis = new TextAnalysis();
    protected List posList;
    protected List negList;
    protected List stopWordList;
    protected Map<String,Double> degreeDict;
    protected String regEx="：|。|！|；|，|（|）";

    public void init(InputPath inputPath) throws Exception {

        String posPath = inputPath.getPosPath();
        String negPath = inputPath.getNegPATH();
        String stopPath = inputPath.getStopWordPath();
        String degreePath = inputPath.getLevelPath();
        posList = Files.readLines(new File(posPath), Charset.forName("UTF-8"));
        negList = Files.readLines(new File(negPath), Charset.forName("UTF-8"));
        stopWordList = Files.readLines(new File(stopPath), Charset.forName("UTF-8"));
        degreeDict = textAnalysis.readText2Map(degreePath);
        System.out.println("初始化完成！");
    }

    public List predict(String text) throws Exception {
        Pattern p = Pattern.compile(regEx);
        String[] sentences = p.split(text);
        List prelist = new ArrayList();
        double poscounts = 0.0;
        double negcounts = 0.0;
        //对单个语句进行分析
        for(int i=0; i<sentences.length; i++ ){
            int wordLocation = 0;
            double poscount = 0;
            double negcount = 0;
            String[] splitWords = TextAnalysis.getSplitWord(sentences[i]).split(" ");

            // 对单个分词进行分析
            for( int j=0; j<splitWords.length; j++){
                if (posList.contains(splitWords[j])){
                    poscount += 1;
                    for( int k = wordLocation; k < j; k++ ){
                        if(degreeDict.keySet().contains(splitWords[k])){
                            poscount = poscount *degreeDict.get(splitWords[k]);
                        }
                        wordLocation = j+1;
                    }
                }else if(negList.contains(splitWords[j])){
                    negcount +=1;
                    for(int k = wordLocation; k < j; k++ ){
                        if(degreeDict.keySet().contains(splitWords[k])){
                            negcount = negcount * degreeDict.get(splitWords[k]);
                        }
                        wordLocation = j+1;
                    }
                }else if(splitWords[j] == "" || splitWords[j] =="!"){
                    for (int k = splitWords.length-2; k >=0; k-- ){
                        if(posList.contains(splitWords[k])){
                            poscount += 2;
                            break;
                        }else if(negList.contains(splitWords[k])){
                            negcount +=2;
                            break;
                        }
                    }
                }
            }
            poscounts = poscounts + poscount;
            negcounts = negcounts + negcount ;
        }
        prelist.add(poscounts-negcounts);
        return prelist;
    }
}
