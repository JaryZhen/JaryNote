package headfirst.singleton;

/**
 * Created by Jary on 2017/8/18 0018.
 */
public class LazySingleton {
    private LazySingleton(){
        System.out.println("is create ");
    }

    private static LazySingleton obj = null;
    public synchronized static LazySingleton getInstance() {
            if (obj == null)
                obj = new LazySingleton();
        return obj;
    }

    public static void main(String[] args) {
        //LazySingleton a = new LazySingleton();
        NoLockSingleton a = NoLockSingleton.getInstance();
        System.out.println(a);
        NoLockSingleton a1 = NoLockSingleton.getInstance();
        System.out.println(a1);
        NoLockSingleton a2 = NoLockSingleton.getInstance();
        System.out.println(a2);

        NoLockSingleton a3 = NoLockSingleton.getInstance();
        System.out.println(a3);
        NoLockSingleton a4 = NoLockSingleton.getInstance();
        System.out.println(a4);

    }
}
