package tree;

import basc.TreeNode;
import lombok.val;

/**
 * @Author: Jary
 * @Date: 2021/7/30 11:07 上午
 */
public class TheBigTree {
    /**
     * 二叉树的终极武器:
     * 。。。0。确定是否跟头节点有关
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

    private class Info {
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

    private Info isFullTree(TreeNode head) {
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
    private class InfoDistence {
        Integer distence;//左、右最大距离
        Integer hight; // 高度

        public InfoDistence(Integer distence, Integer hight) {
            this.hight = hight;
            this.distence = distence;
        }
    }

    public Integer maxDistens(TreeNode node) {
        InfoDistence h = maxDistensR(node);
        return h.distence;
    }

    private InfoDistence maxDistensR(TreeNode node) {
        if (node == null)
            return new InfoDistence(0, 0);
        InfoDistence left = maxDistensR(node.left);
        InfoDistence right = maxDistensR(node.right);

        int he = Math.max(left.hight, right.hight) + 1;
        int dis = Math.max(left.hight + right.hight + 1, Math.max(left.distence, right.distence));
        return new InfoDistence(dis, he);
    }

    /**
     * 3.1 返回 最大二叉搜索树的节点数量
     * 。。。1. 与X无关
     * 。。。。。。。在左边或右边
     * 。。。2. 与X有关
     * 。。。。。。。左右都是搜索二叉树且左边小于X 右边大于X
     * <p>
     * 3.2 返回 最大二叉搜索树的节点数量节点
     */

    private class InfoBst {
        int size;//最大搜索子树个数
        boolean isBst;//是否为搜索树
        int max;//子树最大值
        int min;//子树最小值

        public InfoBst(int size, boolean isBst, int max, int min) {
            this.size = size;
            this.isBst = isBst;
            this.max = max;
            this.min = min;
        }
    }

    public int maxBST(TreeNode head) {
        InfoBst bst = maxBSTR(head);
        return bst.size;
    }

    private InfoBst maxBSTR(TreeNode node) {
        if (node == null)
            return null;// new InfoBst(0, true, -1, -1);// 空节点 是否是最大搜索子树
        InfoBst left = maxBSTR(node.left);
        InfoBst right = maxBSTR(node.right);

        int max = node.val;
        int min = node.val;
        int size = 0;
        if (left != null) {
            min = min > left.min ? min : left.min;
            max = max > left.max ? max : left.max;
            size = left.size;
        }
        if (right != null) {
            min = min > right.min ? min : right.min;
            max = max > right.max ? max : right.max;
            size = Math.max(size, right.size);
        }

        boolean isb = false;
        if (left == null ? true : left.isBst
                && right == null ? true : right.isBst
                && left == null ? true : left.max < node.val
                && right == null ? true : node.val < right.min) {
            size = (left == null ? 0 : left.size) + (right == null ? 0 : right.size) + 1;
            isb = true;
        }
        return new InfoBst(size, isb, max, min);
    }
}
