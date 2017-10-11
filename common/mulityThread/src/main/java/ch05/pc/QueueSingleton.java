package ch05.pc;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class QueueSingleton {

    private  QueueSingleton(){
        System.out.println("creating queue");
    }

    private static BlockingDeque<PCData>  queue = null;
    public static synchronized BlockingDeque<PCData> getInstance(){
            if (queue ==null)
                queue = new LinkedBlockingDeque<PCData>(10);

        return queue;
    }

}
