import java.util.*;

/**
 * A class representing a directed graph.
 * @param <E> Usually a BusEdge.
 */
public class DirectedGraph<E extends Edge> {

    private int noOfNodes;
    private LinkedList<E> edges;
    private List<E>[] neighbours;

    /**
     * Constructor.
     * @param noOfNodes Total number of nodes in the graph.
     */
    public DirectedGraph(int noOfNodes) {
        this.edges = new LinkedList<E>();
        this.noOfNodes = noOfNodes;
        this.neighbours = new List[noOfNodes];
        for(int i =0; i< noOfNodes;i++)
        {
            this.neighbours[i]= new ArrayList<>();
        }
    }

    /**
     * Adds an edge to the graph.
     * @param e The edge to be added.
     */
    public void addEdge(E e) {
        this.edges.add(e);
        this.neighbours[e.from].add(e);
    }

    /**
     * Finds the shortest path between two nodes.
     * @param from The starting node.
     * @param to The end node.
     * @return An iterator that iterates over the shortest path.
     */
    public Iterator<E> shortestPath(int from, int to) {
        PriorityQueue<CompDijkstraPath> PQ = new PriorityQueue<>();
        ArrayList<Integer> visited = new ArrayList<>();
        CompDijkstraPath startNode = new CompDijkstraPath(from, 0,new LinkedList<BusEdge>());
        PQ.add(startNode);

        while (!PQ.isEmpty())
        {
            CompDijkstraPath node = PQ.poll();
            if (!visited.contains(node.nodeObject))
            {
                if(node.nodeObject==to){
                    return node.path.iterator();
                }
                else {
                    visited.add(node.nodeObject);
                    List<E> neighboursList = neighbours[node.nodeObject];
                    for (E e:neighboursList) {
                        if(!visited.contains(e.to))
                        {
                            LinkedList<BusEdge> tempPath = new LinkedList<>();
                            for (Object q:node.path) {
                                tempPath.add((BusEdge)q);
                            }
                            CompDijkstraPath newNode = new CompDijkstraPath(e.to,node.cost+e.getWeight(),tempPath);
                            newNode.path.add(e);
                            PQ.add(newNode);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Gets the minimum spanning tree of the graph using kruskals algorithm.
     * @return An iterator that iterates over the minimum spanning tree.
     */
    public Iterator<E> minimumSpanningTree() {
        // Create an array of lists containing edges.
        List<E>[] cc = new List[noOfNodes];
        for (int i = 0; i < noOfNodes; i++) {
            cc[i] = new LinkedList<>();
        }

        // Create and populate a priority queue with all the edges.
        PriorityQueue<CompKruskalEdge> pQueue = new PriorityQueue<>();
        for (E edge : edges) {
            pQueue.offer(new CompKruskalEdge(edge.getSource(), edge.getDest(), edge.getWeight(), "lineName"));
        }

        while (!pQueue.isEmpty() && cc.length > 1) {
            // Get the node ID's connected to the edge with minimum weight.
            E currentEdge = (E) pQueue.poll();
            int fromID = currentEdge.getSource();
            int toID = currentEdge.getDest();

            // If list of edges doesn't point to same list, add smaller to larger.
            if (cc[fromID] != cc[toID]) {
                if (cc[fromID].size() > cc[toID].size()) {
                    // Copy over all edges then change reference for all the lists to destination list.
                    cc[fromID].addAll(cc[toID]);
                    for (E edge : cc[toID]) {
                        cc[edge.getSource()] = cc[fromID];
                        cc[edge.getDest()] = cc[fromID];
                    }

                    // Point the smaller list to the larger and add the edge.
                    cc[toID] = cc[fromID];
                    cc[fromID].add(currentEdge);
                } else {
                    // Copy over all edges then change reference for all the lists to source list.
                    cc[toID].addAll(cc[fromID]);
                    for (E edge : cc[fromID]) {
                        cc[edge.getSource()] = cc[toID];
                        cc[edge.getDest()] = cc[toID];
                    }

                    // Point the smaller list to the larger and add the edge.
                    cc[fromID] = cc[toID];
                    cc[toID].add(currentEdge);
                }
            }
        }

        // Return an iterator of the only list remaining.
        return cc[0].iterator();
    }

}
  
