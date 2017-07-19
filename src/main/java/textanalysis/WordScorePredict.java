package textanalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sssd on 2017/7/19.
 */
public class WordScorePredict implements PredictModel {

    protected TextAnalysis textAnalysis = new TextAnalysis();
    protected Map<String,Double>  textMap = new HashMap<String,Double>();

    //读入路径返回词典中的数据
    public void init(InputPath inputPath) throws Exception {
//        String path = "C:\\Users\\sssd\\Desktop\\data\\BosonNLP_sentiment_score.txt";
        String path = inputPath.getPath();
         textMap = textAnalysis.readText2Map(path);
    }

    public List predict(String sentence) throws Exception {
        List preLsit = new ArrayList();
        String[] textSplits = TextAnalysis.getSplitWord(sentence).split(" ");
        Double sentenceWeight = textAnalysis.wordScoreCompare(textSplits,textMap);
        preLsit.add(sentenceWeight);
        return preLsit;
    }

}
