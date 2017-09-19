package ch03.Threadpool;

import java.util.concurrent.ThreadFactory;

/**
 * Created by Jary on 2017/9/19 0019.
 */
public class MyThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setDaemon(true);
        System.out.println("create " + t);
        return t;

    }
}
