import basc.ListNode;
import stack_queue.Rewers;

import java.util.List;
import java.util.Stack;

/**
 * @Author: Jary
 * @Date: 2021/6/19 3:36 下午
 */
public class Test {


    public static void main(String[] args) {
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

    public ListNode ReverseList(ListNode head) {
        Stack a = new Stack();
        boolean has = true;
        while (has) {
            a.push(head.data);
            System.out.println("push " + head.data);
            head = head.next;
            if (head == null)
                has = false;
        }

        ListNode node = new ListNode(a.pop(), null, null);
        while (!a.empty()) {

            System.out.println("p " + a.peek());
            ListNode next = new ListNode(a.pop(), null, null);
            ;
            node.next = next;
            node = next;
        }
        return node;
    }


    public ListNode reversList(ListNode head) {
      ListNode pre = null;
      ListNode curr = head;

      while (curr != null){
          ListNode next = curr.next;
          curr.next = pre;
          pre = curr;
          curr = next;
      }
      return pre;
    }

}
