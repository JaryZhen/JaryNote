package leetcode;

import java.util.*;

/**
 * @Author: Jary
 * @Date: 2020/6/2 10:25 AM
 */
public class Leetcode {
    public static void main(String[] args) {
        Leetcode lee = new Leetcode();
        //lee.testlowestCommonAncestor();
        //testTwoSum();
        //isAnagram();
    }

    public void testlowestCommonAncestor() {
        TreeNode t_3 = new TreeNode(null, null, 3);
        TreeNode t_5 = new TreeNode(null, null, 5);

        TreeNode t_7 = new TreeNode(null, null, 7);
        TreeNode t_9 = new TreeNode(null, null, 9);

        TreeNode t_0 = new TreeNode(null, null, 0);
        TreeNode t_4 = new TreeNode(t_3, t_5, 4);
        TreeNode t_2 = new TreeNode(t_0, t_4, 2);
        TreeNode t_8 = new TreeNode(t_7, t_9, 8);
        TreeNode root_6 = new TreeNode(t_2, t_8, 6);

        System.out.println(lowestCommonAncestor(root_6, t_3, t_5, "root").val);


        StackTraceElement stack[] = Thread.currentThread().getStackTrace();

        for (int i = 0; i < stack.length; i++) {
            System.out.print(stack[i].getClassName() + " ." + stack[i].getMethodName() + "-----");
        }
    }

    /**
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * <p>
     * 百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，
     * 满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * <p>
     * 例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5]
     * <p>
     * <p>
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q, String di) {

        if (root == null || root.val == p.val || root.val == q.val) {
            if (root == null) {
                System.out.println(di + " val: " + " null");
            } else System.out.println(di + " val: " + root.val);
            return root;
        }
        System.out.println(di + " val: " + root.val);
        TreeNode left = lowestCommonAncestor(root.left, p, q, "left");
        TreeNode right = lowestCommonAncestor(root.right, p, q, "right");
        //return left == null ? right : right == null ? left : root;

/*
        if (left == null)
            return right;
        else if (right == null) {
            return left;
        } else return root;
*/

        if (left != null && right != null) return root;
        if (left != null)
            return left;
        else if (right != null)
            return right;
        else return null;
    }

    /**
     * 异位词
     */
    public static void isAnagram() {
        //;System.out.println(isAnagram("anagram", "nagaram"));
        System.out.println(isAnagram("aaac", "accc"));
        int a = 1;
        System.out.println(++a);
        System.out.println(a);
    }

    public static boolean isAnagram(String s, String t) {
        if (s.isEmpty() && t.isEmpty()) return true;
        if (s.length() != t.length()) return false;

        Map<Character, Integer> charMap = new HashMap<>();
        char[] chars = s.toCharArray();
        char[] chars1 = t.toCharArray();
        boolean is = true;

        for (int i = 0; i < chars.length; i++) {
            int aa = charMap.getOrDefault(chars[i], 0);
            charMap.put(chars[i], ++aa);
        }
        for (int i = 0; i < chars1.length; i++) {
            int aa = charMap.getOrDefault(chars1[i], 0);
            charMap.put(chars1[i], --aa);
            if (++aa <= 0) {
                is = false;
                return is;
            }
        }
        return is;
    }


    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * 示例:
     * <p>
     * 给定 nums = [2, 7, 11, 15], target = 9
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     */
    public static void testTwoSum() {
        int[] nums = {2};
        int target = 9;
        System.out.println(twoSum(nums, target)[0] + " " + twoSum(nums, target)[1]);
    }

    public static int[] twoSum(int[] nums, int target) {
        if (nums.length < 0) return new int[]{-1, -1};

        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            int tem = nums[i];
            int re = target - tem;
            if (map.containsKey(re)) {
                return new int[]{map.get(re), i};
            } else {
                map.put(tem, i);
            }
        }
        return new int[]{-1, -1};
    }
}
