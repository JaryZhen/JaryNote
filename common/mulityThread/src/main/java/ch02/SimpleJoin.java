package ch02;

/**
 * @Author: Jary
 * @Date: 2020/2/3 4:51 PM
 */
public class SimpleJoin {
    public volatile static int i =0 ;
    public static class AddThread extends Thread{
        @Override
        public void run() {
            for (i = 0 ; i< 10000000 ; i ++);
        }
    }

    public static void main(String[] args) {
        AddThread thread = new AddThread();
        thread.start();
        /*try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        try {
            Thread.currentThread().wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(i);
    }
}
