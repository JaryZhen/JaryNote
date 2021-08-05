package stack_queue_list;

import java.util.Stack;


/**
 * @Author: Jary
 * @Date: 2021/7/19 10:39 上午
 */
public class StackQueueTest {

    public static void re(Stack<Integer> stack) {
        if (!stack.isEmpty()) {
            int a = stack.pop();
            re(stack);
            stack.add(a);
        }
        //stack.push(stack.pop());
    }

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();
        stack.add(3);
        stack.add(2);
        stack.add(1);
        re(stack);
    }
}









