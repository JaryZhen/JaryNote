package ch02;

/**
 * Created by Jary on 2017/8/28 0028.
 */
public class SinglyLinkedList<T> implements LList<T> {

    public Node<T> head;

    public SinglyLinkedList() {
        this.head = new Node<T>();
    }

    public SinglyLinkedList(T[] element) {
        this();                                       //创建空单链表，只有头结点
        Node<T> rear = this.head;                       //rear指向单链表最后一个结点
        for (int i = 0; i < element.length; i++)          //若element==null，抛出空对象异常
        {                                             //element.length==0时，构造空链表
            rear.next = new Node<T>(element[i], null);   //尾插入,创建结点链入rear结点之后
            rear = rear.next;                         //rear指向新的链尾结点
        }
        this.head = this.head.next;
    }

    @Override
    public boolean isEmpty() {
        return this.head.next == null;
    }

    @Override
    public int length() {

        int i = 0;
        Node<T> p = this.head;                     //p从单链表第一个结点开始
        while (p != null)                               //若单链表未结束
        {
            i++;
            p = p.next;                               //p到达后继结点
        }
        return i;
    }

    @Override
    public T get(int i) {
        if (i > length()) return null;
        Node<T> temp = this.head;
        for (int j = 0; temp != null && j <= i; j++) {
            if (j == i) {
                return temp.data;
            }
            temp = temp.next;
        }
        return null;
    }

    @Override
    public void set(int i, T x) {
        if (x == null) return;                              //不能设置元素为空对象
        Node<T> p = this.head;
        for (int j = 0; p != null && j < i; j++)
            p = p.next;
        if (i >= 0 && p != null)
            p.data = x;                                    //p指向第i个结点
        else throw new IndexOutOfBoundsException(i + "");    //抛出序号越界异常
    }

    @Override
    public void insert(int i, T x) {
        if (x == null) return;                              //不能设置元素为空对象
        Node<T> p = this.head;
        for (int j = 0; p.next != null && j < i - 1; j++)     //寻找插入位置
            p = p.next;
        p.next = new Node<T>(x, p.next);
    }

    public void foreach() {
        Node<T> p = this.head;
        int i = 0;//p从单链表第一个结点开始
        while (p != null)                               //若单链表未结束
        {
            System.out.println(i + " == " + p.data);
            i++;
            p = p.next;                               //p到达后继结点
        }
    }

    @Override
    public void append(T x) {
        int i = length();
        insert(i, x);
    }

    @Override
    public T remove(int i) {
        if (i >= 0) {
            Node<T> p = this.head;
            for (int j = 0; p.next != null && j < i; j++) //定位到待删除结点（i）的前驱结点（i-1）
                p = p.next;
            if (p.next != null) {
                T old = p.next.data;                       //获得原对象
                p.next = p.next.next;                      //删除p的后继结点
                return old;
            }
        }
        return null;                                       //当i<0或大于表长时
    }

    @Override
    public void removeAll() {
        this.head.next = null;                               //Java将自动收回各结点所占用的内存空间
    }

    @Override
    public T search(T key) {
        return null;
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[10];
        for (int i = 0; i < 10; i++) {
            a[i] = i;
        }

        SinglyLinkedList<Integer> lList = new SinglyLinkedList<Integer>(a);
        lList.foreach();
        lList.append(Integer.parseInt("88"));
        lList.foreach();

    }
}
