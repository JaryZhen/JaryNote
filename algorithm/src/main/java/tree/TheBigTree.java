package tree;

import basc.TreeNode;

/**
 * @Author: Jary
 * @Date: 2021/7/30 11:07 上午
 */
public class TheBigTree {
    /**
     * 二叉树的终极武器:
     * 。。。1.处理左孩子信息
     * 。。。2.处理右孩子信息
     * 。。。3.处理叶子结点和头结点信息信息
     * 搞定
     * <p>
     * 已验证 是否为完全二叉树为例：
     * 一颗完全二叉树所满足的要求是
     * 。。。1. 左右子树高度
     * 。。。2. 左右子树是否平衡
     */

    class Info {
        Boolean isBalence;
        Integer height;
        public Info(Boolean isBalence, Integer height) {
            this.height = height;
            this.isBalence = isBalence;
        }
    }

    public Boolean ifFull(TreeNode he) {
        Info info = isFullTree(he);
        return info.isBalence;
    }

    public Info isFullTree(TreeNode head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Boolean isb = false;
        Info leftInfo = isFullTree(head.left);
        Info rightInfo = isFullTree(head.right);

        if (leftInfo.isBalence && rightInfo.isBalence && Math.abs(leftInfo.height - rightInfo.height) <= 1) {
            isb = true;
        }
        int hei = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(isb, hei);
    }

}
