/**
 * Created by Jary on 2017/9/19 0019.
 */
public class SkipListNode<T> {
    private int key;
    private T value;
    public SkipListNode<T> up, down, left, right;

    public static final int HEAD_KEY = Integer.MIN_VALUE;//负无穷
    public static final int TAIL_KEY = Integer.MAX_VALUE;//正无穷

    public SkipListNode(int k, T v) {
        this.key = k;
        this.value = v;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof SkipListNode<?>)) {
            return false;
        }

        SkipListNode<T> ent;
        try {
            ent = (SkipListNode<T>) o;
        } catch (Exception e) {
            return false;
        }
        return (ent.getKey() == key) && (ent.getValue() == value);
    }

    @Override
    public String toString() {
        return "key-value:"+key+"-"+value;
    }
}