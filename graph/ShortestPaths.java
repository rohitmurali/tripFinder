package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;

/** The shortest paths through an edge-weighted labeled graph of type GRAPHTYPE.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to get parameters of the
 *  search and to return results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Rohit Muralidharan
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        preds = new HashMap<Integer, Integer>();
        for (int vertex : _G.vertices()) {
            setPredecessor(vertex, 0);
        }
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Astar a = new Astar(_G);
        for (int vertex : _G.vertices()) {
            setWeight(vertex, Double.POSITIVE_INFINITY);
            result.add(vertex);
        }
        setWeight(_source, 0.0);
        a.traverse(result);
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        List<Integer> result = new ArrayList<Integer>();
        int check = v;
        while (check != 0) {
            result.add(check);
            check = getPredecessor(check);
        }
        Collections.reverse(result);
        return result;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** The predecessors by index. */
    protected HashMap<Integer, Integer> preds;
    /** A comparator for the PriorityQueue to use. */
    public class DistComparator implements Comparator<Integer> {

        @Override
        public int compare(Integer v, Integer u) {
            double distV = estimatedDistance(v) + getWeight(v);
            double distU = estimatedDistance(u) + getWeight(u);
            if (distV < distU) {
                return -1;
            } else if (distV > distU) {
                return 1;
            }
            return 0;
        }
    }

    /** Astar Traversal class. */
    private class Astar extends Traversal {
        /** Constructor.
        * @param G takes in a Graph g.
        */
        Astar(Graph G) {
            super(G, new PriorityQueue<Integer>(G.vertexSize(),
                new DistComparator()));
        }

        @Override
        protected boolean processSuccessor(int u, int v) {
            double dist = getWeight(u) + getWeight(u, v);
            if (dist >= getWeight(v)) {
                return false;
            }
            setWeight(v, dist);
            setPredecessor(v, u);
            _fringe.remove(v);
            return true;
        }

        @Override
        protected boolean visit(int v) {
            return v != _dest;
        }
    }
}
