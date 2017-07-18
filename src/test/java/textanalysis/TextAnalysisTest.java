package textanalysis;

import org.junit.Test;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by hui on 2017/7/16.
 */
public class TextAnalysisTest {

    @Test
    public void outPrint() throws Exception {
        TextAnalysis textAnalysis = new TextAnalysis();

        // 读取正，负文档
        String negPath = "C:\\Users\\sssd\\Desktop\\data\\train\\negdata2.txt";
        String posPath = "C:\\Users\\sssd\\Desktop\\data\\train\\posdata2.txt";
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

        //训练word2vec,并利用word2vec 转换文本为向量
        textAnalysis.initWord2Vec(splitWordPath,word2vecPath);  //训练word2vec
        List<List> wordDatas = textAnalysis.text2Vec(textMap,4); //将文本转换成向量

        //将自己的数据集转换成libsvm的数据结构
        List<List> allDatas = textAnalysis.dataComple(wordDatas);  // 数据补全
        String savaPath = "C:\\Users\\sssd\\Desktop\\data\\train\\libsvmtrain.txt";
        textAnalysis.data2Svm(savaPath,allDatas);


        //模型训练及预测
        String[] arg = { "C:\\Users\\sssd\\Desktop\\data\\train\\libsvmtrain.txt",
                "C:\\Users\\sssd\\Desktop\\data\\train\\model.txt" };
        String[] parg = { "C:\\Users\\sssd\\Desktop\\data\\train\\libsvmtrain.txt",
                "C:\\Users\\sssd\\Desktop\\data\\train\\model.txt",
                "C:\\Users\\sssd\\Desktop\\data\\train\\predict.txt" };
        System.out.println("........SVM运行开始..........");
        long start=System.currentTimeMillis();
        svm_train.main(arg); //训练
        System.out.println("用时:"+(System.currentTimeMillis()-start));
        //预测
        svm_predict.main(parg);


        //读取json文件
        String jsonPath = "C:\\Users\\sssd\\Desktop\\data\\example.json";
        Map<Integer,String> testText= ReadJsonFile.readJsonFile(jsonPath);
        System.out.println("-------Json解析数据显示-------");
        System.out.println(testText.get(1).toString());
        System.out.println(" -----开始转换向量------");
        List<List> testWords = textAnalysis.text2Vec(testText,4);
        System.out.println(testWords.get(0).get(0)+"----"+testWords.get(0).get(1));
        List<List> allTestData = textAnalysis.dataComple(testWords,wordDatas.get(0).size());






        //存放二维向量
/*        File file = new File("C:\\Users\\hui\\Desktop\\data\\train\\train.txt");
        FileWriter out = new FileWriter(file);
        for(int i = 0; i < wordDatas.size(); i++){
            for (int j =0; j< wordDatas.get(i).size(); j++){
                out.write(wordDatas.get(i).get(j).toString()+" ");
            }
            out.write("\n");
        }
        out.close();*/
    }

}