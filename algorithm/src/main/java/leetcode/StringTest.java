package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Jary
 * @Date: 2020/6/30 2:07 PM
 */
public class StringTest {

    public static void main(String[] args) {
        StringTest test = new StringTest();
        //test.generateParenthesis(3);
        test.gen(3);
    }

    public void gen(int n) {
        List<String> lis = new ArrayList<>();
        _gen(0, 0, n, "", lis);
        System.out.println(lis);
    }

    public void _gen(int left, int right, int n, String result, List<String> lis) {
        System.out.println(left + " " + right + " " + n + " " + result);
        if (left == n && right == n) {
            lis.add(result);
            System.out.println("add: " + left + " " + right + " " + result);
            return;
        }
        if (left < n) {
            _gen(left + 1, right, n, result + "(", lis);
        }
        if (left > right && right < n) {
            _gen(left, right + 1, n, result + ")", lis);
        }
    }

    /**
     * 22.数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
     * 示例：
     * 输入：n = 3
     * 输出：[
     * "((()))",
     * "(()())",
     * "(())()",
     * "()(())",
     * "()()()"
     * ]
     */


    public List<String> generateParenthesis(int n) {

        List<String> lis = new ArrayList<>();
        generat(0, 0, n, "", lis);
        System.out.println(lis);
        return lis;
    }

    public void generat(int left, int right, int n, String kuo, List<String> lis) {
        if (left == n && right == n) {
            lis.add(kuo);
            return;
        }
        if (left < n)
            generat(left + 1, right, n, kuo + "(", lis);
        if (right < n && left > right)
            generat(left, right + 1, n, kuo + ")", lis);
    }
}
