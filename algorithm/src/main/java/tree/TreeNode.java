package tree;

/**
 * @Author: Jary
 * @Date: 2020/6/15 9:19 AM
 */
public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public String data;

    public TreeNode(TreeNode left, TreeNode right, String data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public static TreeNode create() {
        TreeNode G = new TreeNode(null, null, "G");
        TreeNode E = new TreeNode(null, null, "E");
        TreeNode F = new TreeNode(G, null, "F");
        TreeNode C = new TreeNode(E, F, "C");
        TreeNode B = new TreeNode(null, null, "B");
        TreeNode A = new TreeNode(B, C, "A");
        return A;
    }
}
