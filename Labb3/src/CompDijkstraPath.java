import java.util.*;

public class CompDijkstraPath<E extends BusEdge> implements Comparable {

    int nodeObject = 0;
    double cost = 0;
    LinkedList<E> path;

    public CompDijkstraPath(int n, double cost, LinkedList<E> path) {
        this.nodeObject = n;
        this.cost = cost;
        this.path = path;
    }

    @Override
    public int compareTo(Object o) {
        CompDijkstraPath dNtemp = (CompDijkstraPath) o;

        if (dNtemp.cost < this.cost)
            return 1;
        else if (dNtemp.cost == this.cost)
            return 0;
        else
            return -1;
    }
}