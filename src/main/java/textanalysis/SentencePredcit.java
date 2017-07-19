package textanalysis;

import java.util.List;

/**
 * Created by sssd on 2017/7/18.
 */
public class SentencePredcit {
    protected PredictModel predictModel;

    public void initTrain( int method, InputPath inputPath) throws Exception {
        predictModel =  PredictFactory.newInstance(method);
        predictModel.init(inputPath);
    }
    public List sensePredict(String sentecne)throws Exception{
        List preLabel = predictModel.predict(sentecne);
        return  preLabel;
    }

}
