import java.util.Random;

/**
 * Created by Jary on 2017/11/8 0008.
 */
public class test {
    public static Object ins= null;
    public static void main(String[] args) {

        test a = new test();
        test b = new test();

        a.ins = b;
        b.ins = a;

        a =null;
        b= null;


        System.gc();

        System.out.println();
    }
}
