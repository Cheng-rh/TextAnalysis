package textanalysis;

import java.util.List;

/**
 * Created by sssd on 2017/7/18.
 */
public interface PredictModel {
    public void init(InputPath inputPath)throws Exception;
    public List predict(String sentence ) throws Exception;
}
