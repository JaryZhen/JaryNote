package ch05;

/**
 * Created by Jary on 2017/8/18 0018.
 */
public class Singleton {
    private Singleton(){
        System.out.println();
    }

    private static Singleton obj = null;
    public static Singleton getIns() {

        synchronized (obj) {
            if (obj == null)
                obj = new Singleton();
        }

        return obj;
    }
}
