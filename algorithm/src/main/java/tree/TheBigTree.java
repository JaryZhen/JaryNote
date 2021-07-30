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

    /**
     * 2. 给定一个头结点X
     * 求任意节点之间最大距离
     * <p>
     * 分析：
     * 。。。1. 与X 无关 -> max（左子树最大距离 或 柚子树最大距离）
     * 。。。1. 与X 有关 左子树高度加柚子树高度
     */
    class InfoH {
        Integer distence;//左、右最大距离
        Integer hight; // 高度

        public InfoH(Integer distence, Integer hight) {
            this.hight = hight;
            this.distence = distence;
        }
    }

    public Integer maxDistens(TreeNode node) {
        InfoH h = maxDistensR(node);
        return h.distence;
    }

    public InfoH maxDistensR(TreeNode node) {
        if (node == null)
            return new InfoH(1, 0);
        InfoH left = maxDistensR(node.left);
        InfoH right = maxDistensR(node.right);

        int dis = Math.max(left.distence + 1, right.distence + 1);
        int he = left.hight + 1 + right.hight + 1 + 1;

        if (he < dis) { //无关： 左边最大距离或右边最大距离
            return new InfoH(dis, he);
        } else {//有关 ：左右高度之和
            return new InfoH(he, Math.max(left.hight, right.hight) + 1);
        }
    }
}
