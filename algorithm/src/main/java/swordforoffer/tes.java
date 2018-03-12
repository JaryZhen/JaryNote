package swordforoffer;

import java.math.BigDecimal;

/**
 * Created by Jary on 2018/3/8 0008.
 */
public class tes {
    public static void main(String[] args) {
        double a=2.0;
        double b =2.00;
        BigDecimal data1 = new BigDecimal(a);
        BigDecimal data2 = new BigDecimal(b);
        System.out.println(data1.compareTo(data2));

        System.out.println(a==b);
        System.out.println(NumberOf1(4));
    }

    public static int NumberOf1(int n) {
        int count = 0;
        int flag = 1;
        while (n != 0) {
            if ((n & 1) != 0) {
                count++;
            }
            n = n >> 1;
        }
        return count;
    }

}
