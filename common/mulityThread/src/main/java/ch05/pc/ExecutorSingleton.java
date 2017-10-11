package ch05.pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class ExecutorSingleton {
    private ExecutorSingleton(){};

    private static ExecutorService es = null;

    public static synchronized  ExecutorService getInstance(int n){
        if (es==null)
            es = Executors.newFixedThreadPool(n);
        return es;
    }
}
