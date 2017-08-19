package ch03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jary on 2017/8/16 0016.
 */
public class ThreadPoolDemo {
    public static void main(String[] args) {

        Mytask t = new Mytask();
/*
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.submit(t);
        }
        es.shutdown();*/

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
            ses.scheduleWithFixedDelay(t,3,2,TimeUnit.SECONDS);

    }

    public static class Mytask implements Runnable{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+ ": ThreadID:"+ Thread.currentThread().getId());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
