package demo.app.message;

import java.util.List;

/**
 * Created by Jary on 2016/8/18.
 */
public class MapData {

    public List<WordCount> wordCountList;

    public MapData(List<WordCount> wordCounts){
        this.wordCountList = wordCounts;
    }

    public List<WordCount> getWordCountList(){
        return wordCountList;
    }
}
