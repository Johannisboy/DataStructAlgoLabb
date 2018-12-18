import java.util.*;

public class DirectedGraph<E extends Edge> {

	private LinkedList<E> edges;
	private int noOfNodes;

	public DirectedGraph(int noOfNodes) {
		this.edges = new LinkedList<E>();
		this.noOfNodes = noOfNodes;
	}

	public void addEdge(E e) {
		edges.add(e);
	}

	public Iterator<E> shortestPath(int from, int to) {
		return null;
	}
		
	public Iterator<E> minimumSpanningTree() {
		CompKruskalEdge kruskal = new CompKruskalEdge(edges, noOfNodes);
		return kruskal.getMST();
	}

}
  
