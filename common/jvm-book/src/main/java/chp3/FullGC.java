package chp3;

/**
 * Created by Jary on 2017/10/12 0012.
 */
public class FullGC {
    public static void main(String[] args) {
        //testPretenureSizeThreshold();
    }

    private static final int _1MB = 1*1024 * 1024;

    /**
     * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -Xloggc:data\gc\gclog -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     */
    public static void testPretenureSizeThreshold() {
        byte[] allocation;
        allocation = new byte[9 * _1MB];  //直接分配在老年代中

    }
}
