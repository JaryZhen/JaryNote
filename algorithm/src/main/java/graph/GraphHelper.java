package graph;

/**
 * @Author: Jary
 * @Date: 2021/8/2 2:56 下午
 */
public class GraphHelper {
    /**
     *  {weight, from, to}
     * @param graph
     * @param matrix
     * @return
     */
    static Graph makeG(Graph graph, int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            graph.nodes.putIfAbsent(from, new GraphNode(from));
            graph.nodes.putIfAbsent(to, new GraphNode(to));
            GraphNode froNode = graph.nodes.get(from);
            GraphNode toNode = graph.nodes.get(to);

            GraphEage eage = new GraphEage(weight, froNode, toNode);

            froNode.nexts.add(toNode);
            froNode.out++;
            froNode.in++;
            froNode.eages.add(eage);
            graph.eages.add(eage);
        }
        return graph;
    }
}
