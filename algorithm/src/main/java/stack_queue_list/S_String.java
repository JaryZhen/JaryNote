package stack_queue_list;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Jary on 2017/9/12 0012.
 * Given a string containing just the characters'(',')','{','}','['and']', determine if the input string is valid.
 * The brackets must close in the correct order,"()"and"()[]{}"are all valid but"(]"and"([)]"are not.
 */
public class S_String {
    public int jary;

    public static void main(String[] args) {

        String s = "(()[])";
        System.out.println(isRight(s));
    }

    public static boolean isRight(String s) {

        s = s.replace("()", "").replace("[]", "").replace("{}", "");
        Stack<Character> sby = new Stack<>();
        String constants = "])";
        Map<Character, Character> map_con= new HashMap<>();
        char[] bs = s.toCharArray();
        for (int i = 0; i < bs.length; i++) {
            char e = bs[i];

            if (constants.contains(e + "") && !sby.empty()) {
                Character p = sby.peek();
                char tem = '0';
                switch (e) {
                    case ')':
                        tem = '(';
                        break;
                    case ']':
                        tem = '[';
                }
                if (p == tem) {
                    sby.pop();
                } else {
                    return false;
                }
            } else {
                sby.push(e);
            }
        }

        if (sby.isEmpty()) return true;
        else return false;
    }
}

