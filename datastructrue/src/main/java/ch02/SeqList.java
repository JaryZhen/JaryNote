package ch02;

/**
 * Created by Jary on 2017/8/28 0028.
 */
public class SeqList<T> implements LList<T> {
    protected Object[] element;                  //对象数组，保护成员
    protected int len;                           //顺序表长度，记载元素个数

    public SeqList(int size)                     //构造方法，创建容量为size的空表
    {
        this.element = new Object[size];         //若size<0，抛出负数组长度异常NegativeArraySizeException
        this.len = 0;
    }

    public SeqList()                             //默认构造方法，创建默认容量的空表
    {
        this(64);
    }

    @Override
    public boolean isEmpty() {
        return this.len == 0;
    }

    @Override
    public int length() {
        return this.len;
    }

    @Override
    public T get(int i) {
        if (i >= 0 && i <= this.len)
            return (T) this.element[i];
        return null;
    }

    @Override
    public void set(int i, T x) {
        if (x == null) return;
        if (i >= 0 && i < this.len) {
            this.element[i] = x;
        } else throw new IndexOutOfBoundsException(i + "");    //抛出序号越界异常
    }

    @Override
    public void insert(int i, T x) {
        if (x == null) return;

        if (this.len == this.element.length) {
            Object[] temp = this.element;
            this.element = new Object[this.len * 2];
            for (int j = 0; j < temp.length; j++) {
                this.element[j] = temp[j];
            }
        }
        if (i < 0) {
            i = 0;
            this.element[i] = x;
        }
        if (i > this.element.length) {
            i = this.len;
            this.element[i] = x;
        }
        if (i >= 0 && i < this.len) {
            for (int j = this.len - 1; j >= i; j--) {
                this.element[j + 1] = this.element[j];

            }
            this.element[i] = x;
            this.len++;
        }
    }

    @Override
    public void append(T x) {
        insert(this.len, x);
    }

    @Override
    public T remove(int i) {
        if (this.len == 0 || i < 0 || i >= this.len)
            return null;
//        throw new IndexOutOfBoundsException(i+"");     //抛出序号越界异常
        T old = (T) this.element[i];
        for (int j = i; j < this.len - 1; j++)                   //元素前移，平均移动len/2
            this.element[j] = this.element[j + 1];
        this.element[this.len - 1] = null;
        this.len--;
        return old;
    }

    @Override
    public void removeAll() {
        this.len = 0;
    }

    public String toString()                     //返回显示线性表所有元素值的字符串，形式为[,]
    {
        String str = "(";
        if (this.len != 0) {
            for (int i = 0; i < this.len - 1; i++)
                str += this.get(i).toString() + ", ";
            str += this.get(this.len - 1).toString();
        }
        return str + ")";
    }


    //6.  顺序表的浅拷贝与深拷贝
/*    public SeqList(SeqList<T> list)                      //浅拷贝构造方法
    {
        this.element = list.element;                  //数组引用赋值，两个变量共用一个数组，错误
        this.len = list.len;
    }*/
    public SeqList(SeqList<T> list)                        //深拷贝构造方法
    {
        this.len = list.len;                               //若list==null，抛出空对象异常
        this.element = new Object[list.element.length];    //申请一个数组
        for (int i = 0; i < list.element.length; i++)          //复制数组元素，O(n)
            this.element[i] = list.element[i];             //对象引用，没有创建新对象
//          this.element[i] = new T(list.element[i]);    //语法错，因为Java没有提供默认拷贝构造方法
//      this.element[i] = new Object(list.element[i]);    //语法错，因为Object没有提供拷贝构造方法，且构造方法不能继承
    }

    public SeqList(T[] element)                           //构造方法，参数数组指定顺序表初值，深拷贝
    {
        this.len = element.length;
        this.element = new Object[element.length];         //申请一个数组
        for (int i = 0; i < element.length; i++)               //复制数组元素，O(n)
            this.element[i] = element[i];
    }

