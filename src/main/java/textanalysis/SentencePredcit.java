package textanalysis;

/**
 * Created by sssd on 2017/7/18.
 */
public class SentencePredcit {
    protected PredictModel predictModel;

    public void initTrain( int method,InputPath inputPath) throws Exception {
        predictModel =  PredictFactory.newInstance(method);
        predictModel.init(inputPath);
    }
    public void sensePredict(String sentecne,int label)throws Exception{
        predictModel.predict(sentecne,label);
    }

}
