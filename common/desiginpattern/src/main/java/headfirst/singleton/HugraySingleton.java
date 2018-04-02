package headfirst.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jary on 2017/8/18 0018.
 */
public class HugraySingleton {
    private HugraySingleton() {
        System.out.println("is create ");
    }

    private static HugraySingleton obj = new HugraySingleton();

    public synchronized static HugraySingleton getInstance() {
        if (obj == null)
            obj = new HugraySingleton();
        return obj;
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println( HugraySingleton.getInstance());
                }
            });
        }
    }
}
