package stack_queue;

import java.util.Stack;

/**
 * Created by Jary on 2017/9/7 0007.
 */
public class S_rewers {

    /**
     * 返回并移除当前栈底元素（栈内元素<栈底>1�?2�?3<栈顶>变为2�?3<栈顶>�?.
     */
    private int getAndRemoveBottom(Stack<Integer> stack) {
        int result = stack.pop();
        if (stack.empty()) {
            return result;
        } else {
            int bottom = getAndRemoveBottom(stack);
            stack.push(result);
            return bottom; // 第一轮时，返回栈底元�?1
        }
    }

    /**
     * 每层递归取出栈底的元素并缓存到变量中，直到栈空；
     * <p>
     * 然后逆向将每层变量压入栈，最后实现原栈数据的逆序�?
     *
     * @param stack
     */
    public void reverse(Stack<Integer> stack) {
        if (stack.empty()) {
            return;
        }
        int i = getAndRemoveBottom(stack); // 依次返回1�?2�?3
        reverse(stack);
        stack.push(i); // 依次压入3�?2�?1
    }

    ///////// 测试方法////////
    public static void main(String[] args) {
        Stack stack = new Stack(); // Stack继承Vector，默认容量是10
        stack.push(1);
        stack.push(2);
        stack.push(3);
        S_rewers rStack = new S_rewers();
        rStack.reverse(stack);
        while (!stack.empty()) {
            System.out.println(stack.pop());
        }
    }
}
