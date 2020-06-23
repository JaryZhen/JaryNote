package ch03.reentrantlock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jary on 2017/9/16 0016.
 */
public class ConditionLockTest implements Runnable {
    static  ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    int a =0;
    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" is trying");
            condition.await();
            System.out.println(Thread.currentThread().getName()+" is going");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
           // condition.signal();
            lock.unlock();
        }

    }
    public static void main(String[] args) throws InterruptedException {
        ConditionLockTest clock = new ConditionLockTest();
        Thread a = new Thread(clock,"a");
        Thread b = new Thread(clock,"b");
        a.setDaemon(true);
        b.setDaemon(true);

        a.start();
        b.start();

        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName()+" main trying");

        lock.lock();
        condition.signalAll();
        lock.unlock();

    }

}
