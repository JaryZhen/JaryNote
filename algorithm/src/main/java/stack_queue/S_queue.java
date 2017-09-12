package stack_queue;

import java.util.Stack;

/**
 * Created by Jary on 2017/9/4 0004.
 */

/*
    由两个栈组成的队列
 */
public class S_queue<K> {
    private Stack<K> data;
    private Stack<K> re;

    S_queue() {
        this.data = new Stack<K>();
        this.re = new Stack<K>();
    }
    public void add(K ele) {
        data.push(ele);
    }

    public K poll() {
        if (this.re.empty()) {
            while (!this.data.empty()) {
                this.re.push(this.data.pop());
            }
        }
        return this.re.pop();
    }

    public K peek() {
        if (this.re.empty()) {
            while (this.data.empty()) {
                this.re.push(this.data.pop());
            }
        }
        return this.re.peek();
    }

    public static void main(String[] args) {
        S_queue<String> queue = new S_queue<String>();
        System.out.print("Push: ");
        char ch = 'a';
        for (int i = 0; i < 5; i++) {
            String str = (char) (ch + i) + "";
            queue.add(str);
            System.out.print(str + "  ");
        }
        System.out.print(queue.poll());
    }
}
