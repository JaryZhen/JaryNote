package ch04;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public interface SStack<T> {
    boolean isEmpty();                 //判断栈是否为空
    void push(T x);                    //元素x入栈
    T pop();                           //出栈，返回栈顶元素
    T get();                           //取栈顶元素，未出栈
}
