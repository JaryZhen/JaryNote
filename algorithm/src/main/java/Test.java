import basc.ListNode;
import stack_queue.Rewers;

import java.util.*;

/**
 * @Author: Jary
 * @Date: 2021/6/19 3:36 下午
 */
public class Test {
    public void ReverseList(ListNode head) {
        Stack stack = new Stack();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        // Rewers rStack = new Rewers();
        //rStack.reverse(stack);
        ListNode listNode = new ListNode("1", null,
                new ListNode("2", null,
                        new ListNode("3", null, null)));
        Test t = new Test();
        ListNode re = t.reversList(listNode);
        boolean hs = true;
        while (hs) {
            System.out.println(re.data);
            re = re.next;
            if (re == null)
                hs = false;
        }
    }

    public ListNode reversList(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public static void heap() {
        PriorityQueue a = new PriorityQueue(2);
        a.add(10);
        a.add(23);
        a.add(6);
        System.out.println(a.size());
        System.out.println(a.poll());
    }

    public static int[] maxSliding(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        if (nums.length == 0) return result;

        for (int i = 0; i < nums.length - k + 1; i++) {
            int max = nums[i];
            for (int j = i + 1; j < i + k; j++) {
                if (nums[j] > max)
                    max = nums[j];
            }
            result[i] = max;
        }
        return result;
    }

    public static int[] maxSliding2(int[] nums, int k) {
        int[] q = new int[100010];
        int hh = 0, tt = -1;
        int[] res = new int[nums.length - k + 1];
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (tt >= hh && q[hh] < i - k + 1) hh++;
            while (tt >= hh && nums[q[tt]] <= nums[i]) tt--;
            q[++tt] = i;
            if (i >= k - 1) res[j++] = nums[q[hh]];
        }
        return res;
    }

    public static int[] maxSliding3(int[] nums, int k) {
        if(nums == null || nums.length < 2) return nums;
        // 双向队列 保存当前窗口最大值的数组位置 保证队列中数组位置的数值按从大到小排序
        LinkedList<Integer> queue = new LinkedList();
        // 结果数组
        int[] result = new int[nums.length-k+1];
        // 遍历nums数组
        for(int i = 0;i < nums.length;i++){
            // 保证从大到小 如果前面数小则需要依次弹出，直至满足要求
            while(!queue.isEmpty() && nums[queue.peekLast()] <= nums[i]){
                queue.pollLast();
            }
            // 添加当前值对应的数组下标
            queue.addLast(i);
            // 判断当前队列中队首的值是否有效
            if(queue.peek() <= i-k){
                queue.poll();
            }
            // 当窗口长度为k时 保存当前窗口中最大值
            if(i+1 >= k){
                result[i+1-k] = nums[queue.peek()];
            }
        }
        return result;
    }

    public static void maxSliding() {
        int[] ints = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int[] re = maxSliding3(ints, 3);
        for (int i = 0; i < re.length; i++) {
            System.out.println(re[i]);
        }
    }

    public static void main(String[] args) {
        maxSliding();
    }
}
