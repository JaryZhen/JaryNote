package ch03.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jary on 2017/9/15 0015.
 */
public class FairLock implements Runnable{

    ReentrantLock fairlock = new ReentrantLock(true);

    @Override
    public void run() {
        while (true){
            fairlock.lock();
            try {
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName()+" get lock success");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                fairlock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        FairLock fairLock = new FairLock();
        Thread t1 = new Thread(fairLock,"t1");
        Thread t2 = new Thread(fairLock,"t2");

        t1.start();t2.start();
    }
}
