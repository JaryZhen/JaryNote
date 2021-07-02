package basc;

/**
 * @Author: Jary
 * @Date: 2020/6/15 9:19 AM
 */
public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public int val;

    public TreeNode(TreeNode left, TreeNode right, int data) {
        this.left = left;
        this.right = right;
        this.val = data;
    }

    @Override
    public String toString() {
        return "val:"+ val;
    }
}
