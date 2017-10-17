package chp3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jary on 2017/10/12 0012.
 */
public class MetaspaceSize {



    private static final int _1MB = 1*1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:MetaspaceSize=2m
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[9 * _1MB];  //直接分配在老年代中

    }
    public static void main(String[] args) {
        //testPretenureSizeThreshold();
    }
    //static String  base = "string";
    //public static void main(String[] args) {
    //    List<String> list = new ArrayList<String>();
    //    for (int i=0;i< Integer.MAX_VALUE;i++){
    //        String str = base + base;
    //        base = str;
    //        list.add(str.intern());
    //    }
    //}
}
