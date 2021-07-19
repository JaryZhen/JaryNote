package tree;

import basc.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * @Author: Jary
 * @Date: 2021/7/2 11:02 上午
 */
public class ValidBST {

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

    Stack<TreeNode> stack = new Stack<>();

    /*
     中根遍历验证
     */
    public boolean bfsTreeMid(TreeNode head) {
        if (head == null) return true;
        Boolean isM = true;
        boolean l = bfsTreeMid(head.left);
        System.out.println(head.val);
        if (stack.isEmpty()) {
            stack.add(head);
        } else {
            isM = stack.pop().val < head.val;
            stack.add(head);
        }
        boolean r = bfsTreeMid(head.right);
        return isM && l && r;
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

        ValidBST test = new ValidBST();
        //test.testDfsTree();
        System.out.println(test.bfsTreeMid(head));
    }
}
