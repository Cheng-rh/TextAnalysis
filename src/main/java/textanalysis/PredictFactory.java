package textanalysis;

/**
 * Created by sssd on 2017/7/18.
 */
public class PredictFactory {

    public static PredictModel newInstance( int mmethod){
        if ( mmethod ==1 ){
            return new MLlibPredict();
        }else if (mmethod == 2) {
            return new WordPredict();
        }else if (mmethod ==3){
            return new WordScorePredict();
        }else{
            return new WordScordPredict2();
        }
    }
}
