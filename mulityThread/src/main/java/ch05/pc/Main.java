package ch05.pc;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Jary on 2017/8/19 0019.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingDeque blockingDeque = QueueSingleton.getInstance();

        Producer p1 = new Producer(blockingDeque);
        //Producer p2 = new Producer(blockingDeque);
        //Producer p3 = new Producer(blockingDeque);

        Consumer c1 = new Consumer(blockingDeque);
        //Consumer c2 = new Consumer(blockingDeque);
        //Consumer c3 = new Consumer(blockingDeque);

        ExecutorService  es = ExecutorSingleton.getInstance(5);

        es.execute(p1);
        //es.execute(p2);
        //es.execute(p3);
        es.execute(c1);
        //es.execute(c2);
        //es.execute(c3);
        Thread.sleep(100*1000);
        p1.stop();
        //p2.stop();
        //p3.stop();

        Thread.sleep(3000);
        es.shutdown();


    }
}
