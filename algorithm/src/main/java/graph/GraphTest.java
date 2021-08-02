package graph;

/**
 * @Author: Jary
 * @Date: 2021/8/2 10:29 上午
 */
public class GraphTest {

    public static void main(String[] args) {

        int[][] we = new int[][]{
                {1, 2, 3},
                {2, 3, 4},
                {3, 2, 4},
                {3, 4, 5},
                {3, 4, 3},
                {3, 4, 2},
                {3, 4, 6}};
        Graph graph = GraphHelper.makeG(new Graph(), we);

        graph.printBFS(2);
    }
}
