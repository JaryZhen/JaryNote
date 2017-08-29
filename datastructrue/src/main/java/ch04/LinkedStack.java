package ch04;

import ch02.Node;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public class LinkedStack<T> implements SStack<T> {
    private Node<T> top;                      //栈顶结点，单链表结点类声明见第2章

    public LinkedStack()                                   //构造空栈
    {
        this.top = null;
    }

    @Override
    public boolean isEmpty() {
        return this.top == null;
    }

    @Override
    public void push(T x) {

        if (x == null) return;
        this.top = new Node<T>(x, this.top);
    }

    @Override
    public T pop() {
        if (this.top == null)
            return null;
        T temp = this.top.data;                            //取栈顶结点元素
        this.top = this.top.next;                          //删除栈顶结点
        return temp;
    }

    @Override
    public T get() {
        return this.top == null ? null : this.top.data;
    }

    //返回栈所有元素的描述字符串，形式为“(,)”。算法同不带头结点的单链表
    public String toString() {
        String str = "(";
        for (Node<T> p = this.top; p != null; p = p.next) {
            str += p.data.toString();
            if (p.next != null)
                str += ", ";                               //不是最后一个结点时后加分隔符
        }
        return str + ")";                                    //空表返回()
    }
}
