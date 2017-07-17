package textanalysis;

/**
 * Created by sssd on 2017/7/17.
 */
public class Sentence {
    protected  String   title;
    protected  String   content;

    public Sentence(){

    }

    public Sentence(String title,String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
