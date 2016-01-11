package graph;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
*  @author Rohit Muralidharan
*/
public class TraversalTesting {

    private class FindMaxx extends DepthFirstTraversal {

        public FindMaxx(Graph G) {
            super(G);
        }

        @Override
        protected boolean visit(int v) {
            visited.add(v);
            return super.visit(v);
        }

        @Override
        protected boolean shouldPostVisit(int v) {
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            postvisited.add(v);
            return super.postVisit(v);
        }

        private ArrayList<Integer> getVisited() {
            return visited;
        }

        private ArrayList<Integer> getPisited() {
            return postvisited;
        }
        private ArrayList<Integer> visited = new ArrayList<Integer>();
        private ArrayList<Integer> postvisited = new ArrayList<Integer>();
    }

    @Test
    public void dfsTravTest() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);
        FindMaxx bread = new FindMaxx(g);
        bread.traverse(5);

        List<Integer> result1 = asList(5, 4, 1, 3, 2);
        List<Integer> result2 = asList(5, 3, 2, 4, 1);

        if (bread.getVisited().get(0) == 5 && bread.getVisited().get(1) == 4) {
            assertEquals(result1, bread.getVisited());
        } else {
            assertEquals(result2, bread.getVisited());
        }

        List<Integer> result3 = asList(1, 4, 2, 3, 5);
        List<Integer> result4 = asList(2, 3, 1, 4, 5);

        if (bread.getPisited().get(0) == 1) {
            assertEquals(result3, bread.getPisited());
        } else {
            assertEquals(result4, bread.getPisited());
        }

    }

    private class FindMax extends BreadthFirstTraversal {

        public FindMax(Graph G) {
            super(G);
        }

        protected boolean visit(int v) {
            visited.add(v);
            return super.visit(v);
        }

        private ArrayList<Integer> getVisited() {
            return visited;
        }
        private ArrayList<Integer> visited = new ArrayList<Integer>();
    }

    @Test
    public void bfsTravTest() {
        DirectedGraph g = new DirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(5, 4);
        g.add(5, 3);
        g.add(4, 1);
        g.add(3, 2);
        g.add(1, 5);

        FindMax bread = new FindMax(g);
        bread.traverse(5);
        List<Integer> result1 = asList(5, 4, 3, 1, 2);
        List<Integer> result2 = asList(5, 3, 4, 1, 2);
        List<Integer> result3 = asList(5, 4, 3, 2, 1);
        List<Integer> result4 = asList(5, 3, 4, 2, 1);

        if (bread.getVisited().get(1) == 4 && bread.getVisited().get(3) == 1) {
            assertEquals(result1, bread.getVisited());
        } else if (bread.getVisited().get(1) == 3
            && bread.getVisited().get(3) == 1) {
            assertEquals(result2, bread.getVisited());
        } else if (bread.getVisited().get(1) == 4
            && bread.getVisited().get(3) == 2) {
            assertEquals(result3, bread.getVisited());
        } else {
            assertEquals(result4, bread.getVisited());
        }
    }
}
