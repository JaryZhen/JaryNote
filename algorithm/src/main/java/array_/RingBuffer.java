package array_;

import java.util.Arrays;

/**
 * @Author: Jary
 * @Date: 2021/7/20 3:01 下午
 */
public class RingBuffer {
    int[] queue = null;
    int in = 0, out = 0, size = 0, limit = 0;

    public RingBuffer(int len) {
        queue = new int[len];
        this.limit = len;
    }

    public void push(int x) {
        if (size == limit)
            throw new RuntimeException("manx " + toString());
        queue[in] = x;
        if (++in >= limit)
            in = 0;
        size++;
    }

    public int pop() {
        if (size == 0)
            throw new RuntimeException("o " + toString());
        int re = queue[out];
        if (++out >= limit)
            out = 0;
        size--;
        return re;
    }

    @Override
    public String toString() {
        return "RingBuffer{" +
                "queue=" + Arrays.toString(queue) +
                ", in=" + in +
                ", out=" + out +
                ", size=" + size +
                ", limit=" + limit +
                '}';
    }
    public static void main(String[] args) {
        RingBuffer r = new RingBuffer(4);
        r.push(1);
        r.push(2);
        r.push(3);
        r.push(4);
        System.out.println(r.pop());
        r.push(5);
        System.out.println(r.pop());
        System.out.println(r.pop());
        System.out.println(r.pop());
        System.out.println(r.pop());
        System.out.println(r.pop());
        r.push(6);
        r.push(7);
        r.push(8);
        r.push(9);

        System.out.println(r.toString());
    }
}
