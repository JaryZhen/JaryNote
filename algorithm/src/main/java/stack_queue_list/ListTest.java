package stack_queue_list;

import basc.ListNode;

import java.util.Random;

/**
 * @Author: Jary
 * @Date: 2021/7/27 2:57 下午
 */
public class ListTest {

    /**
     * 快慢指针 - 找中点
     */
    public ListNode midNode(ListNode node) {
        if (node == null || node.next == null || node.next.next == null)
            return node;
        ListNode slow = node.next;
        ListNode fast = node.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * @param args
     */

    public static void main(String[] args) {
        ListTest test = new ListTest();
        //测试多少次
        for (int j = 0; j < 20; j++) {
            // 构造数据
            ListNode listNode = new ListNode("1", null, null);
            ListNode head = listNode;
            ListNode tail = listNode;
            int lage = new Random().nextInt(15);
            System.out.print("size: " + (lage + 1 )+ ": ");
            for (int i = 0; i < lage; i++) {
                ListNode temp = new ListNode(new Random().nextInt(1000) + "", null, null);
                tail.next = temp;
                tail = temp;
            }
            ListNode indexHe = head;
            while (indexHe != null) {
                System.out.print(indexHe.data + " -> ");
                indexHe = indexHe.next;
            }
            System.out.println();

            /**
             * hers test
             */
            System.out.println("中点： " + test.midNode(head));
        }
    }
}
