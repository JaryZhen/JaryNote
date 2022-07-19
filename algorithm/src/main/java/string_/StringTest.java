package string_;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: Jary
 * @Date: 2021/8/5 10:09 上午
 */
public class StringTest {

    /**
     * 求字符串所有子序列
     */
    public static List<String> subSequences(String str) {
        char[] chars = str.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        subSequencesProcess(chars, 0, ans, path);
        return ans;
    }

    private static void subSequencesProcess(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        String no = path;
        subSequencesProcess(str, index + 1, ans, no);
        String yes = path + String.valueOf(str[index]);
        subSequencesProcess(str, index + 1, ans, yes);
    }
    /**
     * 求字符串所有子序列 - 不要重复值
     */
    public static Set<String> subSequences_norepeat(String str) {
        char[] chars = str.toCharArray();
        String path = "";
        Set<String> ans = new HashSet<>();
        subProcess_norepeate(chars, 0, ans, path);
        return ans;
    }
    private static void subProcess_norepeate(char[] str, int index, Set<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        String no = path;
        subProcess_norepeate(str, index + 1, ans, no);
        String yes = path + String.valueOf(str[index]);
        subProcess_norepeate(str, index + 1, ans, yes);
    }
    public static void main(String[] args) {

        subSequences("aaad").stream().forEach(System.out::println);
        System.out.println("-------------");
        subSequences_norepeat("aaad").stream().forEach(System.out::println);

    }

}
