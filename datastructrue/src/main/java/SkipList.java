/**
 * Created by Jary on 2017/9/19 0019.
 */

import java.util.Random;

/**
 * SkipList
 * 不固定层级的跳跃表
 * Created by heqianqian on 2017/6/1.
 */
public class SkipList<T extends Comparable<? super T>> {

    private SkipListNode<T> head, tail;
    private int nodes;//节点总数
    private int listLevel;//层数
    private Random random;//用于产生随机数
    private static final double PROBABILITY = 0.5;//向上提升一个的概率

    public SkipList() {
        random = new Random();
        clear();
    }

    /**
     * 清空跳跃表
     */
    public void clear() {
        head = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
        tail = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
        horizontalLink(head, tail);
        listLevel = 0;
        nodes = 0;
    }

    /**
     * 水平双向连接
     */
    private void horizontalLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.right = node2;
        node2.left = node1;
    }

    /**
     * 垂直双向连接
     */
    private void vertiacallLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node1.down = node2;
        node2.up = node1;
    }

    /**
     * 在最下面一层，找到要插入的位置前面的那个key
     */
    private SkipListNode<T> findNode(int key) {
        SkipListNode<T> p = head;
        while (true) {
            while (p.right.getKey() != SkipListNode.TAIL_KEY && p.right.getKey() <= key) {
                p = p.right;
            }
            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }
        return p;
    }

    /**
     * 查找是否存储key，存在则返回该节点，否则返回null
     */
    public SkipListNode<T> search(int key) {
        SkipListNode<T> p = findNode(key);
        return (key == p.getKey()) ? p : null;
    }

    /**
     * 向跳跃表中添加key-value
     */
    public void put(int k, T v) {
        SkipListNode<T> p = findNode(k);
        //如果key值相同，替换原来的vaule即可结束
        if (k == p.getKey()) {
            p.setValue(v);
            return;
        }
        SkipListNode<T> q = new SkipListNode<T>(k, v);
        backLink(p, q);
        int currentLevel = 0;//当前所层次是0
        //产生随机数
        double randomnum= random.nextDouble();
        while ( randomnum < PROBABILITY) {
            //新建一个层
            if (currentLevel >= listLevel) {
                listLevel++;
                SkipListNode<T> p1 = new SkipListNode<T>(SkipListNode.HEAD_KEY, null);
                SkipListNode<T> p2 = new SkipListNode<T>(SkipListNode.TAIL_KEY, null);
                horizontalLink(p1, p2);
                vertiacallLink(p1, head);
                vertiacallLink(p2, tail);
                head = p1;
                tail = p2;
            }
            //把p移动到上一层
            while (p.up == null) {
                p = p.left;
            }
            p = p.up;

            SkipListNode<T> e = new SkipListNode<T>(k, null);
            backLink(p, e);
            vertiacallLink(e, q);
            q = e;
            currentLevel++;
        }
        nodes++;
    }

    /**
     * 在node1后插入node2
     */
    private void backLink(SkipListNode<T> node1, SkipListNode<T> node2) {
        node2.left = node1;
        node2.right = node1.right;
        node1.right.left = node2;
        node1.right = node2;
    }

    public boolean isEmpty() {
        return nodes == 0;
    }

    public int size() {
        return nodes;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "跳跃表为空";
        }
        StringBuilder builder = new StringBuilder();
        SkipListNode<T> p = head;
        while (p.down != null) {
            p = p.down;
        }
        while (p.left != null) {
            p = p.left;
        }
        if (p.right != null) {
            p = p.right;
        }
        while (p.right != null) {
            builder.append(p);
            builder.append("\n");
            p = p.right;
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        SkipList<String> list = new SkipList<>();
        System.out.println(list.size());
        list.put(2, "he");
        list.put(1, "qianqian");
        list.put(3, "何");
        list.put(4, "芊");
        list.put(20, "he0");
        list.put(10, "qianqian0");
        list.put(30, "何0");
        list.put(40, "芊0");

        list.put(20, "he0");
        list.put(10, "qianqian0");
        list.put(30, "何0");
        list.put(40, "芊0");

        list.put(21, "he0");
        list.put(11, "qianqian0");
        list.put(31, "何0");
        list.put(41, "芊0");
        list.put(21, "he0");
        list.put(11, "qianqian0");
        list.put(31, "何0");
        list.put(41, "芊0");

        System.out.println(list.listLevel);
        System.out.println(list.size());
    }
}
