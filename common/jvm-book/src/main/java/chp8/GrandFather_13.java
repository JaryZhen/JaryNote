package chp8;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * Created by Jary on 2017/11/21 0021.
 */
public class GrandFather_13 {
    void thinking() {
        System.out.println("i am grandfather");
    }
}

class Father extends GrandFather_13 {
    void thinking() {
        System.out.println("i am father");
    }
}

class Son extends Father {
    void thinking() {
        //super.thinking();
        // 请读者在这里填入适当的代码（不能修改其他地方的代码）
        // 实现调用祖父类的thinking()方法，打印"i am grandfather"
        try {
            MethodType mt = MethodType.methodType(void.class);
            MethodHandle mh = lookup().findSpecial(GrandFather_13.class,
                    "thinking", mt, getClass());
            mh.invoke(this);
        } catch (Throwable e) {
        }
        System.out.println("i am son");
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.thinking();
    }
}
