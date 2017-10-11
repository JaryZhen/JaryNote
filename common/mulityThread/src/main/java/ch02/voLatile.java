package ch02;

/**
 * Created by Jary on 2017/8/7 0007.
 */
public class voLatile  {
    static volatile  int a = 0;
    public static void main(String[] args) throws InterruptedException {

        Thread[] t = new Thread[10];
        for (int i = 0; i < 10; i++) {
            t[i] = new Thread(new testVolatile());
            t[i].start();
        }
        for (int i = 0; i < 10; i++) {
            t[i].join();
        }
        System.out.println(a);
    }

    public static class testVolatile implements Runnable{

        @Override
        public void run() {
            synchronized (voLatile.class) {
                for (int i = 0; i < 10000; i++) {
                    a++;
                }
            }
        }
    }
}
