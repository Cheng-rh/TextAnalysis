package textanalysis;

/**
 * Created by sssd on 2017/7/18.
 */
public class PredictFactory {

    public static PredictModel newInstance( int mmethod){
        if ( mmethod ==1 ){
            return new MLlibPredict();
        }else {
            return new WordPredict();
        }
    }
}
