package union;

import graph.GraphTest;

import java.util.*;

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
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
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
                    sizeMap.remove(bhead);
                } else {
                    parents.put(ahead, bhead);
                    sizeMap.put(bhead, aSetSize + bSetSize);
                    sizeMap.remove(ahead);
                }
            }
        }
    }


    public static void main(String[] args) {
        User a = new User(1, 10, 100);
        User b = new User(2, 10, 101);
        User c = new User(3, 11, 100);
        User v = new User(3, 11, 100);

        ArrayList<User> lists = new ArrayList<>();
        lists.add(a);
        lists.add(b);
        lists.add(c);
        lists.add(v);

        HashMap<Integer, User> mapId = new HashMap();
        HashMap<Integer, User> mapGithub = new HashMap();
        HashMap<Integer, User> mapBilibi = new HashMap();

        for (User user : lists) {
            mapId.put(user.id, user);
            mapGithub.put(user.gitlhub, user);
            mapBilibi.put(user.bili, user);
        }

        UnionSet<User> unionSet = new UnionSet<>(lists);

        for (User user : lists) {
            if (mapId.containsKey(user.id)) {
                unionSet.union(user, mapId.get(user.id));
            }else {
                mapId.put(user.id, user);
            }
            if (mapGithub.containsKey(user.gitlhub)) {
                unionSet.union(user, mapGithub.get(user.gitlhub));
            }else {
                mapGithub.put(user.gitlhub, user);
            }
            if (mapBilibi.containsKey(user.bili)) {
                unionSet.union(user, mapBilibi.get(user.bili));
            }else {
                mapBilibi.put(user.bili, user);
            }
        }

        System.out.println(unionSet.sizeMap.size());
    }

    private static class User {
        Integer id;
        Integer gitlhub;
        Integer bili;

        public User(Integer id, Integer gitlhub, Integer bili) {
            this.id = id;
            this.gitlhub = gitlhub;
            this.bili = bili;
        }
    }
}
