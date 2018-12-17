import java.util.*;

/**
 * Class implementing kruskals algoritm.
 * @param <E> A BusEdge in this laboration.
 */
public class CompKruskalEdge<E extends Edge> {

    private List<KruskalNode> cc;
    private PriorityQueue<E> pQueue;

    /**
     * Constructor.
     * @param edges The list of all edges in the graph.
     * @param noOfNodes The total amount of nodes in the graph.
     */
    public CompKruskalEdge(List<E> edges, int noOfNodes) {
        // Create and add all nodes with empty lists of edges (sorted alphabetically by ints).
        this.cc = new ArrayList<>();
        for (int i = 0; i < noOfNodes; i++) {
            cc.add(new KruskalNode());
        }

        // Add all edges to the priority queue.
        this.pQueue = new PriorityQueue<>();
        this.pQueue.addAll(edges);
    }

    /**
     * Inner class representing the nodes with added list of edges.
     */
    public class KruskalNode {
        public LinkedList<E> edgeList;

        public KruskalNode() {
            this.edgeList = new LinkedList<>();
        }
    }

    /**
     * Gets the minimum spanning tree with kruskals algorithm.
     * @return An iterator from the minimum spanning tree.
     */
    public Iterator<E> getMST() {
        while (!pQueue.isEmpty() && cc.size() > 1) {
            // Get the nodes connected to the edge with min weight.
            E currentEdge = pQueue.poll();
            KruskalNode fromNode = cc.get(currentEdge.getSource());
            KruskalNode toNode = cc.get(currentEdge.getDest());

            // If nodes list of edges doesn't point to same list, add smaller to larger.
            if (!fromNode.edgeList.equals(toNode.edgeList)) {
                if (fromNode.edgeList.size() > toNode.edgeList.size()) {
                    fromNode.edgeList.addAll(toNode.edgeList);
                    toNode.edgeList = fromNode.edgeList;
                    //TODO: @jol Maybe need to redirect other references in cc to correct list.
                    fromNode.edgeList.add(currentEdge);
                } else {
                    toNode.edgeList.addAll(fromNode.edgeList);
                    fromNode.edgeList = toNode.edgeList;
                    //TODO: @jol Maybe need to redirect other references in cc to correct list.
                    toNode.edgeList.add(currentEdge);
                }
            }

        }

        // Return an iterator of the only list remaining.
        return cc.get(0).edgeList.iterator();
    }

}
