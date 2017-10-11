package ch04;

import ch02.Node;

/**
 * Created by Jary on 2017/8/29 0029.
 */
//4.2.3   链式队列

public class LinkedQueue<T> implements QQueue<T> //链式队列类，实现队列接口
{
    private Node<T> front, rear;                 //front和rear分别指向队头和队尾结点

    public LinkedQueue()                         //构造空队列
    {
        this.front = this.rear = null;
    }

    public boolean isEmpty()                     //判断队列是否空，若空返回true
    {
        return this.front == null && this.rear == null;
    }

    public void enqueue(T x)                     //元素x入队，空对象不能入队
    {
        if (x == null)
            return;                              //空对象不能入队
        Node<T> q = new Node<T>(x, null);
        if (this.front == null)
            this.front = q;                        //空队插入
        else
            this.rear.next = q;                    //插入在队列之尾
        this.rear = q;
    }

    public T dequeue()                           //出队，返回队头元素，若队列空返回null
    {
        if (isEmpty())
            return null;
        T temp = this.front.data;                //取得队头元素
        this.front = this.front.next;            //删除队头结点
        if (this.front == null)
            this.rear = null;
        return temp;
    }

    public String toString()                     //返回队列所有元素的描述字符串，形式为“(,)”
    {                                            //算法同不带头结点的单链表
        String str = "(";
        for (Node<T> p = this.front; p != null; p = p.next) {
            str += p.data.toString();
            if (p.next != null)
                str += ", ";                               //不是最后一个结点时后加分隔符
        }
        return str + ")";                                    //空表返回()
    }
}
