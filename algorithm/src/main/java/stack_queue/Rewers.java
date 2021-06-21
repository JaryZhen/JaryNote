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
            return bottom;
        }
    }

    public void reverse(Stack<Integer> stack) {
        if (stack.empty()) {
            return;
        }
        int i = getAndRemoveBottom(stack); // 渚濇杩斿洖1銆?2銆?3
        reverse(stack);
        stack.push(i); // 渚濇鍘嬪叆3銆?2銆?1
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
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
