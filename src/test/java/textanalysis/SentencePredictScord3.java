package textanalysis;

import com.google.common.io.Files;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sssd on 2017/7/31.
 */
public class SentencePredictScord3 {

    @Test
    public void outPrint()throws Exception{
        SentencePredcit sentencePredcit = new SentencePredcit();
        InputPath inputPath = new InputPath();
        inputPath.setPosPath("F:\\ecplisework\\Sentiment_dict\\emotion_dict\\pos_all_dict.txt");
        inputPath.setNegPATH("F:\\ecplisework\\Sentiment_dict\\emotion_dict\\neg_all_dict.txt");
        inputPath.setStopWordPath("F:\\ecplisework\\Sentiment_dict\\emotion_dict\\stop_words.txt");
        inputPath.setLevelPath("F:\\ecplisework\\Sentiment_dict\\emotion_dict\\degreeWords.txt");
        int method = 5;
        sentencePredcit.initTrain(method,inputPath);

/*        List preLabel =  sentencePredcit.sensePredict("商务大床房，房间很大，床有2M宽，整体感觉经济实惠不错!");
        System.out.println(preLabel.get(0));*/

        List<String> lines = Files.readLines(new File("C:\\Users\\sssd\\Desktop\\data\\train\\posdata.txt"), Charset.forName("UTF-8"));
        int posNum = 1;
        int posNumPre = 0;
        List pres = new ArrayList();
        long posstartT = System.currentTimeMillis();
        for (String line : lines) {
            List preLabel = sentencePredcit.sensePredict(line);
            pres.add(preLabel.get(0));
//            System.out.println(posNum + " 预测值为："+preLabel.get(0));
            posNum++;
            if ((Double)preLabel.get(0) > 0.0){
                posNumPre++;
            }
        }
        long posendT = System.currentTimeMillis();
        System.out.println("---------------------");
        System.out.println("正面文本准确率为："+ (double)posNumPre/(posNum-1));
        System.out.println("正面文本最大值为："+Collections.max(pres));
        System.out.println("正面文本最小值为："+Collections.min(pres));
        System.out.println(String.format("%d 条文本耗时 %d ms, 平均： %f ms/条", lines.size(), (posendT - posstartT), ((posendT - posstartT) / (float)posNum)));
        List<String> negLines = Files.readLines(new File("C:\\Users\\sssd\\Desktop\\data\\train\\negdata.txt"), Charset.forName("UTF-8"));
        int negNum = 1;
        int negNumPre = 0;
        List negPres = new ArrayList();
        long negstartT = System.currentTimeMillis();
        for (String line : negLines) {
            List preLabe2 = sentencePredcit.sensePredict(line);
            negPres.add(preLabe2.get(0));
//            System.out.println(negNum + " 预测值为："+preLabel.get(0));
            negNum++;
            if ((Double)preLabe2.get(0) < -0.0){
                negNumPre++;
            }
        }
        long negendT = System.currentTimeMillis();
        System.out.println("反面文本准确率为："+ (double)negNumPre/(negNum-1));
        System.out.println("反面文本最大值为："+Collections.max(negPres));
        System.out.println("反面文本最小值为："+ Collections.min(negPres));
        System.out.println(String.format("%d 条文本耗时 %d ms, 平均： %f ms/条", negLines.size(), (negendT - negstartT), ((negendT - negstartT) / (float)negNum)));

        System.out.println("---------------------");
        System.out.println("预测总的文本时间为：" +(negendT- posstartT));
        System.out.println("平均预测一个文本时间为：" +(negendT- posstartT)/(posNum+negNum-2));
    }
}
