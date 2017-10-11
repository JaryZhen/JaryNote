package ch02;

/**
 * Created by Jary on 2017/8/28 0028.
 */
public class Node<T> {
    public T data;                               //数据域，保存数据元素
    public Node<T> next;                         //地址域，引用后继结点

    public Node(T data, Node<T> next)            //构造结点，data指定数据元素，next指定后继结点
    {
        this.data = data;
        this.next = next;
    }
    public Node()
    {
        this(null, null);
    }

    //4、Node类可声明以下方法：
    public String toString()                     //返回结点元素值对应的字符串
    {
        return this.data.toString();
    }
    public boolean equals(Object obj)            //比较两个结点值是否相等，覆盖Object类的equals(obj)方法
    {
        return obj==this || obj instanceof Node && this.data.equals(((Node<T>)obj).data);
    }
}
