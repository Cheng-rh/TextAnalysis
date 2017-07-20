package textanalysis;

/**
 * Created by sssd on 2017/7/18.
 */
public class InputPath {


    public String path;      // word-score 的路径(wordScore)

    public String negPATH;   //neg 的路径
    public String posPath;   //pos 的路径

    public String emotionPath;
    public String denyPath;
    public String levelPath;

    public String getEmotionPath() {
        return emotionPath;
    }

    public void setEmotionPath(String emotionPath) {
        this.emotionPath = emotionPath;
    }

    public String getDenyPath() {
        return denyPath;
    }

    public void setDenyPath(String denyPath) {
        this.denyPath = denyPath;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getNegPATH() {
        return negPATH;
    }

    public void setNegPATH(String negPATH) {
        this.negPATH = negPATH;
    }

    public String getPosPath() {
        return posPath;
    }

    public void setPosPath(String posPath) {
        this.posPath = posPath;
    }
}
