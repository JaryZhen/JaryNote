package ch05.singleton;

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

        LazySingleton a = LazySingleton.getInstance();
        System.out.println(a);
        LazySingleton a1 = LazySingleton.getInstance();
        System.out.println(a1);
        LazySingleton a2 = LazySingleton.getInstance();
        System.out.println(a2);

        LazySingleton a3 = LazySingleton.getInstance();
        System.out.println(a3);
        LazySingleton a4 = LazySingleton.getInstance();
        System.out.println(a4);

    }
}
