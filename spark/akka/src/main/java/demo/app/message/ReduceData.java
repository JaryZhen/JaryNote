package demo.app.message;

import java.util.HashMap;

/**
 * Created by Jary on 2016/8/18.
 */
public class ReduceData {
    public HashMap<String ,Integer> reduceDataMap;
    public ReduceData(HashMap<String,Integer> reduceDataMap){
        this.reduceDataMap= reduceDataMap;
    }

    public HashMap<String ,Integer> getReduceDataMap(){
        return reduceDataMap;
    }

}
