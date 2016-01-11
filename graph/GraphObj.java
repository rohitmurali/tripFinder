package graph;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Rohit Muralidharan
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _graph = new HashMap<Integer, ArrayList<ArrayList<Integer>>>();
        edges = new ArrayList<int[]>();
        max = 0;
    }

    @Override
    public int vertexSize() {
        return _graph.size();
    }

    @Override
    public int maxVertex() {
        max = 0;
        for (Integer key: _graph.keySet()) {
            if (key > max) {
                max = key;
            }
        }
        return max;
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (!contains(v)) {
            return 0;
        }
        return _graph.get(v).get(1).size();
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return _graph.containsKey(u);
    }

    @Override
    public boolean contains(int u, int v) {
        for (int[] arr : edges) {
            if (arr[0] == u && arr[1] == v) {
                return true;
            } else if (!isDirected()) {
                if (arr[1] == u && arr[0] == v) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public int add() {
        int tail = vertexSize() + 1;
        for (int i = tail - 1; i > 0; i--) {
            if (!contains(i)) {
                tail = i;
            }
        }
        _graph.put(tail, new ArrayList<ArrayList<Integer>>());
        _graph.get(tail).add(new ArrayList<Integer>());
        _graph.get(tail).add(new ArrayList<Integer>());
        return tail;
    }

    @Override
    public int add(int u, int v) {
        if (!contains(u, v)) {
            _graph.get(u).get(1).add(v);
            _graph.get(v).get(0).add(u);
            edges.add(new int[]{u, v});
            if (!isDirected() && u != v) {
                _graph.get(u).get(0).add(v);
                _graph.get(v).get(1).add(u);
            }
        }
        return u;
    }
    /** Helper method that removes all instances of an edge from the arrayList.
    * Does not return anything.
    * @param u the u of edge to remove
    * @param v the v of edge to remove
    */
    private void removeEdge(int u, int v) {
        int[] check = {u, v};
        for (int i = 0; i < edges.size(); i++) {
            if (Arrays.equals(check, edges.get(i))) {
                edges.remove(i);
            }
        }
    }

    @Override
    public void remove(int v) {
        if (contains(v)) {
            for (Integer p : _graph.get(v).get(0)) {
                if (_graph.get(p).get(1).remove((Object) v)) {
                    removeEdge(p, v);
                }
            }
            for (Integer s : _graph.get(v).get(1)) {
                if (_graph.get(s).get(0).remove((Object) v)) {
                    removeEdge(v, s);
                }
            }
            _graph.remove(v);
        }
    }

    @Override
    public void remove(int u, int v) {
        if (contains(u, v)) {
            _graph.get(u).get(1).remove((Object) v);
            _graph.get(v).get(0).remove((Object) u);
            removeEdge(u, v);
        }
        if (!isDirected()) {
            _graph.get(u).get(0).remove((Object) v);
            _graph.get(v).get(1).remove((Object) u);
            if (contains(v, u)) {
                removeEdge(v, u);
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        if (vertexSize() > 0) {
            ArrayList<Integer> result = new ArrayList<Integer>(vertexSize());
            for (Integer vertex : _graph.keySet()) {
                result.add(vertex);
            }
            return Iteration.iteration(result.iterator());
        }
        ArrayList<Integer> emptyiter = new ArrayList<Integer>();
        return Iteration.iteration(emptyiter.iterator());
    }

    @Override
    public int successor(int v, int k) {
        int result = 0;
        if (contains(v)) {
            if (_graph.get(v).get(1).size() > k) {
                result = _graph.get(v).get(1).get(k);
            }
        }
        return result;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        if (outDegree(v) > 0) {
            return Iteration.iteration(_graph.get(v).get(1).iterator());
        }
        ArrayList<Integer> emptyiter = new ArrayList<Integer>();
        return Iteration.iteration(emptyiter.iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        if (edgeSize() > 0) {
            return Iteration.iteration(edges.iterator());
        }
        ArrayList<int[]> emptyiter = new ArrayList<int[]>();
        return Iteration.iteration(emptyiter.iterator());

    }

    @Override
    protected boolean mine(int v) {
        return contains(v);
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!mine(v)) {
            System.out.println("exception");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        if (!contains(u, v)) {
            return 0;
        }
        if (isDirected()) {
            return idHelp(u, v);
        }
        return idHelp(u + v, u * v);
    }
    /** Uses cantar function.
    * @param a a
    * @param b b
    * @return the result from the cantar.*/
    private int idHelp(int a, int b) {
        return (a + b) * (a + b) + (3 * a) + b;
    }
    /** The graph is stored as a hashmap mapping vertexes to
    * lists of predecessors and successors. */
    protected HashMap<Integer, ArrayList<ArrayList<Integer>>> _graph;
    /** An arraylist of all the edges. */
    private ArrayList<int[]> edges;
    /** Stores the max vertex. */
    private int max;
}
