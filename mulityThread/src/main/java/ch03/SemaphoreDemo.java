package ch03;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Jary on 2017/8/14 0014.
 */
public class SemaphoreDemo  implements Runnable{
    final Semaphore sm = new Semaphore(5);


    public void run() {
        try {
            //sm.acquire();
            Thread.sleep(2000000);
            System.out.println(Thread.currentThread().getId() +" done!");
           // sm.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(20);
        final  SemaphoreDemo sd = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            Thread.sleep(2000);

            exec.submit(sd);
        }
    }
}
