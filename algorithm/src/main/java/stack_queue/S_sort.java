package stack_queue;

import java.util.Stack;

/**
 * Created by Jary on 2017/9/9 0009.
 */
// 用一个栈排序另一个栈
public class S_sort {
    public void sort(Stack<Integer> stack) {

        Stack<Integer> help = new Stack<Integer>();

        while (!stack.empty()) {
            int tem = stack.pop();
            while (!help.empty() && tem < help.peek()) {
                stack.push(help.pop());
            }
            help.push(tem);
        }
        while (!help.empty()) {
            stack.push(help.pop());
        }
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(3);
        stack.push(6);
        stack.push(1);
        stack.push(9);
        stack.push(4);
        //System.out.print(stack.peek());
        S_sort rStack = new S_sort();
        rStack.sort(stack);
        for (Integer a : stack) {
            System.out.print(a + " sdfgs梵蒂冈的");
        }
    }
}
