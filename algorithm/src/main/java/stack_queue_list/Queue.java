package stack_queue_list;

import java.util.Stack;

/**
 * Created by Jary on 2017/12/6 0006.
 */
public class Queue<K> {
    private Stack<K> data;
    private Stack<K> re;

    Queue() {
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
        Queue<String> queue = new Queue<String>();
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
