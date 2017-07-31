package textanalysis;

/**
 * Created by sssd on 2017/7/18.
 */
public class PredictFactory {

    public static PredictModel newInstance( int method){
        if ( method ==1 ){
            return new MLlibPredict();
        }else if (method == 2) {
            return new WordPredict();
        }else if (method ==3){
            return new WordScorePredict();
        }else if (method == 4){
            return new WordScordPredict2();
        }else{
            return new WordScordPredict3();
        }
    }
}
