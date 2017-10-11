package ch05.singleton;

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
    }
}
