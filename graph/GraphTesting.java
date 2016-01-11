package graph;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.*;


public class GraphTesting {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void directedTests() {
        DirectedGraph G = new DirectedGraph();
        G.add(); G.add(); G.add(); G.add();
        assertEquals(4, G.vertexSize());
        assertEquals(4, G.maxVertex());
        G.add(1, 2); G.add(2, 3); G.add(3, 1); G.add(1, 3);
        G.remove(5);
        assertEquals(4, G.edgeSize());
        assertEquals(true, G.contains(2));
        assertEquals(false, G.contains(5));
        assertEquals(false, G.contains(3, 2));
        assertEquals(true, G.contains(1, 2));
        assertEquals(2, G.outDegree(1));
        assertEquals(0, G.outDegree(4));
        assertEquals(1, G.outDegree(2));
        assertEquals(2, G.inDegree(3));
        assertEquals(1, G.inDegree(2));
        assertEquals(3, G.successor(1, 1));
        assertEquals(0, G.successor(1, 2));
        assertEquals(0, G.predecessor(4, 0));
        assertEquals(2, G.predecessor(3, 0));
        assertEquals(3, G.predecessor(1, 0));
        assertEquals(true, G.contains(1));
        assertEquals(true, G.contains(2));
        assertEquals(true, G.contains(3));
        assertEquals(true, G.contains(4));
        int[] verts = new int[G.vertexSize()];
        int c = 0;
        for (Integer i : G.vertices()) {
            verts[c] = i;
            c++;
        }
        int[] vertR = {1, 2, 3, 4};
        assertEquals(Arrays.equals(vertR, verts), true);
        int c2 = 0;
        int[] sucs = new int[2];
        for (Integer i : G.successors(1)) {
            sucs[c2] = i;
            c2++;
        }
        int[] sucsR = {2, 3};
        assertEquals(Arrays.equals(sucsR, sucs), true);
        int c3 = 0;
        int[] pres = new int[2];
        for (Integer i : G.predecessors(3)) {
            pres[c3] = i;
            c3++;
        }
        int[] presR = {2, 1};
        assertEquals(Arrays.equals(presR, pres), true);
        G.remove(2);
        assertEquals(false, G.contains(2));
        assertEquals(true, G.mine(1));
        assertEquals(false, G.mine(2));
        assertEquals(2, G.edgeSize());
        G.remove(1, 3);
        assertEquals(1, G.edgeSize());
    }


    @Test
    public void undirectedTests() {
        UndirectedGraph G = new UndirectedGraph();
        G.add(); G.add(); G.add(); G.add();
        assertEquals(4, G.vertexSize());
        assertEquals(4, G.maxVertex());
        G.add(1, 2);
        G.add(2, 3);
        G.add(3, 4);
        G.add(4, 1);
        G.remove(5);
        assertEquals(4, G.edgeSize());
        assertEquals(true, G.contains(2));
        assertEquals(false, G.contains(5));
        assertEquals(true, G.contains(3, 2));
        assertEquals(true, G.contains(1, 2));
        assertEquals(2, G.outDegree(1));
        assertEquals(0, G.outDegree(5));
        assertEquals(2, G.inDegree(3));
        assertEquals(4, G.successor(1, 1));
        assertEquals(0, G.successor(1, 2));
        assertEquals(1, G.successor(2, 0));
        assertEquals(3, G.successor(4, 0));
        assertEquals(3, G.predecessor(4, 0));
        assertEquals(2, G.predecessor(3, 0));
        assertEquals(2, G.predecessor(1, 0));

        int[] verts = new int[G.vertexSize()];
        int c = 0;
        for (Integer i : G.vertices()) {
            verts[c] = i;
            c++;
        }
        int[] vertR = {1, 2, 3, 4};
        assertEquals(Arrays.equals(vertR, verts), true);
        int[] sucs = new int[2];
        int c1 = 0;
        for (Integer i : G.successors(1)) {
            sucs[c1] = i;
            c1++;
        }
        int[] sucsR = {2, 4};
        assertEquals(Arrays.equals(sucsR, sucs), true);
        int[] pres = new int[2];
        int c2 = 0;
        for (Integer i : G.predecessors(1)) {
            pres[c2] = i;
            c2++;
        }
        int[] presR = {2, 4};
        assertEquals(true, Arrays.equals(presR, pres));
        G.remove(2);
        assertEquals(false, G.mine(2));
        assertEquals(2, G.edgeSize());
        G.remove(1, 3);
        assertEquals(2, G.edgeSize());
    }
}
