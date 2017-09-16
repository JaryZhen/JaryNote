package ch03;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jary on 2017/9/13 0013.
 */
public class InterRrupterTimeLock implements Runnable{
    public static ReentrantLock lock = new ReentrantLock();

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){

                System.out.println(Thread.currentThread().getName()+" get lock success");
                Thread.sleep(6000);
                }
                else {
                    System.out.println(Thread.currentThread().getName()+ " get lock filed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        InterRrupterTimeLock timeLockt = new InterRrupterTimeLock();

        Thread t1 = new Thread(timeLockt,"t1");
        Thread t2 = new Thread(timeLockt,"t2");

        t1.start();
        t2.start();

    }
}
