package graph;

import java.util.ArrayList;

/**
 * @Author: Jary
 * @Date: 2021/8/2 2:51 下午
 */
public class GraphNode {
    public int value;
    public int in;
    public int out;
    public ArrayList<GraphNode> nexts;
    public ArrayList<GraphEage> eages;

    public GraphNode(int value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        nexts = new ArrayList<>();
        eages = new ArrayList<>();
    }
}
