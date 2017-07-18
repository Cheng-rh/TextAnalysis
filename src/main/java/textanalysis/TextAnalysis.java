package textanalysis;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;

import java.io.*;
import java.util.*;

/**
 * Created by hui on 2017/7/16.
 */
public class TextAnalysis {

    private static String stopWordTable = "src/main/resources/中文停用词库2.txt";

    /**
     * 将文本和词典中的极性词进行对比
     * @param text  文本，
     * @param set   中文极性词典
     * @return      文本中包含极性此的个数
     */
    public List textCompare(String text , Set set) {
        String[] splitWords = text.split("\n");
        List lists = new ArrayList();
        for (int i = 0; i < splitWords.length; i++) {
            int num = 0;
            String[] splitLines = splitWords[i].split(" ");
            for (int j = 0; j < splitLines.length; j++) {
                if (set.contains(splitLines[j])) {
                    num++;
                }
            }
            lists.add(num);
        }
        return lists;
    }

    /**
     * 读取文件，返回Set数据类型
     * @param path
     * @return 返回Set数据类型
     * @throws Exception
     */
        public Set<String>  readSet(String path)throws Exception{
            BufferedReader negWordRead = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));
            Set set = new HashSet<String>();
            String eleWord = null;
            for(; (eleWord = negWordRead.readLine()) != null;) {
                set.add(eleWord);
            }
            return set;
        }

    /**
     * 将数据转换成libsvm数据格式
     * @param savapath
     * @param data
     * @throws Exception
     */
    public void data2Svm(String savapath,List<List> data)throws Exception{
        File file = new File(savapath);
        FileWriter out = new FileWriter(file);
        StringBuffer dataOut = new StringBuffer();
        for(int i = 0; i < data.size(); i++){
            for (int j =0; j< data.get(i).size(); j++){
                if(j == 0){
                    dataOut.append(data.get(i).get(j).toString()).append(" ");
                }else{
                    if(Double.parseDouble(data.get(i).get(j).toString()) == 0.0){
                        continue;
                    }else{
                        dataOut.append(j).append(":").append(data.get(i).get(j).toString()).append(" ");
                    }
                }
            }
            dataOut.deleteCharAt(dataOut.length()-1).append("\n");
        }
        dataOut.deleteCharAt(dataOut.length()-1);
        out.write(dataOut.toString());
        out.close();
    }

    /**
     * 数据补全（缺失的补0）
     * @param oldData
     * @return  补全以后的数据
     */
    public List<List> dataComple (List<List> oldData){

        int maxclo = 0;
        for(int i = 0; i < oldData.size(); i++){
            int temp = oldData.get(i).size();
            if (temp > maxclo){
                maxclo = temp;
            }
        }

        for (int i = 0; i<oldData.size();i++){
            for (int j =0; j < maxclo-1 ; j++){
                if( j > oldData.get(i).size()-2){
                    oldData.get(i).add(0.0);
                }
            }
        }
        return  oldData;
    }

    public List<List> dataComple (List<List> oldData,int maxnum ){
        int maxclo = maxnum;
        for (int i = 0; i<oldData.size();i++){
            for (int j =0; j < maxclo-1 ; j++){
                if( j > oldData.get(i).size()-2){
                    oldData.get(i).add(0.0);
                }
            }
        }
        return  oldData;
    }


    /**
     * 将文本转换成向量
     * @param text
     * @return  返回文本向量
     */
    public List text2Vec(Map<Integer,String> text,int yuzhi)throws Exception{

        Word2VEC vec= new Word2VEC();
        vec.loadJavaModel("src/main/resources/vector.mod");
        List<List> allData = new ArrayList<List>();
        Iterator iterator = text.keySet().iterator();
        while (iterator.hasNext()){
            Object key = iterator.next();
            String[] contents = text.get(key).split("\n");
            for( int i = 0;i<contents.length;i++ ){
                String[] lines = contents[i].split(" ");
                if( lines.length > yuzhi){
                    List lineVecot = TextAnalysis.lines2Vec(Double.parseDouble(key.toString()),lines,vec);
                    allData.add(lineVecot);
                }else{
                    continue;
                }
            }
        }
        return  allData;
    }

    /**
     * 将文本每行转换成行向量
     * @param lines
     * @return   文本的行向量
     */
    public static List lines2Vec(double key,String[] lines,Word2VEC vec) throws Exception{
        List<Double> elem = new ArrayList<Double>();
        elem.add(key);
        for(int i = 0; i < lines.length;i++){
            double sum = 0;
            float[] wordVectors = vec.getWordVector(lines[i]);
            if(wordVectors != null){
                for( int ii = 0; ii < wordVectors.length; ii++ ){
                    sum += wordVectors[ii];
                }
            }else{
                sum = 0;
            }
            elem.add(sum);
        }
        return elem;
    }



    /**
     * 训练word2vec模型，保存
     * @param wordPath
     * @param modelPath
     * @throws Exception
     */
    public void initWord2Vec(String wordPath,String modelPath)throws Exception{
        Learn learn = new Learn();
        learn.learnFile(new File(wordPath));
        learn.saveModel(new File(modelPath));
    }


    /**
     * 读取整个文本
     * @param path
     * @return 带有标签、分词后的文本行
     */
    public Map<Integer,String> readText(String path)throws Exception{
        Map<Integer,String> text = new HashMap <Integer, String>();
        StringBuffer textStringBuffer = new StringBuffer();
        BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(path),"gbk"));
        String line = null;
        while( (line=read.readLine()) != null ){
            textStringBuffer.append(TextAnalysis.getSplitWord(line)).append("\n");
        }
        textStringBuffer.deleteCharAt(textStringBuffer.length()-1);
        if(path.contains("neg")){
            text.put(-1,textStringBuffer.toString());
        }else if(path.contains("pos")){
            text.put(1,textStringBuffer.toString());
        }
        return text;
    }


    /**
     * 按行读取文本进行分词，并去掉停留词
     * @param textLine
     * @return 分词之后的文本
     * @throws Exception
     */
    public static String getSplitWord(String textLine)throws Exception{
        String sentence = textLine;
        BufferedReader StopWordFileBr = new BufferedReader(new InputStreamReader(new FileInputStream(new File(stopWordTable)),"gbk"));
        Set stopWordSet = new HashSet<String>(); //用来存放停用词的集合
        String stopWord =  null;
        for(; (stopWord = StopWordFileBr.readLine()) != null;) {
            stopWordSet.add(stopWord);
        }

        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> tokens = segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX);
        StringBuffer tokenizerResult = new StringBuffer();
//        List<String> tokenizerResult = new ArrayList<String>();
        for (SegToken token : tokens) {
            if (!stopWordSet.contains(token.word)  ){
                if (token.word != null || token.word != " "){
                    tokenizerResult.append(token.word).append(" ");
//                    tokenizerResult.add(token.word);
                }
            }
        }
        tokenizerResult.deleteCharAt(tokenizerResult.length()-1);
        return tokenizerResult.toString();
    }

}
