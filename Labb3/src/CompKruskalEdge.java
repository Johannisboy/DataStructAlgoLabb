/**
 * A representation of an edge that can be compared.
 */
public class CompKruskalEdge extends BusEdge implements Comparable {

    /**
     * Construct an edge. Nothing can change once created.
     *
     * @param from The start node.
     * @param to The end node.
     * @param weight The weight of the edge.
     * @param line The linename of the edge.
     */
    CompKruskalEdge(int from, int to, double weight, String line) {
        super(from, to, weight, line);
    }

    /**
     * Compare function to make it work with the priority queue. Compares the weights.
     *
     * @param o Object to be compared to.
     * @return -1 if this object is less than the specified, 0 if equal and 1 if it's bigger.
     */
    @Override
    public int compareTo(Object o) {
        CompKruskalEdge cmp = (CompKruskalEdge) o;
        if (this.getWeight() < cmp.getWeight()) {
            return -1;
        } else if (this.getWeight() == cmp.getWeight()) {
            return 0;
        } else {
            return 1;
        }
    }
}
