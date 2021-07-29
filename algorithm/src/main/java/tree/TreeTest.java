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
     * 深度优先遍历 - 递归的
     *
     * @param root
     */
    public static void dfsTree(TreeNode root) {
        if (root == null || root.left == null || root.right == null) return;
        System.out.println(root.left.val);//先序
        dfsTree(root.left);
        System.out.println(root.left.val);//中序
        dfsTree(root.right);
        System.out.println(root.right.val);//后序
    }

    /**
     * 深度优先遍历 - 非递归的  1. 先
     *
     * @param node
     */
    public static void dfsTreeNonRecursiveFirs(TreeNode node) {
        System.out.println("firs:");
        Stack<TreeNode> stack = new Stack<>();
        if (node == null)
            return;
        stack.add(node);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            System.out.print(curr.val + " ");
            if (curr.right != null)
                stack.add(curr.right);
            if (curr.left != null)
                stack.add(curr.left);
        }
    }

    /**
     * 深度优先遍历 - 非递归的  2. 中
     *
     * @param node
     */
    public static void dfsTreeNonRecursiveMid(TreeNode node) {
        if (node == null)
            return;
        Stack<TreeNode> stack = new Stack<>();
        while (!stack.isEmpty() || node != null) {
            if (node != null) {// 不空 压入节点 处理左节点
                stack.add(node);
                node = node.left;
            } else { //为空 弹出节点 再处理右节点
                node = stack.pop();
                System.out.print(node.val + " ");
                node = node.right;
            }
        }
    }

    /**
     * 深度优先遍历 - 非递归的 3. 后
     *
     * @param node
     */
    public static void dfsTreeNonRecursiveLast(TreeNode node) {
        if (node == null)
            return;
        System.out.println("\nlast: ");
        Stack<TreeNode> stack = new Stack<>();
        Stack<TreeNode> restack = new Stack<>();
        stack.add(node);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            restack.add(curr);
            if (curr.left != null)
                stack.add(curr.left);
            if (curr.right != null)
                stack.add(curr.right);
        }
        while (!restack.isEmpty()) {
            System.out.print(restack.pop().val + " ");
        }
    }

    /**
     * 循环的广度优先遍历,按层打印
     */
    public static void bfsTree2(TreeNode node) {
        Queue<TreeNode> queue = new ArrayDeque();
        int level = 0;
        queue.add(node);
        while (!queue.isEmpty()) {
            System.out.println("level " + level);
            int si = queue.size();
            // 打印当前这一层所有元素
            for (int i = 0; i < si; i++) {
                TreeNode curr = queue.poll();
                if (curr.left != null) {
                    System.out.print(curr.left.val + "\t");
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    System.out.print(curr.right.val + "\t");
                    queue.add(curr.right);
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
    static List<TreeNode> list = new ArrayList<>();

    public static TreeNode findAllparentPath(TreeNode head, TreeNode p) {
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
        TreeNode left_2 = new TreeNode(left_left, left_right, 4);
        TreeNode right_2 = new TreeNode(right_left, right_right, 12);
        TreeNode head = new TreeNode(left_2, right_2, 8);
        /*
              8
            /   \
           4     12
          / \    / \
         3   5  6  13

        */


        //dfsTree(head);
        //dfsTreeNonRecursiveFirs(head);
        //dfsTreeNonRecursiveMid(head);
        //dfsTreeNonRecursiveLast(head);

        //bfsTree2(head);
        //System.out.println(findAllparentPath(head, left_left));
        //System.out.println(list);
    }

}
