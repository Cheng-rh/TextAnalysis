package textanalysis;

/**
 * Created by sssd on 2017/7/18.
 */
public interface PredictModel {
    public void init(InputPath inputPath)throws Exception;
    public void predict(String sentence ,int label) throws Exception;
}
