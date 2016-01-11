package graph;
import java.util.HashMap;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Rohit Muralidharan
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        weights = new HashMap<Integer, Double>();
        for (int vertex : _G.vertices()) {
            setWeight(vertex, Double.POSITIVE_INFINITY);
        }

    }

    @Override
    public double getWeight(int v) {
        if (_G.contains(v)) {
            return weights.get(v);
        }
        return Double.POSITIVE_INFINITY;
    }

    @Override
    protected void setWeight(int v, double w) {
        weights.put(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        if (_G.contains(v) && preds.containsKey(v)) {
            return preds.get(v);
        }
        return 0;
    }

    @Override
    protected void setPredecessor(int v, int u) {
        preds.put(v, u);
    }
    /** A hashmap of the weights. */
    private HashMap<Integer, Double> weights;
}
