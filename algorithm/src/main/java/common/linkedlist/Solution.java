package common.linkedlist;

/**
 * @Author: Jary
 * @Date: 2020/5/15 9:46 AM
 */
public class Solution {

    public static void main(String[] args) {

      /*  ListNode self = swapPairs(getNode());

        while (self != null) {
            System.out.println(self.val);
            self = self.next;
        }*/

        reverseList(getNode());

        int[] alis = new int[6];
        alis[0] = 1;
        alis[1] = 2;
        alis[2] = 3;
        alis[3] = 4;

    }

    public static ListNode reverseList(ListNode head) {
        if (head != null){
            ListNode tmp =head;

            System.out.println(head.data);
            return reverseList(head.next);

        }
        return head;
    }
    public static ListNode swapPairs(ListNode head) {
        // Dummy node acts as the tmpNode for the head node
        // of the list and hence stores pointer to the head node.
        ListNode dummy = new ListNode("-1", null, null);
        dummy.next = head;

        ListNode tmpNode = dummy;

        while ((head != null) && (head.next != null)) {

            // Nodes to be swapped
            ListNode firstNode = head;
            ListNode secondNode = head.next;

            // Swapping
            tmpNode.next = secondNode;
            firstNode.next = secondNode.next;
            secondNode.next = firstNode;

            // Reinitializing the head and tmpNode for next swap
            tmpNode = firstNode;
            head = firstNode.next; // jump
        }

        // Return the new head node.
        return dummy.next;
    }

    private static ListNode<String> getNode() {
        ListNode<String> t = new ListNode("3", null, null);
        ListNode<String> s = new ListNode("2", null, t);
        ListNode<String> f = new ListNode("1", null, s);
        ListNode<String> self = new ListNode("0", null, f);
        return self;
    }

}
