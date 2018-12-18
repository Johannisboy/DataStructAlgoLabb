import java.util.*;

/**
 * Class implementing kruskals algoritm.
 * @param <E> A BusEdge in this laboration.
 */
public class CompKruskalEdge<E extends Edge> {

    private List<KruskalNode> kruskalNodes;
    private PriorityQueue<KruskalEdge> pQueue;

    /**
     * Constructor.
     * @param edges The list of all edges in the graph.
     * @param noOfNodes The total amount of nodes in the graph.
     */
    public CompKruskalEdge(List<E> edges, int noOfNodes) {
        // Create and add all nodes with empty lists of edges (sorted alphabetically by ints).
        this.kruskalNodes = new ArrayList<>();
        for (int i = 0; i < noOfNodes; i++) {
            kruskalNodes.add(new KruskalNode());
        }
        // Add all edges to the priority queue.
        this.pQueue = new PriorityQueue<>();

        for (E edge : edges) {
            // TODO: @jol Can' get access to the line name because it only knows about Edge, not BusEdge which implements it.
            this.pQueue.offer(new KruskalEdge(edge.getSource(), edge.getDest(), edge.getWeight(), "line"));
        }
        int i = 0;
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
     * Inner class implementing Comparable to get it to work with the priority queue.
     */
    public class KruskalEdge extends BusEdge implements Comparable {

        /**
         * Construct an edge. Nothing can change once created.
         *
         * @param from Representation of source.
         * @param to Representation of destination.
         * @param weight Weight of the edge.
         * @param line String representation of the busline.
         */
        KruskalEdge(int from, int to, double weight, String line) {
            super(from, to, weight, line);
        }

        /**
         * Compare function to make it work with the priority queue. Compares the weights.
         * @param o Object to be compared to.
         * @return -1 if this object is less than the specified, 0 if equal and 1 if it's bigger.
         */
        @Override
        public int compareTo(Object o) {
            KruskalEdge cmp = (KruskalEdge) o;
            if (this.getWeight() < cmp.getWeight()) {
                return -1;
            } else if (this.getWeight() == cmp.getWeight()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * Gets the minimum spanning tree with kruskals algorithm.
     * @return An iterator from the minimum spanning tree.
     */
    public Iterator<E> getMST() {
        while (!pQueue.isEmpty() && kruskalNodes.size() > 1) {
            // Get the nodes connected to the edge with min weight.
            E currentEdge = (E) pQueue.poll();
            KruskalNode fromNode = kruskalNodes.get(currentEdge.getSource());
            KruskalNode toNode = kruskalNodes.get(currentEdge.getDest());

            // If nodes list of edges doesn't point to same list, add smaller to larger.
            if (fromNode.edgeList != toNode.edgeList) {
                if (fromNode.edgeList.size() > toNode.edgeList.size()) {
                    fromNode.edgeList.addAll(toNode.edgeList);
                    toNode.edgeList = fromNode.edgeList;
                    //TODO: @jol Maybe need to redirect other references in kruskalNodes to correct list.
                    fromNode.edgeList.add(currentEdge);
                } else {
                    toNode.edgeList.addAll(fromNode.edgeList);
                    fromNode.edgeList = toNode.edgeList;
                    //TODO: @jol Maybe need to redirect other references in kruskalNodes to correct list.
                    toNode.edgeList.add(currentEdge);
                }
            }

        }

        // Return an iterator of the only list remaining.
        return kruskalNodes.get(0).edgeList.iterator();
    }

}
