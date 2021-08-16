package leetcode.src;

/**
 * @author cuilihuan
 * @data 2020/11/21 18:31
 */
public class Problem_0019_删除链表的倒数第N个节点 {
    //a->b->c->d->e
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n == 0)
            return head;
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            n--;
            if (n == -1) {
                pre = head;
            }
            if (n < -1) {
                pre = pre.next;
            }
            cur = cur.next;
        }
        if (n > 0)
            return head;
        if (pre == null)
            return head.next;
        pre.next = pre.next.next;
        return head;
    }

    public basc.ListNode removeNthFromEnd2(basc.ListNode head, int n) {
        basc.ListNode dumm = new basc.ListNode(0);
        dumm.next = head;
        basc.ListNode slow = dumm;
        basc.ListNode fast = dumm;

        for (int i = 0; i < n+1; i++) {
            fast = fast.next;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dumm.next;
    }
}
