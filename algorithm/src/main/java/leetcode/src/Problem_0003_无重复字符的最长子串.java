package leetcode.src;

import java.util.HashMap;

/**
 * @author Jary
 * @data 2020/11/18 21:49
 */
public class Problem_0003_无重复字符的最长子串 {

    public int lengthOfLongestSubstring(String array) {
        char[] chars = array.toCharArray();
        int max = 0;
        if (array == null || array.length() == 0)
            return 0;
        HashMap<Character, Integer> map = new HashMap();
        int start = 0;
        for (int i = 0; i < chars.length; i++) {
            if (map.containsKey(chars[i])) {
                int c = map.get(chars[i]);
                if (c >= start)
                    start = map.get(chars[i]) + 1;
            }
            map.put(chars[i], i);
            max = Math.max(max, i - start + 1);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(new Problem_0003_无重复字符的最长子串().lengthOfLongestSubstring("abcabcbb"));
    }
}
