package ch06_tree.binary;

/**
 * Created by Jary on 2017/8/29 0029.
 */
public class BinaryTree<T> implements BinaryTTree<T> {

    public BinaryNode<T> root;

    public BinaryTree()                          //构造空二叉树
    {
        this.root = null;
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public void preOrder() {
        System.out.print("先根次序遍历二叉树：  ");
        preOrder(root);
        System.out.println();

    }

    public void preOrder(BinaryNode<T> p)        //先根次序遍历以p结点为根的子二叉树，递归方法
    {
        if (p != null) {
            System.out.print(p.data.toString() + " ");  //访问当前结点
            preOrder(p.left);
            preOrder(p.right);
        }
    }

    @Override
    public void inOrder() {
        System.out.println("中跟次序");
        inOrder(root);
    }

    public void inOrder(BinaryNode<T> p) {
        if (p != null) {
            inOrder(p.left);
            System.out.print(p.data.toString() + " ");  //访问当前结点
            inOrder(p.right);
        }
    }

    @Override
    public void postOrder() {
        System.out.println("后跟次序");
        postOrder(root);
    }

    public void postOrder(BinaryNode<T> p) {
        if (p != null) {
            postOrder(p.left);
            postOrder(p.right);
            System.out.print(p.data.toString() + " ");  //访问当前结点
        }
    }

    @Override
    public void levelOrder() {

    }

    @Override
    public int count() {
        return count(root);
    }

    public int count(BinaryNode<T> p) {
        if (p == null)
            return 0;
        return 1 + count(p.left) + count(p.right);
    }

    @Override
    public int height() {
        return 0;
    }

    public int height(BinaryNode<T> p) {
        if (p == null)
            return 0;
        int hl = 1 + height(p.left);
        int hr = 1 + height(p.right);
        return (hl >= hr) ? hl : hr;
    }

    @Override
    public T search(T key) {
        return search(key, root);
    }

    public T search(T key, BinaryNode<T> p) {
        if (p == null) return null;
        T obj = null;
        if (key.equals(p.data)) {
            obj = p.data;
            return obj;
        }
        search(key, p.left);
        search(key, p.right);
        return obj;
    }

    //返回node结点的父母结点，若空树、未找到或node为根，则返回null
    public BinaryNode<T> getParent(BinaryNode<T> node) {
        if (root == null || node == null || node == root)
            return null;
        return getParent(root, node);
    }

    //在以p为根的子树中查找并返回node结点的父母结点
    public BinaryNode<T> getParent(BinaryNode<T> p, BinaryNode<T> node) {
        if (p == null)
            return null;
        if (p.left == node || p.right == node)
            return p;
        BinaryNode<T> find = getParent(p.left, node);
        if (find == null)
            find = getParent(p.right, node);
        return find;
    }

    @Override
    public void insertRoot(T x) {

    }

    @Override
    public BinaryNode<T> insertChild(BinaryNode<T> p, T x, boolean leftChild) {
        return null;
    }

    @Override
    public void removeChild(BinaryNode p, boolean leftChild) {

    }

    @Override
    public void removeAll() {

    }

    public String toString()                     //返回先根次序遍历二叉树所有结点的描述字符串
    {
        return toString(root);
    }

    private String toString(BinaryNode<T> p)     //返回先根次序遍历以p为根的子树描述字符串，递归算法
    {
        if (p == null)
            return "";
        return p.data.toString() + " " + toString(p.left) + toString(p.right);//递归调用
    }

    //5. 构造二叉树
    public BinaryTree(T[] prelist, T[] inlist)        //以先根和中根序列构造二叉树
    {
        this.root = create(prelist, inlist, 0, 0, prelist.length);
    }

    //以先根和中根序列创建一棵子树，子树根结点值是prelist[preStart]，n指定子序列长度，
    //返回所创建子树的根结点
    private BinaryNode<T> create(T[] prelist, T[] inlist, int preStart, int inStart, int n) {
        System.out.print("prelist:");
        print(prelist, preStart, n);
        System.out.print("，inlist:");
        print(inlist, inStart, n);
        System.out.println();

        if (n <= 0)
            return null;
        T elem = prelist[preStart];                          //根结点值
        BinaryNode<T> p = new BinaryNode<T>(elem);           //创建叶子结点
        int i = 0;
        while (i < n && !elem.equals(inlist[inStart + i]))     //在中根序列中查找根值所在位置
            i++;
        p.left = create(prelist, inlist, preStart + 1, inStart, i);             //创建左子树
        p.right = create(prelist, inlist, preStart + i + 1, inStart + i + 1, n - 1 - i);  //创建右子树
        return p;
    }

    private void print(T[] table, int start, int n) {
        for (int i = 0; i < n; i++)
            System.out.print(table[start + i]);
    }

    public static BinaryTree<String> make()                  //构造给定的一棵二叉树
    {
        BinaryTree<String> bitree = new BinaryTree<String>();  //创建空二叉树
        BinaryNode<String> child_f, child_d, child_b, child_c;
        child_d = new BinaryNode<String>("D", null, new BinaryNode<String>("G"));
        child_b = new BinaryNode<String>("B", child_d, null);
        child_f = new BinaryNode<String>("F", new BinaryNode<String>("H"), null);
        child_c = new BinaryNode<String>("C", new BinaryNode<String>("E"), child_f);
        bitree.root = new BinaryNode<String>("A", child_b, child_c);
        return bitree;
    }

    public static void main(String args[]) {
        BinaryTree<String> bitree = make();
        bitree.preOrder();                                   //先根次序遍历二叉树
        bitree.inOrder();
        bitree.postOrder();
    }

    public static void main2(String args[]) {
/*        //标明空子树的先根序列，两棵二叉树也对
        String[] prelist1 = {"A","B",null,null,"C",};
        BinaryTree<String> bitree1 = new BinaryTree<String>(prelist1);
        bitree1.preOrder();
        bitree1.inOrder();
        bitree1.postOrder();
        System.out.println("是否完全二叉树？  "+bitree1.isCompleteBinaryTree());
*/
 /*        String[] prelist2 = {"A","B","D",null,"G",null,null,null,"C","E",null,null,"F","H"};
        BinaryTree<String> bitree2 = new BinaryTree<String>(prelist2);
        bitree2.preOrder();
        bitree2.inOrder();
        bitree2.postOrder();
        bitree2.preOrderTraverse();
        bitree2.inOrderTraverse();
        bitree2.postOrderTraverse();
       System.out.println(bitree2.toGenListString());
        bitree2.levelOrder();                         //按层次遍历二叉树
        System.out.println("H结点的层次是 "+bitree2.getLevel("H"));
        System.out.println("是否完全二叉树？  "+bitree2.isCompleteBinaryTree());

        System.out.print("叶子结点：  ");
        bitree2.leaf();
        System.out.println("，共"+bitree2.countLeaf()+"个");

        BinaryTree<String> bitree3 = new BinaryTree<String>(bitree2); //深拷贝
        System.out.println("两棵二叉树相等?  "+bitree3.equals(bitree2));
        System.out.println("第3棵二叉树替换(\"D\",\"F\")，");
        bitree3.replace("D","F");

        System.out.println("两棵二叉树相等?  "+bitree3.equals(bitree2));
        System.out.println("第3棵二叉树全部替换(\"F\",\"Y\")  ");
        bitree3.replaceAll("F","Y");
        bitree3.preOrder();

        BinaryNode<String> find = bitree2.search("D");      //查找
        bitree2.insertChild(find, "Z", true);
        System.out.println("插入Z作为 "+find.data+"的左孩子");
        bitree2.preOrder();
*/
    }

}
