package ch02;

/**
 * Created by Jary on 2017/8/7 0007.
 */
public class VoLatile {
    static volatile  int a = 0;
    public static void main(String[] args) throws InterruptedException {

        Thread[] t = new Thread[10];
        for (int i = 0; i < 10; i++) {
            t[i] = new Thread(new TestVolatile());
            t[i].start();
        }
        for (int i = 0; i < 10; i++) {
            t[i].join();
        }
        System.out.println(a);
    }

    public static class TestVolatile implements Runnable{

        @Override
        public void run() {
            synchronized (VoLatile.class) {
                for (int i = 0; i < 10000; i++) {
                    a++;
                }
            }
        }
    }
}
