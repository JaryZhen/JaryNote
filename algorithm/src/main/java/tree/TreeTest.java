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
        System.out.println(root.left.val);//先序
        dfsTree(root.left);
        System.out.println(root.left.val);//中序
        dfsTree(root.right);
        System.out.println(root.right.val);//后序
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

    /**
     * 102. 给一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
     *
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


    /**
     * 104. 给定一个二叉树，找出其最大深度。
     * <p>
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数
     * 返回它的最大深度 3 。
     *
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

    public TreeNode maxComParent(TreeNode head, TreeNode p, TreeNode b) {
        if (head == null) return head;
        if (p.val > b.val) {
            TreeNode temp = b;
            b = p;
            p = temp;
        }
        Stack<TreeNode> stack = new Stack();
        stack.push(head);
        TreeNode yes = null;
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            Boolean isP = p.val > curr.val;
            Boolean isB = b.val > curr.val;
            if (isB && isP)
                stack.push(head.right);
            else if (!isB && !isP)
                stack.push(head.left);
            else {
                yes = head;
                break;
            }
        }
        return yes;
    }

    public TreeNode findAllparent(TreeNode head, TreeNode p, TreeNode q) {
        if (head == null || p == head || q == head) return head;
        TreeNode left = findAllparent(head.left, p, q);
        TreeNode right = findAllparent(head.right, p, q);
        TreeNode res;
        if (left == null) {
            res = right;
        } else if (right == null) {
            res = left;
        } else
            res = head;
        return res;
    }


    /*
    find parent path
     */
    List<TreeNode> list = new ArrayList<>();

    public TreeNode findAllparentPath(TreeNode head, TreeNode p) {
        if (head == null || p == head) return head;
        TreeNode left = findAllparentPath(head.left, p);
        TreeNode right = findAllparentPath(head.right, p);

        TreeNode res = null;
        if (left != null) {
            list.add(head);
            res = left;
        } else if (right != null) {
            list.add(head);
            res = right;
        }
        return res;
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

        // bfsTree(nodeQueue);
        //bfsTree2(nodeQueue);

        TreeTest test = new TreeTest();
        System.out.println(test.findAllparentPath(head, left_left));
        System.out.println(test.list);
    }

}
