package stack_queue;

import java.util.Stack;

/**
 * Created by Jary on 2017/12/6 0006.
 */
public class Rewers {
    private int getAndRemoveBottom(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.empty()) {
            return result;
        } else {
            int bottom = getAndRemoveBottom(stack);
            stack.push(result);
            return bottom; // 绗竴杞椂锛岃繑鍥炴爤搴曞厓绱?1
        }
    }

    /**
     * 姣忓眰閫掑綊鍙栧嚭鏍堝簳鐨勫厓绱犲苟缂撳瓨鍒板彉閲忎腑锛岀洿鍒版爤绌猴紱
     * <p>
     * 鐒跺悗閫嗗悜灏嗘瘡灞傚彉閲忓帇鍏ユ爤锛屾渶鍚庡疄鐜板師鏍堟暟鎹殑閫嗗簭銆?
     *
     * @param stack
     */
    public void reverse(Stack<Integer> stack) {
        if (stack.empty()) {
            return;
        }
        int i = getAndRemoveBottom(stack); // 渚濇杩斿洖1銆?2銆?3
        reverse(stack);
        stack.push(i); // 渚濇鍘嬪叆3銆?2銆?1
    }

    ///////// 娴嬭瘯鏂规硶////////
    public static void main(String[] args) {
        Stack stack = new Stack(); // Stack缁ф壙Vector锛岄粯璁ゅ閲忔槸10
        stack.push(1);
        stack.push(2);
        stack.push(3);
        Rewers rStack = new Rewers();
        rStack.reverse(stack);
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }
}
