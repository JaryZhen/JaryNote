package time;

import java.util.concurrent.TimeUnit;

/**
 * Created by Jary on 2017/12/28 0028.
 */
public class nanotime {

    public static void main(String[] args) {
        long took = System.nanoTime();
        System.out.format("Elapsed time: %d ms", TimeUnit.NANOSECONDS.toMillis(took));

    }
}
