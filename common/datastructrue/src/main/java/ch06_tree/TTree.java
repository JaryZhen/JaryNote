package ch06_tree;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public interface TTree<T> {
    boolean isEmpty();                                     //判断是否空树
    TreeNode<T> getChild(TreeNode<T> p, int i);            //返回p结点的第i（i≥0）个孩子结点
    TreeNode<T> getLastChild(TreeNode<T> p);               //返回p结点的最后一个孩子结点
    TreeNode<T> getLastSibling(TreeNode<T> p);             //返回p结点的最后一个兄弟结点
    TreeNode<T> getParent(TreeNode<T> node);               //返回node的父母结点

    int count();                                           //返回树的结点个数
    int childCount(TreeNode<T> p);                         //返回p结点的孩子结点个数
    int height();                                          //返回树的高度
    TreeNode<T> search(T x);                               //查找并返回元素为x的结点
    void preOrder();                                       //先根次序遍历树
    void postOrder();                                      //后根次序遍历树
    void levelOrder();                                     //按层次遍历树

    void insertRoot(T x);                                  //插入元素x作为根结点
    TreeNode<T> insertChild(TreeNode<T> p, T x, int i);    //插入x元素作为p结点的第i个孩子结点
    TreeNode<T> insertLastChild(TreeNode<T> p, T x);       //插入最后一个孩子结点
    TreeNode<T> insertLastSibling(TreeNode<T> p, T x);     //插入最后一个兄弟结点
    void removeChild(TreeNode<T> p, int i);                //删除以p结点的第i个孩子为根的子树
    void removeAll();                                      //删除树
}
