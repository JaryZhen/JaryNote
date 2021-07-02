package tree;

import basc.TreeNode;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

/**
 * @Author: Jary
 * @Date: 2020/6/28 10:59 AM
 */
public class TreeTest {

    /**
     * 递归的 深度优先遍历
     *
     * @param root
     */
    public void dfsTree(TreeNode root) {
        if (root == null || root.left == null || root.right == null) return;
        dfsTree(root.left);
        System.out.println(root.left.val);
        dfsTree(root.right);
        System.out.println(root.right.val);
    }

    Queue<TreeNode> nodeQueue = new ArrayDeque();

    /**
     * 递归的广度优先遍历
     *
     * @param root
     */
    public void bfsTree(TreeNode root) {
        if (root == null) return;
        if (root.left != null) {
            System.out.println(root.left.val + "\t");
            nodeQueue.add(root.left);
        }
        if (root.right != null) {
            System.out.println(root.right.val);
            nodeQueue.add(root.right);
        }
        //System.out.println();
        bfsTree(nodeQueue.poll());
    }

    /**
     * 循环的广度优先遍历
     */
    public void bfsTree(Queue<TreeNode> nodeQueue2) {
        while (!nodeQueue2.isEmpty()) {
            TreeNode curr = nodeQueue2.poll();
            if (curr.left != null) {
                System.out.println(curr.left.val + "\t");
                nodeQueue2.add(curr.left);
            }
            if (curr.right != null) {
                System.out.println(curr.right.val);
                nodeQueue2.add(curr.right);
            }
        }
    }

    /**
     * 循环的广度优先遍历-中根遍历
     */
    public void bfsTreeMid(TreeNode head) {
        if (head == null) return;
        bfsTreeMid(head.left);
        System.out.println(head.val);
        bfsTreeMid(head.right);
    }

    /**
     * 循环的广度优先遍历,按层打印
     */
    public void bfsTree2(Queue<TreeNode> nodeQueue2) {
        int level = 0;
        while (!nodeQueue2.isEmpty()) {
            System.out.println("level " + level);
            int si = nodeQueue2.size();
            // 打印当前这一层所有元素
            for (int i = 0; i < si; i++) {
                TreeNode curr = nodeQueue2.poll();
                if (curr.left != null) {
                    System.out.print(curr.left.val + "\t");
                    nodeQueue2.add(curr.left);
                }
                if (curr.right != null) {
                    System.out.print(curr.right.val + "\t");
                    nodeQueue2.add(curr.right);
                }
            }
            level++;
        }
    }


    /*
     * 102. 给一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
     */

    /**
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        ArrayList<List<Integer>> le = new ArrayList<List<Integer>>();
        if (root == null) return le;
        Queue<TreeNode> nodeQueue = new ArrayDeque();
        nodeQueue.add(root);
        bfsTree3(nodeQueue, le);
        return le;
    }

    public void bfsTree3(Queue<TreeNode> nodeQueue2, ArrayList<List<Integer>> le) {
        List<Integer> list;
        while (!nodeQueue2.isEmpty()) {
            list = new ArrayList<>();
            int si = nodeQueue2.size();
            for (int i = 0; i < si; i++) {
                TreeNode curr = nodeQueue2.poll();
                list.add(curr.val);
                if (curr.left != null) {
                    nodeQueue2.add(curr.left);
                }
                if (curr.right != null) {
                    nodeQueue2.add(curr.right);
                }
            }
            le.add(list);
        }
    }


    /*
       104. 给定一个二叉树，找出其最大深度。
       <p>
       二叉树的深度为根节点到最远叶子节点的最长路径上的节点数
      返回它的最大深度 3 。
     */

    /**
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;

        int left = maxDepth(root.left) + 1;
        int right = maxDepth(root.right) + 1;

        return left > right ? left : right;

    }

    public int maxDepth2(TreeNode root) {
        Queue<TreeNode> nodeQueue = new ArrayDeque();
        nodeQueue.add(root);
        int level = 0;
        while (!nodeQueue.isEmpty()) {
            int si = nodeQueue.size();
            level++;
            for (int i = 0; i < si; i++) {
                TreeNode curr = nodeQueue.poll();
                if (curr.left != null) {
                    nodeQueue.add(curr.left);
                }
                if (curr.right != null) {
                    nodeQueue.add(curr.right);
                }
            }
        }
        return level;
    }

    public static void main(String[] args) {
        TreeNode left_left = new TreeNode(null, null, 3);
        TreeNode left_right = new TreeNode(null, null, 5);
        TreeNode right_left = new TreeNode(null, null, 6);
        TreeNode right_right = new TreeNode(null, null, 13);
        TreeNode left = new TreeNode(left_left, left_right, 4);
        TreeNode right = new TreeNode(right_left, right_right, 12);
        TreeNode head = new TreeNode(left, right, 8);
        /*
              8
            /   \
           4     12
          / \    / \
         3   5  6  13
         */


        //dfsTree(root_6);
        //bfsTree(root_6);

        Queue<TreeNode> nodeQueue = new ArrayDeque();
        nodeQueue.add(head);
        // bfsTree(nodeQueue);
        //bfsTree2(nodeQueue);

        TreeTest test = new TreeTest();
        test.bfsTreeMid(head);
    }

}
