package graph;
import java.util.ArrayList;
/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Rohit Muralidharan
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        int result = 0;
        if (mine(v)) {
            result = _graph.get(v).get(0).size();
        }
        return result;
    }

    @Override
    public int predecessor(int v, int k) {
        int result = 0;
        if (mine(v)) {
            if (!_graph.get(v).get(0).isEmpty()) {
                result = _graph.get(v).get(0).get(k);
            }
        }
        return result;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (contains(v) && _graph.get(v).get(0).size() > 0) {
            return Iteration.iteration(_graph.get(v).get(0).iterator());
        }
        ArrayList<Integer> emptyiter = new ArrayList<Integer>();
        return Iteration.iteration(emptyiter.iterator());
    }
}
