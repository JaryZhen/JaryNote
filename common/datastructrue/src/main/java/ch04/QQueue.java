package ch04;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public interface QQueue<T> {
    boolean isEmpty();                           //判断队列是否空
    void enqueue(T x);                           //元素x入队
    T dequeue();                                 //出队，返回队头元素
}
