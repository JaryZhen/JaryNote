package chp3;

/**
 * Created by Jary on 2017/10/12 0012.
 */
public class Age_Eden_Sur_Old {
    public static void main(String[] args) {
        testTenuringThreshold();
    }
    private static final int _1MB = 1024 * 1024;

    /**
     * VM������-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];  // ʲôʱ����������������XX:MaxTenuringThreshold����
        allocation2 = new byte[4 * _1MB];
        allocation3 = new byte[4 * _1MB];
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];
    }
}
