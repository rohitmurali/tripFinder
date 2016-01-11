package graph;
import java.util.ArrayList;

/* See restrictions in Graph.java. */

/** Represents an undirected graph.  Out edges and in edges are not
 *  distinguished.  Likewise for successors and predecessors.
 *
 *  @author Rohit Muralidharan
 */
public class UndirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return false;
    }

    @Override
    public int inDegree(int v) {
        if (!mine(v)) {
            return 0;
        }
        return outDegree(v);
    }

    @Override
    public int predecessor(int v, int k) {
        int result = 0;
        if (mine(v)) {
            if (!_graph.get(v).get(0).isEmpty()) {
                result = _graph.get(v).get(1).get(k);
            }
        }
        return result;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (contains(v)) {
            ArrayList<Integer> p = _graph.get(v).get(0);
            if (p.size() > 0) {
                return Iteration.iteration(p.iterator());
            }
        }
        ArrayList<Integer> emptyiter = new ArrayList<Integer>();
        return Iteration.iteration(emptyiter.iterator());
    }
}
