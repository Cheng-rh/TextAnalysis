package textanalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sssd on 2017/7/18.
 */
public class WordPredict implements PredictModel {

    protected TextAnalysis textAnalysis = new TextAnalysis();
    protected Set negSet;
    protected Set posSet;

    public void init(InputPath inputPath) throws Exception {
        String negWordPath  =  "C:\\Users\\sssd\\Desktop\\data\\NTUSD_negative_simplified.txt";
        String posWOrdPath = "C:\\Users\\sssd\\Desktop\\data\\NTUSD_positive_simplified.txt";
//        String negWordPath = inputPath.getNegPATH();
//        String posWOrdPath = inputPath.getPosPath();
        negSet = textAnalysis.readSet(negWordPath);
        posSet = textAnalysis.readSet(posWOrdPath);
    }

    public List predict(String sentence) throws Exception {
        String tetxSplits = TextAnalysis.getSplitWord(sentence);

        // 句子在负面词的相关性
        List psoLists = textAnalysis.textCompare(tetxSplits, posSet);

        // 句子在负面词的相关性
        List negLists = textAnalysis.textCompare(tetxSplits, negSet);

        List predictList = new ArrayList();
        for(int i =0; i < negLists.size(); i++){
//            predictList.add((Integer) negLists.get(i) - (Integer)psoLists.get(i));
            if((Integer) negLists.get(i) > (Integer)psoLists.get(i)){
                predictList.add(1);
            } else if((Integer) negLists.get(i) < (Integer)psoLists.get(i)){
                predictList.add(-1);
            } else {
                predictList.add(0);
            }
        }
        return predictList;
    }
}
