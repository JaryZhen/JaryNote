package ch06_tree;

/**
 * Created by Jary on 2017/8/29 0029.
 */
//树的孩子兄弟链表结点类

public class TreeNode<T>                         //树的孩子兄弟链表结点类，泛型T指定结点的元素类型
{
    public T data;                               //数据域，存储数据元素
    public TreeNode<T> child, sibling;           //链，分别指向孩子、兄弟结点

    //构造结点，参数分别指定元素、孩子和兄弟结点
    public TreeNode(T data, TreeNode<T> child, TreeNode<T> sibling)
    {
        this.data = data;
        this.child = child;
        this.sibling = sibling;
    }
    public TreeNode(T data)                      //构造指定值的叶子结点
    {
        this(data, null, null);
    }
    public TreeNode()
    {
        this(null, null, null);
    }

    //可声明以下方法
    public String toString()
    {
        return this.data.toString();
    }
    public boolean equals(Object obj)            //比较两个结点值是否相等，覆盖Object类的equals(obj)方法
    {
        return obj==this || obj instanceof TreeNode && this.data.equals(((TreeNode<T>)obj).data);
    }
    public boolean isLeaf()                      //判断是否叶子结点
    {
        return this.child==null && this.sibling==null;
    }
}

