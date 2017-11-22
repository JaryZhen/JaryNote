package chp9.app;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Jary on 2017/11/22 0022.
 */
public class TestApp {
    public static void main(String[] args) throws Exception {
        InputStream is = new FileInputStream("common/jvm-book/target/classes/chp9/app/TestClass.class");
        byte[] b = new byte[is.available()];
        is.read(b);
        is.close();

       System.out.println(JavaClassExecuter.execute(b));
    }
}
