package chp8;

/**
 * 方法静态分派演示
 * Created by Jary on 2017/11/21 0021.
 */
public class StaticDispatch_6 {
    static abstract class Human {
    }

    static class Man extends Human {
    }

    static class Woman extends Human {
    }

    public void sayHello(Human guy) {
        System.out.println("hello,guy!");
    }

    public void sayHello(Man guy) {
        System.out.println("hello,gentleman!");
    }

    public void sayHello(Woman guy) {
        System.out.println("hello,lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch_6 sr = new StaticDispatch_6();
        sr.sayHello(man);
        sr.sayHello(woman);
    }
}
