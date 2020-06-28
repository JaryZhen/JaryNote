package tree;

import leetcode.TreeNode;

import java.util.LinkedList;

public class BinaryTree {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(null,null,0);
        TreeNode next = root;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.print(temp.val + " ");
            if (next == temp) {
                System.out.print("\n");
            }
            if (temp.left != null) {
                next = temp.left;
                queue.offer(temp.left);
            }
            if (temp.right != null) {
                queue.offer(temp.right);
                next = temp.right;
            }
        }


    }
}
