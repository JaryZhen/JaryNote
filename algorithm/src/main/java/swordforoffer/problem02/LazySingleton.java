package swordforoffer.problem02;

/**
 * Created by Jary on 2017/8/18 0018.
 */
public class LazySingleton {
    private LazySingleton(){
        System.out.println("is create 地方很多人");
    }

    private static LazySingleton obj = null;
    public synchronized static LazySingleton getInstance() {
            if (obj == null)
                obj = new LazySingleton();
        return obj;
    }

    public static void main(String[] args) {
        LazySingleton.getInstance();
    }
}
