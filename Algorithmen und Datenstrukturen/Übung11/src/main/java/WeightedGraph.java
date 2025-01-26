import java.sql.SQLOutput;
import java.util.*;

/**
 * Implements a Weighted Graph G with vertices V and edges E accompanied by weights.
 * Each {@link Vertex} V is labeled with a {@link String} and all edges are
 * stored indirectly using an adjacency list. To store the vertices, a
 * {@link HashSet} is used, which ensures that each vertex is added only once. 
 * A graph may be instantiated as directed or undirected.
 * 
 * @author Michail Sioutis
 * @author Tobias Schwartz
 */
public class WeightedGraph {

	final boolean directed ; // initialize in Graph constructor
	HashSet<Vertex> vertices;

	/**
	 * Represents an edge in the {@link WeightedGraph}. Each edge maintains a weight
	 * of type {@link double} and stores its destination vertex.
	 */
	class Edge {
		Vertex destination;
		double weight;
		Edge(Vertex destination, double weight) {
			this.destination = destination;
			this.weight = weight;
		}
	}

	/**
	 * Represents a vertex in the {@link WeightedGraph}. Each vertex is labeled using a
	 * {@link String}. All outgoing edges are stored in an adjacency list.
	 */
	class Vertex {
		String label;
		ArrayList<Edge> adjVertices;
		Vertex(String label) {
			this.label = label;
			this.adjVertices = new ArrayList<>();
		}
	}

	/**
	 * Creates a new graph that may be directed or undirected.
	 * 
	 * @param directed indicates whether the graph is directed or undirected
	 */
	WeightedGraph (boolean directed) {
		this.directed = directed;
		this.vertices = new HashSet<>();
	}

	/**
	 * Adds a new edge between two vertices v1 and v2, if it does not already exist.
	 * Note that in an undirected graph the edge is stored in both directions.
	 * 
	 * @param v1 the origin of the new edge
	 * @param v2 the destination of the new edge
	 * @param weight the weight of the edge
	 */
	public void addEdge(Vertex v1, Vertex v2, double weight) {
		if (v1 != null && v2 != null){
			if (this.directed == true) {
				Edge edge = new Edge(v2, weight);
				v1.adjVertices.add(edge);
			}else{
				Edge edge1 = new Edge(v2, weight);
				Edge edge2 = new Edge(v1, weight);
				v1.adjVertices.add(edge1);
				v2.adjVertices.add(edge2);
			}
		}

	}

	/**
	 * Adds a new {@link Vertex} v to the graph, if the vertex does not already
	 * exist in the graph.
	 * 
	 * @param v the new vertex to add
	 */
	public void addVertex(Vertex v) {
		if(v!= null) {
			this.vertices.add(v);
		}
	}

	/**
	 * Removes a given {@link Vertex} v from the graph. If the graph does not
	 * contain the vertex, the graph remains unchanged.
	 * 
	 * @param v
	 */
	public void removeVertex(Vertex v) {
		if(v!= null) {
			ArrayList<Edge> edgesToBeRemoved = new ArrayList<>();
			if (this.vertices.remove(v)) {
				for (Vertex vertex : vertices) {
					for (Edge edge : vertex.adjVertices) {
						if (edge.destination.label.equals(v.label)) {
							edgesToBeRemoved.add(edge);
						}
					}
					for (Edge edge : edgesToBeRemoved) {
						vertex.adjVertices.remove(edge);
					}
				}
			}
		}

	}

	/**
	 * Removes an existing edge between two vertices v1 and v2.
	 * 
	 * @param v1 the origin of the deleted edge
	 * @param v2 the destination of the deleted edge
	 */
	public void removeEdge(Vertex v1, Vertex v2) {
		if (v1!= null&&v2!= null) {
			ArrayList<Edge> edgesToBeRemoved = new ArrayList<>();
			for (Edge edge : v1.adjVertices) {
				if (edge.destination == v2) {
					edgesToBeRemoved.add(edge);
				}
			}
			for (Edge edge : edgesToBeRemoved) {
				v1.adjVertices.remove(edge);
			}
		}
	}

	/**
	 * Checks if two vertices v1 and v2 are adjacent, i.e. have an edge connecting
	 * them. 
	 * 
	 * @param v1 the first vertex
	 * @param v2 the second vertex
	 * @return true if v1 and v2 are adjacent, false otherwise
	 */
	public boolean areAdjacent(Vertex v1, Vertex v2) {
		if (v1 != null && v2 != null) {
			for (Edge edge : v1.adjVertices) {
				if (edge.destination == v2) {
					return true;
				}
			}
			for (Edge edge : v2.adjVertices) {
				if (edge.destination == v1) {
					return true;
				}
			}

		}
		return false;
	}
	/**
	 * Runs the FloydWarshall algorithm on the graph.
	 * Note: It is recommended to convert the graph into a distance matrix.
	 * 
	 * @return a String as specified in the assignment.
	 */
	public String FloydWarshall() {
		double[][] graph = createGraphMatrix();

		double dist[][] = calculateDistanceMatrix(graph);
		String result = createMatrixString(dist);
		System.out.println(result);
		return result;
	}

	private double[][] calculateDistanceMatrix(double graph[][]){
		int V = this.vertices.size();
		double dist[][] = new double[V][V];
		int i,j,k;

		for (i= 0; i<V; i++){
			for(j= 0; j<V; j++){
				dist[i][j] = graph[i][j];
			}
		}
		for (k = 0; k < V; k++) {
			for (i = 0; i < V; i++) {
				for (j = 0; j < V; j++) {
					if (dist[i][k] + dist[k][j] < dist[i][j])
						dist[i][j] = dist[i][k] + dist[k][j];
				}
			}
		}

		return dist;
	}
	private double[][] createGraphMatrix(){
		int V = this.vertices.size();
		double[][] graph = new double[V][V];
		final double INF = Double.POSITIVE_INFINITY;
		int i = 0;
		for (Vertex v1: this.vertices){
			int j = 0;
			for (Vertex v2: this.vertices){
				if (i == j){
					graph[i][j] = 0;
				}else {
					if (v1.adjVertices.isEmpty()){
						graph[i][j]=INF;
					}
					for (Edge edge : v1.adjVertices) {
						if (edge.destination.label.equals(v2.label)){
							graph[i][j] = edge.weight;
							break;
						}
						graph[i][j] = INF;
					}
				}
				j++;
			}
			i++;
		}
		return graph;
	}

	private String createMatrixString(double[][] matrix){
		int i = 0;
		int counter = 0;
		int V = this.vertices.size();
		String[] resultArray=new String[V*V-V];
		for (Vertex v1: this.vertices){
			int j = 0;
			for (Vertex v2: this.vertices){
				if(i!= j) {
					resultArray[counter] = v1.label + " " + v2.label + " " + matrix[i][j] + "\n";
					counter++;
				}
				j++;
			}
			i++;
		}
		Arrays.sort(resultArray);
		String resultString = "";
		for (int e = 0; e<resultArray.length; e++){
			resultString+= resultArray[e];
		}
		resultString =resultString.trim();
		return resultString;
	}
}
