package textanalysis;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sssd on 2017/7/18.
 */
public class SentencePredcitTest {

    @Test
    public void outPrint()throws Exception{

        SentencePredcit sentencePredcit = new SentencePredcit();
        InputPath inputPath = new InputPath();

        //1，采用的是机器学习
        int method = 1;
        String negPath = "C:\\Users\\sssd\\Desktop\\data\\train\\negdata.txt";
        String posPath = "C:\\Users\\sssd\\Desktop\\data\\train\\posdata.txt";
        inputPath.setNegPATH(negPath);
        inputPath.setPosPath(posPath);
        sentencePredcit.initTrain(method,inputPath);
        sentencePredcit.sensePredict("酒店的位置很方便，小套房的房间比较宽敞和舒适，住酒店停车过夜要收费40元。",1);

        //2,采用的是词典的方式进行语义分析
/*        int method = 2;
        String negPath = "C:\\Users\\sssd\\Desktop\\data\\NTUSD_negative_simplified.tx";
        String posPath = "C:\\Users\\sssd\\Desktop\\data\\NTUSD_positive_simplified.txt";
        inputPath.setNegPATH(negPath);
        inputPath.setPosPath(posPath);
        sentencePredcit.initTrain(method,inputPath);
        sentencePredcit.sensePredict("酒店的位置很方便，小套房的房间比较宽敞和舒适，住酒店停车过夜要收费40元。",1);*/
    }
}