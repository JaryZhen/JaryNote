package ch03.Threadpool;

import java.util.concurrent.*;

/**
 * Created by Jary on 2017/9/19 0019.
 */
public class MyReajectThread implements RejectedExecutionHandler {


    public static class MyTask implements Runnable {
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() / 1000 + " :Thread ID:" + Thread.currentThread().getId());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println(r.toString() + "is dicard");

    }


    public static void main(String[] args) throws InterruptedException {
        MyTask task = new MyTask();
        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MICROSECONDS, new SynchronousQueue<Runnable>(), new MyThreadFactory(), new MyReajectThread()) {

        };
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            pool.submit(task);
            Thread.sleep(500);
            //System.out.print("---"+i);
        }
    }


}
