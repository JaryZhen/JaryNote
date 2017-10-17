package stack_queue;

import java.util.Stack;

/**
 * Created by Jary on 2017/9/4 0004.
 */

/*
使用stack 设计实现�?个getMin功能
 */
public class S_Getmin {
    private Stack<Integer> stackA = null;
    private Stack<Integer> min = null;

    S_Getmin() {
        this.stackA = new Stack<Integer>();
        this.min = new Stack<Integer>();
    }

    public void push(int a) {
        if (this.min.empty()) {
            this.min.push(a);
        } else if (a <= this.getMin()) {
            this.min.push(a);
        }
        this.stackA.push(a);
    }

    public int pop() {
        if (this.stackA.empty()) {
            throw new NullPointerException("null ....");
        }
        int val = this.stackA.pop();
        if (val == this.getMin()) {
            this.min.pop();
        }
        return val;
    }

    public int getMin() {
        if (this.min.empty()) throw new NullPointerException("");
        return this.min.peek();
    }

}
