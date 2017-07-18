package textanalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by sssd on 2017/7/18.
 */
public class MLlibPredict implements PredictModel {

    protected  TextAnalysis textAnalysis = new TextAnalysis();

    public void init(InputPath inputPath)throws Exception {

        //读取文本，返回map<标签，文本>
        String negPath = inputPath.getNegPATH();
        String posPath = inputPath.getPosPath();
        Map textMap = textAnalysis.readText(negPath);
        Map posTextMap = textAnalysis.readText(posPath);
        textMap.putAll(posTextMap);


        //存放分词后的文档用来训练word2vec
        String splitWordPath ="src/main/resources/tokenizerResult.txt";
        String word2vecPath ="src/main/resources/vector.mod";
        StringBuffer textCombine = new StringBuffer();
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(splitWordPath)));
        Iterator iterator=textMap.keySet().iterator();
        while(iterator.hasNext()){
            Object key=iterator.next();
            textCombine.append(textMap.get(key).toString()).append("\n");
        }
        textCombine.deleteCharAt(textCombine.length()-1);
        bw.write(textCombine.toString());
        bw.close();
        textAnalysis.initWord2Vec(splitWordPath,word2vecPath);  //训练word2vec
        List<List> wordDatas = textAnalysis.text2Vec(textMap,2); //将文本转换成向量(50允许文本的长度)
        List<List> allDatas = textAnalysis.dataComple(wordDatas);  // 数据补全

        //保存成svm的数据类型
        String savaTrainPath = "src/main/resources/libsvmtrain.txt";
        String savaModelPath = "src/main/resources/model.txt";
        textAnalysis.data2Svm(savaTrainPath,allDatas);
        //模型训练及预测
        String[] arg = { savaTrainPath,savaModelPath };
        svm_train.main(arg);
    }

    public void predict(String sentence ,int label)throws Exception {
        String tetxSplits = TextAnalysis.getSplitWord(sentence);
        HashMap<Integer, String> senMap = new HashMap<Integer, String>();
        senMap.put(label,tetxSplits);
        List<List> wordDatas = textAnalysis.text2Vec(senMap,2);
        List<List> allDatas = textAnalysis.dataComple(wordDatas,wordDatas.get(0).size());  // 数据补全
        String saveTestPath = "src/main/resources/libsvmtest.txt";
        String savePredictPath = "src/main/resources/libsvmpredict.txt";
        textAnalysis.data2Svm(saveTestPath,allDatas);
        String savaModelPath = "src/main/resources/model.txt";
        String[] parg = {saveTestPath,savaModelPath,savePredictPath};
        svm_predict.main(parg);
//        Double predict = svm_predict.main(parg);
    }
}
