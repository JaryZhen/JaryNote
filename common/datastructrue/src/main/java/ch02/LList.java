package ch02;

/**
 * Created by Jary on 2017/8/28 0028.
 */
//线性表接口LList，描述线性表抽象数据类型，泛型参数T表示数据元素的数据类型
//package dataStructure.linear;                  //声明当前文件中的类或接口在指定包中

public interface LList<T>                        //线性表接口，泛型参数T表示数据元素的数据类型
{
    boolean isEmpty();                           //判断线性表是否空

    int length();                                //返回线性表长度

    T get(int i);                                //返回第i（i≥0）个元素

    void set(int i, T x);                        //设置第i个元素值为x

    void insert(int i, T x);                     //插入x作为第i个元素

    void append(T x);                            //在线性表最后插入x元素

    T remove(int i);                             //删除第i个元素并返回被删除对象

    void removeAll();                            //删除线性表所有元素

    T search(T key);                             //查找，返回首次出现的关键字为key元素
    //boolean contain(T x);                        //判断集合是否包含元素x，即元素x是否属于集合
}
//String toString();                           //返回显示线性表所有元素值对应的字符串
//boolean equals(Object obj);                  //比较两条线性表是否相等
//int count();                                 //返回集合的元素个数

/*也可继承SSet<T>，增加以下方法，使用i作为参数指定操作序号（位置）是线性表操作的特点
public interface LList<T> extends SSet<T>
{
    T get(int i);                                //返回第i（i≥0）个元素
    void set(int i, T x);                        //设置第i个元素值为x
    void insert(int i, T x);                     //插入x作为第i个元素
    T remove(int i);                             //删除第i个元素并返回被删除对象
}*/
