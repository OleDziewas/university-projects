public class Test {
    public static void main(String[] args) {
        WeightedGraph wg = new WeightedGraph(true);
        WeightedGraph.Vertex A = wg.new Vertex("A");
        WeightedGraph.Vertex B = wg.new Vertex("B");
        WeightedGraph.Vertex C = wg.new Vertex("C");
        WeightedGraph.Vertex D = wg.new Vertex("D");
        WeightedGraph.Vertex E = wg.new Vertex("E");
        wg.addVertex(A);
        wg.addVertex(B);
        wg.addVertex(C);
        wg.addVertex(D);
        wg.addVertex(E);
        wg.addEdge(A, B, 1);
        wg.addEdge(A, C, -2);
        wg.addEdge(A, D, 4);
        wg.addEdge(B, C, 9);
        wg.addEdge(B, E, 5);
        wg.addEdge(C,E, -1);
        wg.addEdge(D,C, 20);
        wg.addEdge(E,A, 3);
        wg.addEdge(E,D, 13);

        wg.FloydWarshall();
    }

}
