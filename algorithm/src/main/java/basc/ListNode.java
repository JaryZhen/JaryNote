package basc;

/**
 * Created by Jary on 2017/8/28 0028.
 */
public class ListNode<T> {
    public T val;
    public ListNode<T> last;
    public ListNode<T> next;

    public ListNode(T data, ListNode<T> last, ListNode<T> next) {
        this.val = data;
        this.last = last;
        this.next = next;
    }
    public ListNode() {
        this(null, null, null);
    }
    public ListNode(T val) {
        this(val, null, null);
    }

    public ListNode(T val, ListNode<T> next) {
        this(val, null, next);
    }

    //4、Node类可声明以下方法：
    @Override
    public String toString() {
        return this.val.toString();
    }

    //比较两个结点值是否相等，覆盖Object类的equals(obj)方法
    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ListNode && this.val.equals(((ListNode<T>) obj).val);
    }
}
