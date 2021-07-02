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
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p>
     * [
     * [3],
     * [9,20],
     * [15,7]
     * ]
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
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     * <p>
     * 说明: 叶子节点是指没有子节点的节点。
     * <p>
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     * <p>
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
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

    /*
    98. 验证二叉搜索树
给定一个二叉树，判断其是否是一个有效的二叉搜索树。

假设一个二叉搜索树具有如下特征：
节点的左子树只包含小于当前节点的数。
节点的右子树只包含大于当前节点的数。
所有左子树和右子树自身必须也是二叉搜索树。
示例 1:

输入:
    2
   / \
  1   3
输出: true
示例 2:

输入:
    5
   / \
  1   4
     / \
    3   6
输出: false
解释: 输入为: [5,1,4,null,null,3,6]。
     根节点的值为 5 ，但是其右子节点值为 4 。
     */
    public boolean isValidBST(TreeNode root) {
        return recurse(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean recurse(TreeNode node, Integer left_max, Integer right_min) {
        if (node == null) return true;
        int val = node.val;
        if (val <= left_max || val >= right_min) return false;

        return recurse(node.left, left_max, val) && recurse(node.right, val, right_min);
    }

    public boolean isValidBST(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        return isValidBST(node.left, lower, node.val) && isValidBST(node.right, node.val, upper);
    }

    public boolean isValidBST2(TreeNode root) {
        Deque<TreeNode> stack = new LinkedList<TreeNode>();
        double inorder = -Double.MAX_VALUE;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // 如果中序遍历得到的节点的值小于等于前一个 inorder，说明不是二叉搜索树
            if (root.val <= inorder) {
                return false;
            }
            inorder = root.val;
            root = root.right;
        }
        return true;
    }

    public void testIsValidBst() {
        boolean a = 4 > 2 && 4 < 2;
        System.out.println(a);
        TreeNode left_left = new TreeNode(null, null, 3);
        TreeNode left_right = new TreeNode(null, null, 5);

        TreeNode right_left = new TreeNode(null, null, 6);
        TreeNode right_right = new TreeNode(null, null, 13);

        TreeNode left = new TreeNode(left_left, left_right, 4);
        TreeNode right = new TreeNode(right_left, right_right, 12);

        TreeNode head = new TreeNode(left, right, 8);
        System.out.println(isValidBST2(head));
    }

    public static void main(String[] args) {
        TreeTest test = new TreeTest();
        //test.testDfsTree();
        test.testIsValidBst();
    }

    public void testDfsTree() {
        TreeNode t_3 = new TreeNode(null, null, 3);
        TreeNode t_5 = new TreeNode(null, null, 5);

        TreeNode t_7 = new TreeNode(null, null, 7);
        TreeNode t_9 = new TreeNode(null, null, 9);

        TreeNode t_0 = new TreeNode(null, null, 0);
        TreeNode t_4 = new TreeNode(t_3, t_5, 4);
        TreeNode t_2 = new TreeNode(t_0, t_4, 2);
        TreeNode t_8 = new TreeNode(t_7, t_9, 8);
        TreeNode root_6 = new TreeNode(t_2, t_8, 6);

        //dfsTree(root_6);
        //bfsTree(root_6);

        Queue<TreeNode> nodeQueue = new ArrayDeque();
        nodeQueue.add(root_6);
        // bfsTree(nodeQueue);
        //bfsTree2(nodeQueue);

        System.out.println(maxDepth2(root_6));
    }


}
