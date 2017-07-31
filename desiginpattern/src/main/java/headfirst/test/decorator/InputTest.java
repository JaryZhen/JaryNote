package headfirst.test.decorator;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jary on 2017/7/19 0019.
 */
public class InputTest {
    public static void main(String[] args) {
        int c = 0;
        try {

            InputStream  in = new TestLowerCaseInputStream(new BufferedInputStream(new FileInputStream("target/tt.txt")));
            while((c = in.read()) >= 0){
                System.out.println();
            }
        }catch (IOException o){
            o.printStackTrace();
        }
    }
}
