package chp8;

import java.io.Serializable;

/**
 * Created by Jary on 2017/11/21 0021.
 */
public class Overload_7 {
    public static void sayHello(Object arg) {
        System.out.println("hello Object");
    }

    public static void sayHello(int arg) {
        System.out.println("hello int");
    }

    public static void sayHello(long arg) {
        System.out.println("hello long");
    }

    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

    public static void sayHello(char arg) {
        System.out.println("hello char");
    }

    public static void sayHello(char... arg) {
        System.out.println("hello char ...");
    }

    public static void sayHello(Serializable arg) {
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        // char -> int -> long -> float -> double -> Character(自动装箱) -> Serializable -> 变长参数
        sayHello('a');
    }
}