    //7 顺序表比较相等
    //比较两个顺序表是否相等 ，覆盖Object类的equals(obj)方法，O(n)
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof SeqList) {
            SeqList<T> list = (SeqList<T>) obj;
            if (this.length() == list.length()) {
                for (int i = 0; i < this.length(); i++)             //比较实际长度的元素，而非数组容量
                    if (!(this.get(i).equals(list.get(i))))     //运行时多态性
                        return false;
                return true;
            }
        }
        return false;
    }
    //以下第8章 8.2.1 顺序查找

    //顺序查找关键字为key元素，返回首次出现的元素，若查找不成功返回-1
    //key可以只包含关键字数据项，由T类的equals()方法提供比较对象相等的依据
    public int indexOf(T key) {
        if (key != null)
            for (int i = 0; i < this.len; i++)
                if (this.element[i].equals(key))           //对象采用equals()方法比较是否相等
                    return i;
        return -1;                                         //空表、key为空对象或未找到时
    }

    public T search(T key)                                 //查找，返回首次出现的关键字为key元素
    {
        int find = this.indexOf(key);
        return find == -1 ? null : (T) this.element[find];
    }

    public boolean contain(T key)                          //判断线性表是否包含关键字为key元素
    {
        return this.indexOf(key) >= 0;                       //以查找结果获得判断结果
    }

    public void remove(T key)                              //删除首次出现的关键字为key元素
    {
        this.remove(this.indexOf(key));                    //调用remove(int)方法
    }

    //以下是第8章 8.2.1 顺序查找习题
    public int lastIndexOf(T key)                          //返回元素key最后出现位置，若未找到返回-1
    {
        if (key != null)
            for (int i = this.len - 1; i >= 0; i--)
                if (this.element[i].equals(key))
                    return i;
        return -1;                                         //空表、key为空对象或未找到时
    }

    public void removeAll(T key)                           //删除所有关键字为key元素
    {
        if (key != null) {
            int i = 0;
            while (i < this.len)
                if (this.element[i].equals(key))
                    this.remove(i);                        //删除元素，this.len减1，i不变
                else i++;
        }
    }

    public void replace(T x, T y)                          //将首次出现的元素x替换为y，O(n)
    {
        if (x != null && y != null) {
            int i = this.indexOf(x);                       //查找x首次出现位置
            if (i == -1)
                this.element[i] = y;
        }
    }

    public void replaceAll(T x, T y)                       //将所有元素x替换为y
    {
        if (x != null && y != null)
            for (int i = 0; i < this.len; i++)
                if (x.equals(this.element[i]))
                    this.element[i] = y;
    }

    //第10章，10.2 实现迭代器
    public java.util.Iterator<T> iterator()                //返回Java迭代器对象
    {
        return new SeqIterator();
    }

    private class SeqIterator implements java.util.Iterator<T> //私有内部类，实现迭代器接口
    {
        int index = -1, succ = 0;                              //当前元素和后继元素序号

        public boolean hasNext()                           //若有后继元素，返回true
        {
            return this.succ < SeqList.this.len;   //SeqList.this.len是外部类当前实例的成员变量
        }

        public T next()                               //返回后继元素，若没有后继元素，返回null
        {
            T value = SeqList.this.get(this.succ);    //调用外部类SeqList当前实例的成员方法
            if (value != null) {
                this.index = this.succ++;
                return value;
            }
            throw new java.util.NoSuchElementException();  //抛出无此元素异常
        }

        public void remove()                               //删除迭代器对象表示的集合当前元素
        {
            if (this.index >= 0 && this.index < SeqList.this.len) {
                SeqList.this.remove(this.index);           //调用外部类当前实例的成员方法
                //删除第index个元素，长度SeqList.this.len-1
                if (this.succ > 0)//(this.index<this.succ)
                    this.succ--;
                this.index = -1;                             //设置不能连续删除
            } else throw new java.lang.IllegalStateException(); //抛出无效状态异常
        }
    }//SeqIterator内部类结束

    //习题10
    public java.util.ListIterator<T> listIterator()        //返回Java列表迭代器对象
    {
        return new SeqListIterator(0);
    }

    public java.util.ListIterator<T> listIterator(final int index) //返回Java列表迭代器对象
    {
        if (index >= 0 && index < this.len)
            return new SeqListIterator(index);
        else throw new IndexOutOfBoundsException("Index: " + index);
    }

    //私有内部类，继承实现迭代器接口的SeqIterator内部类，实现列表迭代器接口
    private class SeqListIterator extends SeqIterator implements java.util.ListIterator<T> {
        public SeqListIterator(int index) {
            this.succ = index;
        }

        public boolean hasPrevious()                       //若有前驱元素，返回true
        {
            return this.succ != 0;
        }

        public T previous()                                //返回前驱元素
        {
            T value = SeqList.this.get(this.succ - 1);
            if (value != null) {
                this.index = this.succ--;
                return value;
            }
            throw new java.util.NoSuchElementException();  //抛出无此元素异常
        }

        public int nextIndex()                             //返回后继元素序号
        {
            return this.succ;
        }

        public int previousIndex()                         //返回前驱元素序号
        {
            return this.succ - 1;
        }

        public void set(T x)                               //将集合当前元素替换为x
        {
            if (this.index >= 0 && this.index < SeqList.this.len)
                SeqList.this.set(this.index, x);           //调用外部类当前实例的成员方法
            else throw new java.lang.IllegalStateException(); //抛出无效状态异常
        }

        public void add(T x)                               //增加元素x
        {
            SeqList.this.insert(this.succ, x);             //调用外部类当前实例的成员方法
            this.succ++;                                   //插入元素为当前元素
        }
    }//SeqListIterator内部类结束
}
