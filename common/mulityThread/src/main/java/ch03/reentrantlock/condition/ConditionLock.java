package ch03.reentrantlock.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jary on 2017/9/16 0016.
 */
public class ConditionLock implements Runnable {
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
        ConditionLock clock = new ConditionLock();
        Thread a = new Thread(clock,"a");
        Thread b = new Thread(clock,"b");

        a.start();
        Thread.sleep(2000);

        b.start();
        //
        lock.lock();
        condition.signal();
        lock.unlock();

        //a.setDaemon(true);
        //b.setDaemon(true);
    }

}
