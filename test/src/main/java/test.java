import uuid.UuidTest;

import java.util.Random;

/**
 * Created by Jary on 2017/11/8 0008.
 */
public class test {
    public static Object ins= null;
    public static void main(String[] args) throws InterruptedException {

        int a = 3 | 4;
        System.out.println(""+  a);
    }

    public static int count (int n ){
        int com = 0;
        while (n>0){
            int y = n%2;
            n = n/2;
            if (y==1){
                com++;
            }
        }
        System.out.println(""+com);
        return com;
    }
}
