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
     * 将单链表按基数分 左边小 中间 右边大
     * 1. 放数组里，在数组上partion
     * 2. 分三部分 再链接
     */

    public ListNode split(ListNode<Integer> head, int val) {
        ListNode<Integer> lessHead = null;
        ListNode<Integer> lessTail = null;
        ListNode<Integer> midHead = null;
        ListNode<Integer> midTail = null;
        ListNode<Integer> moreHead = null;
        ListNode<Integer> moreTail = null;

        ListNode next = null;
        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.val < val) {
                if (lessHead == null) {
                    lessHead = head;
                    lessTail = head;
                } else {
                    lessTail.next = head;
                    lessTail = head;
                }
            } else if (head.val == val) {
                if (midHead == null) {
                    midHead = head;
                    midTail = head;
                } else {
                    midHead.next = head;
                    midTail = head;
                }
            } else {
                if (moreHead == null) {
                    moreHead = head;
                    moreTail = head;
                } else {
                    moreTail.next = head;
                    moreTail = head;
                }
            }
            head = next;
        }
        if (lessHead != null) {
            if (midHead != null) {
                lessTail.next = midHead;
                midTail.next = moreHead;
                return lessHead;
            } else {
                lessTail.next = moreHead;
                return lessHead;
            }
        } else {
            if (midHead != null) {
                midTail.next = moreHead;
                return midHead;
            }
        }
        return moreHead;
    }

    /**
     * 1. 证明 节点是不是有环 --> 快慢指针 isLoopNode()
     * 2. 如果有环，请找出环相交点 getLoopNode()
     * ....a。快慢指针
     * ....b。让快指针从头开始
     */
    public ListNode getLoopNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null)
            return null;
        ListNode slow = head.next;
        ListNode fast = head.next.next;
        while (slow != fast) {
            if (fast == null || head.next == null)
                return null;
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ListTest test = new ListTest();
        //测试多少次
        for (int j = 0; j < 1; j++) {
            // 构造数据
            ListNode<Integer> listNode = new ListNode(1, null, null);
            ListNode head = listNode;
            ListNode tail = listNode;
            int lage = new Random().nextInt(15);
            System.out.print("size: " + (lage + 1) + ": ");
            for (int i = 0; i < lage; i++) {
                ListNode temp = new ListNode(new Random().nextInt(15), null, null);
                tail.next = temp;
                tail = temp;
            }
            ListNode indexHe = head;
            while (indexHe != null) {
                System.out.print(indexHe.val + " -> ");
                indexHe = indexHe.next;
            }
            System.out.println();

            /**
             * hers test
             */
            //System.out.println("中点： " + test.midNode(head));

           /* ListNode re = test.split(head, 1);
            while (re != null) {
                System.out.println("re: " + re.data);
                re = re.next;
            }*/


        }
    }
}
