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
        negSet = textAnalysis.readSet(negWordPath);
        posSet = textAnalysis.readSet(posWOrdPath);
    }

    public void predict(String sentence, int label) throws Exception {

        String tetxSplits = TextAnalysis.getSplitWord(sentence);
        List negLists = textAnalysis.textCompare(tetxSplits,negSet);
        List psoLists = textAnalysis.textCompare(tetxSplits,posSet);

        List predictList = new ArrayList();
        for(int i =0;i<negLists.size();i++){
            if(Integer.parseInt(negLists.get(i).toString()) > Integer.parseInt(psoLists.get(i).toString())){
                predictList.add("N");
            }else if(Integer.parseInt(negLists.get(i).toString()) < Integer.parseInt(psoLists.get(i).toString())){
                predictList.add("P");
            }else {
                predictList.add("C");
            }
        }

        System.out.println("------预测："+predictList.get(0).toString());
    }
}
