import org.apache.log4j.Logger;
import org.junit.Test;
import Graph.*;

import java.util.LinkedHashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/5/13
 * Time: 5:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestGraph {

    private Logger log =  Logger.getLogger(TestGraph.class);

    @Test
    public void integrityGraphTest(){
        Graph tGraph = new Graph();
        Node n = new Node("Vienna");
        tGraph.addNode(n);
        assertTrue(tGraph.size()==1);
        Node n1 = new Node("Vienna,US");
        Node n2 = new Node("Sydney,AUS");
        tGraph.addNode(n1);
        tGraph.addNode(n2);
        assertTrue(tGraph.size() == 3);
        assertTrue(n2.getName().equals("Sydney,AUS"));

        Edge e4 = new Edge(n,n1);

        Edge e = new Edge(n1,n2);
        Edge e3 = new Edge(n1,n);

        Edge e2 = new Edge(n2,n1);


        n.addEdge(e4);

        n1.addEdge(e);
        n1.addEdge(e3);

        n2.addEdge(e2);

        assertTrue(e3.getTimesUsed() == 0);
        assertTrue(!n1.getEdges().isEmpty());
        assertTrue(!n.getEdges().isEmpty());
        assertTrue(!n2.getEdges().isEmpty());
    }

    @Test
    public void testBFS() throws NodeNotFoundException {
        Node A = new Node ("A");
        Node B = new Node ("B");
        Node C = new Node ("C");
        Node D = new Node ("D");
        Node E = new Node ("E");
        Node F = new Node ("F");
        Node G = new Node ("G");

        Graph graph = new Graph ();
        graph.addNode(A);
        graph.addNode(B);
        graph.addNode(C);
        graph.addNode(D);
        graph.addNode(E);
        graph.addNode(F);

        Edge e1_A = new Edge(A,B);
        Edge e1_B = new Edge(B,A);

        A.addEdge(e1_A);
        B.addEdge(e1_B);


       Edge e2_B = new Edge(B,D);
       Edge e2_D = new Edge(D,B);

        B.addEdge(e2_B);
        D.addEdge(e2_D);

        Edge e5_B = new Edge (B,C);
        Edge e5_C = new Edge (C,B);

        B.addEdge(e5_B);
        C.addEdge(e5_C);

        Edge e3_D = new Edge(D,F);
        Edge e3_F = new Edge(F,D);

        D.addEdge(e3_D);
        F.addEdge(e3_F);

        Edge e4_F = new Edge(F,E);
        Edge e4_E = new Edge(E,F);

        F.addEdge(e4_F);
        E.addEdge(e4_E);


        Edge e6_C = new Edge(C,E);
        Edge e6_E = new Edge(E,C);

        C.addEdge(e6_C);
        E.addEdge(e6_E);

       Node path =  A.BFS(E);

        while(path.getParent() != null){
            log.debug(path);
            path = path.getParent();

        }
        log.debug(A);
        assertTrue(e6_C.getTimesUsed()==1);
        assertTrue(e6_E.getTimesUsed()==1);

        assertTrue(e5_B.getTimesUsed()==1);
        assertTrue(e5_C.getTimesUsed()==1);

        assertTrue(e1_B.getTimesUsed()==1);
        assertTrue(e1_A.getTimesUsed()==1);


        graph.clearUsages();

        assertTrue(e6_C.getTimesUsed()==0);
        assertTrue(e6_E.getTimesUsed()==0);
        assertTrue(e5_B.getTimesUsed()==0);
        assertTrue(e5_C.getTimesUsed()==0);
        assertTrue(e1_B.getTimesUsed()==0);
        assertTrue(e1_A.getTimesUsed()==0);


    }
    @Test (expected = NodeNotFoundException.class)
    public void testInvalidRoute () throws NodeNotFoundException {
        Node A = new Node ("A");
        Node B = new Node ("B");
        Node C = new Node ("C");
        Node D = new Node ("D");
        Node E = new Node ("E");
        Node F = new Node ("F");
        Node G = new Node ("G");

        Edge e1_A = new Edge(A,B);
        Edge e1_B = new Edge(B,A);

        A.addEdge(e1_A);
        B.addEdge(e1_B);


        Edge e2_B = new Edge(B,D);
        Edge e2_D = new Edge(D,B);

        B.addEdge(e2_B);
        D.addEdge(e2_D);

        Edge e5_B = new Edge (B,C);
        Edge e5_C = new Edge (C,B);

        B.addEdge(e5_B);
        C.addEdge(e5_C);

        Edge e3_D = new Edge(D,F);
        Edge e3_F = new Edge(F,D);

        D.addEdge(e3_D);
        F.addEdge(e3_F);

        Edge e4_F = new Edge(F,E);
        Edge e4_E = new Edge(E,F);

        F.addEdge(e4_F);
        E.addEdge(e4_E);


        Edge e6_C = new Edge(C,E);
        Edge e6_E = new Edge(E,C);

        C.addEdge(e6_C);
        E.addEdge(e6_E);

        E.BFS(G);

    }





}
