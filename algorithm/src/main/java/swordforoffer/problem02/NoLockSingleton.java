package swordforoffer.problem02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2017/9/19 0019.
 */
public class NoLockSingleton {
    private NoLockSingleton(){}

    private static class help{
        private static NoLockSingleton singleton = new NoLockSingleton();
    }

    public static NoLockSingleton getInstance(){
        return help.singleton;
    }
    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println( NoLockSingleton.getInstance());
                }
            });
        }

    }
}
