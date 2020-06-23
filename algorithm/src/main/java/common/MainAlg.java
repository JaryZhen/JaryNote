package common;

/**
 * @Author: Jary
 * @Date: 2020/5/11 3:45 PM
 */
public class MainAlg {
    public static void main(String[] args) {

        System.out.println(lengthOfLongestSubstring("abc"));
    }

    //最长无重复子串
    public static int sutr(String s) {
        int maxLength = 1;
        char[] charS = s.toCharArray();

        if (charS.length == 0 || charS.length == 1) {
            return charS.length;
        }

        char[] suchar = new char[10];
        for (int i = 0; i < charS.length; i++) {
            char a = charS[i];
            for (int j = i + 1; j < charS.length - i; j++) {
                char b = charS[j];
                if (a != b) {

                    maxLength++;
                } else {
                    break;
                }
            }
        }

        return maxLength;
    }

    public static int lengthOfLongestSubstring(String s) {
        char[] chars = s.toCharArray();//Converts to an array of characters
        int i;
        int Max = 1;//The final result
        int max1 = 1;//Ends with the last character
        if (chars.length == 0 || chars.length == 1) {//Special case 1
            return chars.length;
        }
        for (i = 1; i < chars.length; i++) {//Dynamic programming
            int k = i - max1;
            for (int j = i - 1; j >= k; j--) {
                if (chars[i] == chars[j]) {//Find the same update pop up
                    max1 = i - j;
                    break;
                }
                if (j == i - max1) {//update
                    max1++;
                }
            }
            if (max1 > Max) {//Whether or not to update
                Max = max1;
            }
        }

        return Max;
    }

}
