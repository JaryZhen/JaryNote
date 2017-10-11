package ch03;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2017/9/18 0018.
 */
public class CountDownLatchDemo implements Runnable{

    static final CountDownLatch latch = new CountDownLatch(10);
    static int count = 0;
    @Override
    public void run() {
        try {
            Thread.sleep(2*1000);
            System.out.println("check complete");
            latch.countDown();
            latch.await();
            System.out.println("process  complete------ "+count);
            count++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchDemo demo = new CountDownLatchDemo();
        ExecutorService service =  Executors.newFixedThreadPool(10);

        for (int i = 0; i < 15; i++) {
            service.submit(demo);
        }
        latch.await();
        System.out.println("" + "final "+count);


        service.shutdown();
    }
}
