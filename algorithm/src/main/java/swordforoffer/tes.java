package swordforoffer;

import javax.swing.text.rtf.RTFEditorKit;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Jary on 2018/3/8 0008.
 */
public class tes {
    public static void main(String[] args) {
        String a = "()a()()";
        int le = 7;
      //  System.out.println(""+chkParenthesis(a, le));


        TreeNode l = new TreeNode(2);
        l.left = new TreeNode(4);
        l.right = new TreeNode(5);
        TreeNode r = new TreeNode(3);
        r.right = new TreeNode(7);
        r.left = new TreeNode(6);

        TreeNode root = new TreeNode(1);
        root.left = l;
        root.right = r;

        //zhongxu(root);
        levelRead(root);
    }

    public static boolean chkParenthesis(String A, int n) {
        if (A == null || n == 0) {
            return false;
        }
        char[] chas = A.toCharArray();
        int status = 0;
        for (int i = 0; i < n; i++) {
            if (chas[i] != ')' && chas[i] != '(') {
                return false;
            }
            if (chas[i] == ')' && --status < 0) {
                return false;
            }
            if (chas[i] == '(') {
                status++;
            }
        }
        return status == 0;
    }
    public int longestSubstring(String A, int n) {
        if (A == null || n == 0) {
            return 0;
        }
        char[] chas = A.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < 256; i++) {
            map[i] = -1;
        }
        int len = 0;
        int pre = -1;
        int cur = 0;
        for (int i = 0; i < n; i++) {
            pre = Math.max(pre, map[chas[i]]);
            cur = i - pre;
            len = Math.max(len, cur);
            map[chas[i]] = i;
        }
        return len;
    }

    private static void xianxu( TreeNode root){
        if (root != null){
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);
            while (!stack.empty()){
                root = stack.pop();
                System.out.println(""+root.val);
                if (root.right != null){
                    stack.push(root.right);
                }
                if (root.left != null){
                    stack.push(root.left);
                }
            }

        }
    }

    private static void zhongxu(TreeNode root){
        if (root !=null){
            Stack<TreeNode> stack = new Stack<>();
            while (!stack.empty() || root != null){
                if (root != null ){
                    stack.push(root);
                    root = root.left;
                }else {
                    root = stack.pop();
                    System.out.println(""+root.val);
                    root = root.right;
                }
            }
        }
    }
    public static void levelRead(TreeNode root)
    {
        if(root == null) return;
        Queue<TreeNode> queue = new LinkedList<TreeNode>() ;
        queue.add(root);
        while(queue.size() != 0)
        {
            int len = queue.size();
            for(int i=0;i <len; i++)
            {
                TreeNode temp = queue.poll();
                System.out.print(temp.val+" ");
                if(temp.left != null)  queue.add(temp.left);
                if(temp.right != null) queue.add(temp.right);
            }
        }
    }

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}
