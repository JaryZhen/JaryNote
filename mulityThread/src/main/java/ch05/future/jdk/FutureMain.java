package ch05.future.jdk;

import java.util.concurrent.*;

/**
 * Created by Jary on 2017/8/21 0021.
 */
public class FutureMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Future<String> future = new FutureTask<String>(new RealData("a"));
        ExecutorService es = Executors.newFixedThreadPool(1);
        es.submit((Runnable) future);

        Thread.sleep(1000);

        System.out.println(future.get());

    }
}
