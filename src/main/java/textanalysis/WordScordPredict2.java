package textanalysis;

import com.google.common.io.Files;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by sssd on 2017/7/20.
 */
public class WordScordPredict2 implements PredictModel {
    protected TextAnalysis textAnalysis = new TextAnalysis();
    protected Map<String,Double> senDict;
    protected List notList;
    protected Map<String,Double> degreeDict;

    public void init(InputPath inputPath) throws Exception {

        String emtionPath = inputPath.getEmotionPath();
        String denyPath = inputPath.getDenyPath();
        String levelPath = inputPath.getLevelPath();

        senDict = textAnalysis.readText2Map(emtionPath);
        notList = Files.readLines(new File(denyPath), Charset.forName("UTF-8"));    //返回的是List数组
        degreeDict = textAnalysis.readText2Map(levelPath);

    }

    public List predict(String text) throws Exception {

        List preLab  = new ArrayList();
        Double sum = 0.0;
        String[] sentences = text.split("。");
        for (String sentence: sentences){
            System.out.println(sentence);
            Map<Integer,Double> senWord = new LinkedHashMap<Integer, Double>();
            Map<Integer,Double> notWord = new LinkedHashMap<Integer, Double>();
            Map<Integer,Double> degreeWord = new LinkedHashMap<Integer, Double>();
            String[] splitSentence = TextAnalysis.getSplitWord(sentence).split(" ");

            //将句子中的各类分词分别存储并记录其位置
            for(int i = 0; i<splitSentence.length ; i++){
                if( senDict.keySet().contains(splitSentence[i]) && !notList.contains(splitSentence[i]) && !degreeDict.keySet().contains(splitSentence[i]) ){
                    // 权重/句子长度，
                    senWord.put(i,senDict.get(splitSentence[i]));
                }else if (notList.contains(splitSentence[i]) && !degreeDict.keySet().contains(splitSentence[i])){
                    notWord.put(i,-1.0);
                }else if(degreeDict.keySet().contains(splitSentence[i])){
                    degreeWord.put(i,degreeDict.get(splitSentence[i]));
                }
            }

            //情感聚合
            Double score = textAnalysis.scoreSent(senWord,notWord,degreeWord,splitSentence);
            sum = sum +score;
        }
        double predict = sum/sentences.length;
        preLab.add(predict);
        return preLab;
    }
}
