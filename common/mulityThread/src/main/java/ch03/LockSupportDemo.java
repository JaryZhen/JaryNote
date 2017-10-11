package ch03;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Jary on 2017/9/18 0018.
 */
public class LockSupportDemo {
    static  Object u = new Object();

    static class ChangeObjectThread extends Thread{
        public ChangeObjectThread(String name){
            super.setName(name);
        }

        @Override
        public void run() {
            synchronized (u){
                System.out.println("in "+ getName());
                LockSupport.park();
                System.out.println("proc ... "+ getName());

            }
        }
    }
    static ChangeObjectThread t1 = new ChangeObjectThread("t1");
    static ChangeObjectThread t2 = new ChangeObjectThread("t2");

    public static void main(String[] args) throws InterruptedException {

        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(5000);
        System.out.println(" ... ");
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);

        t1.join();
        t2.join();
    }
}
