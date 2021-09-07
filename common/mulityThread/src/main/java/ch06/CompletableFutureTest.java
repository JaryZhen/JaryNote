package ch06;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Jary
 * @Date: 2020/2/10 11:38 AM
 */
public class CompletableFutureTest {

    public class CompletableFutur implements Runnable {
        CompletableFuture<Integer> re = null;

        public CompletableFutur(CompletableFuture<Integer> re) {
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
    }

    public void test() {
        final CompletableFuture<Integer> fu = new CompletableFuture<>();
        new Thread(new CompletableFutur(fu)).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fu.complete(60);
    }

    public Integer call(Integer pa) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pa * pa;
    }

    public static void main(String[] args) {
        CompletableFutureTest ct = new CompletableFutureTest();
        ct.test();
    }
}
