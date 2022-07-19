package graph;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

/**
 * @Author: Jary
 * @Date: 2021/8/2 2:49 下午
 */
public class Graph {
    HashMap<Integer, GraphNode> nodes;
    HashSet<GraphEage> eages;

    public Graph() {
        nodes = new HashMap<>();
        eages = new HashSet<>();
    }

    public void printBFS(int nodeVC) {
        GraphNode node = nodes.get(nodeVC);
        if (node == null) {
            return;
        }
        Queue<GraphNode> queue = new ArrayDeque();
        HashSet re = new HashSet();
        queue.add(node);
        re.add(node);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                GraphNode node1 = queue.poll();
                System.out.print(node1.value + " ");
                for (GraphNode n : node1.nexts) {
                    if (!re.contains(n)) {
                        queue.add(n);
                        re.add(n);
                    }
                }
            }
            System.out.println();
        }
    }
}
