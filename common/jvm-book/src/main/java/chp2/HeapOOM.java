package chp2;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * Created by Jary on 2017/10/11 0011.
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while (true) {
            Thread.sleep(0);
            list.add(new OOMObject());
        }
    }
}
