package ch06;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Jary
 * @Date: 2020/2/10 11:38 AM
 */
public class CompletableFutureTest implements Runnable {

    CompletableFuture<Integer> re = null;

    public CompletableFutureTest(CompletableFuture<Integer> re) {
        this.re = re;
    }

    @Override
    public void run() {
        int myRe = 0;
        try {
            myRe = re.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(myRe);
    }

    public static void main(String[] args) throws InterruptedException {
        final CompletableFuture<Integer> fu = new CompletableFuture<>();
        new Thread(new CompletableFutureTest(fu)).start();
        Thread.sleep(1000);
        fu.complete(60);
    }
}
