package demo.app.message;

/**
 * Created by Jary on 2016/8/18.
 */
public class WordCount {
    private String world;
    private Integer count;

    public WordCount(String world ,Integer count){
        this.world = world;
        this.count = count;
    }

    public String getWorld() {
        return world;
    }

    public Integer getCount() {
        return count;
    }
}
