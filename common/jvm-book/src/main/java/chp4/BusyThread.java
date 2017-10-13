package chp4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by Jary on 2017/10/13 0013.
 */
public class BusyThread {
    /**
     * �߳���ѭ����ʾ
     */
    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)   // ��41��
                    ;
            }
        }, "testBusyThread");
        thread.start();
    }

    /**
     * �߳����ȴ���ʾ
     */
    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }

    public static void main(String[] args) throws Exception {
        Thread.sleep(1000*9);
        System.out.print("ok");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        br.readLine();
        createBusyThread();
        br.readLine();
        Object obj = new Object();
        createLockThread(obj);
    }


}
