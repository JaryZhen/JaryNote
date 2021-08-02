package graph;

/**
 * @Author: Jary
 * @Date: 2021/8/2 2:50 下午
 */
public class GraphEage {
    int weight;
    GraphNode from;
    GraphNode to;

    public GraphEage(int weight, GraphNode from, GraphNode to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
