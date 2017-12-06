package java7;

/**
 * Created by Jary on 2017/9/21 0021.
 */
public class Newfeture {
    //1. switch can use string
    static String getString(String string) {

        switch (string) {
            case "a":
                System.out.println(string);
            case "b":
                System.out.println(string);
        }
        return string;
    }

    // 2. 二进制
    static int getBinaryInt() {
        int a = 0b100;
        return a;
    }

    //3.下划线
    static int getInt_() {
        int b = 100_100_000;
        return b;
    }
    //4.合并异常
    static void exception() {
        int a = 0;
        try {
        } catch (Exception | OutOfMemoryError e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(getString("a"));
        System.out.println(getBinaryInt());
        System.out.println(getInt_());

    }
}
