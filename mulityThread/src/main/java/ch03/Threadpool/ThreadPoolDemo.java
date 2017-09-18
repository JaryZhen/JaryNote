package ch03.Threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jary on 2017/8/16 0016.
 */
public class ThreadPoolDemo {

    static int dely = 2000;
    static int exe = 3000;

    public static void main(String[] args) {

        Mytask t = new Mytask();
/*
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.submit(t);
        }
        es.shutdown();*/

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
        //ses.scheduleWithFixedDelay(t, 0, dely, TimeUnit.MILLISECONDS);//相加
        ses.scheduleAtFixedRate(t,0,dely,TimeUnit.MILLISECONDS);//取最大值

    }

    public static class Mytask implements Runnable {

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() / 1000 + ": ThreadID:" + Thread.currentThread().getId());

            try {
                Thread.currentThread().sleep(exe);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
