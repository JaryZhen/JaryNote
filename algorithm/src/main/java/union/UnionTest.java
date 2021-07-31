package union;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Author: Jary 并查集
 * @Date: 2021/7/31 4:51 下午
 */
public class UnionTest {

    private static class UnionNode<T> {
        T value;

        UnionNode(T value) {
            this.value = value;
        }
    }

    private static class UnionSet<V> {
        private HashMap<V, UnionNode<V>> nodes;
        private HashMap<UnionNode<V>, UnionNode<V>> parents;
        private HashMap<UnionNode<V>, Integer> sizeMap;

        UnionSet(List<V> values) {
            for (V v : values) {
                UnionNode<V> node = new UnionNode<>(v);
                nodes.put(v, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public boolean isSameSet(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b))
                return false;
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        private UnionNode<V> findFather(UnionNode<V> vNode) {
            Stack<UnionNode<V>> path = new Stack<>();
            while (vNode != parents.get(vNode)) {
                path.push(vNode);
                vNode = parents.get(vNode);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), vNode);
            }
            return vNode;
        }

        public void union(V a, V b) {
            if (!nodes.containsKey(a) || !nodes.containsKey(b))
                return;
            UnionNode<V> ahead = findFather(nodes.get(a));
            UnionNode<V> bhead = findFather(nodes.get(b));
            if (ahead != bhead) {
                int aSetSize = sizeMap.get(ahead);
                int bSetSize = sizeMap.get(bhead);
                if (aSetSize >= bSetSize) {
                    parents.put(bhead, ahead);
                    sizeMap.put(ahead, aSetSize + bSetSize);
                    sizeMap.remove(ahead);
                } else {
                    parents.put(ahead, bhead);
                    sizeMap.put(bhead, aSetSize + bSetSize);
                    sizeMap.remove(bhead);
                }
            }
        }
    }


    public static void main(String[] args) {
        HashMap<Integer, String> a = new HashMap();
        a.put(1, "a");
        System.out.println(a.get(1));
        System.out.println(a.get(1));
    }

}
