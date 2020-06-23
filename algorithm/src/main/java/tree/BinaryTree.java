package tree;

import java.util.LinkedList;

public class BinaryTree {

    public static void main(String[] args) {
        TreeNode root = TreeNode.create();
        TreeNode next = root;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode temp = queue.poll();
            System.out.print(temp.data + " ");
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
