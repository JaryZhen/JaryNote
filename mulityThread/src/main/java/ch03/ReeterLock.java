package ch03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jary on 2017/8/14 0014.
 */
public class ReeterLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {

        for (int j = 0; j < 1000000; j++) {
            lock.lock();
            i++;
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReeterLock t = new ReeterLock();

        Thread a1 = new Thread(t);
        Thread a2 = new Thread(t);
        a1.start();
        a2.start();
        a1.join();
        a2.join();
        System.out.println(i);
    }
}
