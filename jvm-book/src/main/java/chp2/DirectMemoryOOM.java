package chp2;

        import sun.misc.Unsafe;

        import java.lang.reflect.Field;

/**
 * -Xmx20M -XX:MaxDirectMemorySize=10M
 * Created by Jary on 2017/10/11 0011.
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024*1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeFeild = Unsafe.class.getDeclaredFields()[0];
        unsafeFeild.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeFeild.get(null);

        while (true){
            unsafe.allocateMemory(_1MB);
        }
    }
}
