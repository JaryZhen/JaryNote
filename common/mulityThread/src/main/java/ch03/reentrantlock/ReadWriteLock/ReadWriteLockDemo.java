package ch03.reentrantlock.ReadWriteLock;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Jary on 2017/9/16 0016.
 */
public class ReadWriteLockDemo {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Lock read = readWriteLock.readLock();
    private static Lock write = readWriteLock.writeLock();
    private int valeu;

    public Object handleRead(Lock lk) throws InterruptedException {
        try {
            lk.lock();
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName()+" read = "+valeu);
        } finally {
            lk.unlock();
        }
        return valeu;
    }

    public void handleWrite(Lock lk, int index) throws InterruptedException {
        try {
            lk.lock();
            Thread.sleep(1000);
            valeu = index;
            System.out.println(Thread.currentThread().getName()+" write = "+valeu);

        } finally {
            lk.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo = new ReadWriteLockDemo();
        Runnable readRun = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(read);
                    //demo.handleRead(lock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable writeRun = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleWrite(write, new Random().nextInt());
                    //demo.handleWrite(lock, new Random().nextInt());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(readRun).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(writeRun).start();
        }

    }

}
