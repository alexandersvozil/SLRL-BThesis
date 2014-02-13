import ads1.graphprinter.GraphPrinter;
import ads1.graphprinter.Traversable;
import org.apache.log4j.Logger;
import org.junit.Test;
import Graph.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

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

        Graph graph1 = new Graph();
        graph1.addNode(A);
        graph1.addNode(B);
        graph1.addNode(C);
        graph1.addNode(D);
        graph1.addNode(E);
        graph1.addNode(F);

       Node path =  graph1.BFS2(A,E);
        graph1.markPath2(path);
        graph1.markEdges();
        snapshotG(graph);
        //log.debug(A);
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
    @Test
    public void test_moreThanOneShortestPath2() throws Exception {
        Node A = new Node ("A");
        Node B = new Node ("B");
        Node C = new Node ("C");
        Node D = new Node ("D");
        Node E = new Node ("E");
        Node F = new Node ("F");

        Edge e1_A = new Edge(A,B);
        Edge e1_B = new Edge(B,A);
        A.addEdge(e1_A);
        B.addEdge(e1_B);

        Edge e2_A = new Edge(A,C);
        Edge e2_C = new Edge(C,A);
        A.addEdge(e2_A);
        C.addEdge(e2_C);

        Edge e3_A = new Edge(A,D);
        Edge e3_D = new Edge(D,A);
        A.addEdge(e3_A);
        D.addEdge(e3_D);



        Edge e4_E = new Edge(E,F);
        Edge e4_F = new Edge(F,E);
        E.addEdge(e4_E);
        F.addEdge(e4_F);

        Edge e5_B = new Edge(B,E);
        Edge e5_E = new Edge(E,B);
        B.addEdge(e5_B);
        E.addEdge(e5_E);


        Edge e6_C = new Edge(C,E);
        Edge e6_E = new Edge(E,C);
        C.addEdge(e6_C);
        E.addEdge(e6_E);


        Edge e7_D = new Edge(D,E);
        Edge e7_E = new Edge(E,D);
        D.addEdge(e7_D);
        E.addEdge(e7_E);

        Graph graph1 = new Graph();
        graph1.addNode(A);
        graph1.addNode(B);
        graph1.addNode(C);
        graph1.addNode(D);
        graph1.addNode(E);
        graph1.addNode(F);

        graph1.BFS2(A,F);
        graph1.markPath2(F);
        graph1.markEdges();
        graph1.clearParents();

        assertTrue(e1_B.getTimesUsed()==1);
        assertTrue(e1_A.getTimesUsed() == 1);

        assertTrue(e2_C.getTimesUsed()==1);
        assertTrue(e2_A.getTimesUsed() == 1);

        assertTrue(e3_A.getTimesUsed()==1);
        assertTrue(e3_D.getTimesUsed()==1);

        assertTrue(e4_E.getTimesUsed()==1);
        assertTrue(e4_F.getTimesUsed()==1);

        assertTrue(e5_E.getTimesUsed()==1);
        assertTrue(e5_B.getTimesUsed()==1);

        assertTrue(e6_E.getTimesUsed()==1);
        assertTrue(e6_C.getTimesUsed()==1);

        assertTrue(e7_E.getTimesUsed()==1);
        assertTrue(e7_D.getTimesUsed()==1);
        graph1.BFS2(E,F);
        graph1.markPath2(F);
        graph1.markEdges();
        graph1.clearParents();
        graph1.BFS2(B,F);
        graph1.markPath2(F);
        graph1.markEdges();
        graph1.clearParents();
        graph1.BFS2(C,F);
        graph1.markPath2(F);
        graph1.markEdges();
        graph1.clearParents();
        graph1.BFS2(D,F);
        graph1.markPath2(F);
        graph1.markEdges();
        graph1.clearParents();
        assertTrue(graph1.getMaxUsage()==5);
        assertTrue(e4_E.getTimesUsed()==5);
        assertTrue(e4_F.getTimesUsed()==5);
    }
    @Test
    public void test_moreThanOneShortestPath() throws Exception {
        Node A = new Node ("A");
        Node B = new Node ("B");
        Node C = new Node ("C");
        Node D = new Node ("D");

        Edge e1_A = new Edge(A,B);
        Edge e1_B = new Edge(B,A);
        A.addEdge(e1_A);
        B.addEdge(e1_B);

        Edge e2_A = new Edge(A,C);
        Edge e2_C = new Edge(C,A);
        A.addEdge(e2_A);
        C.addEdge(e2_C);

        Edge e3_B = new Edge(B,D);
        Edge e3_D = new Edge(D,B);
        B.addEdge(e3_B);
        D.addEdge(e3_D);

        Edge e4_C = new Edge(C,D);
        Edge e4_D = new Edge(D,C);
        C.addEdge(e4_C);
        D.addEdge(e4_D);


        Graph graph1 = new Graph();
        graph1.addNode(A);
        graph1.addNode(B);
        graph1.addNode(C);
        graph1.addNode(D);

        Node path = graph1.BFS2(A,D);
        graph1.markPath2(path);
        graph1.markEdges();
        assertTrue(e1_B.getTimesUsed()==1);
        assertTrue(e1_A.getTimesUsed() == 1);

        assertTrue(e2_C.getTimesUsed()==1);
        assertTrue(e2_A.getTimesUsed() == 1);

        assertTrue(e3_B.getTimesUsed()==1);
        assertTrue(e3_D.getTimesUsed()==1);

        assertTrue(e4_C.getTimesUsed()==1);
        assertTrue(e4_D.getTimesUsed()==1);
    }
    @Test
    public void testComplicatedRoute() throws  NodeNotFoundException{
        Node A = new Node ("A");
        Node B = new Node ("B");
        Node C = new Node ("C");
        Node D = new Node ("D");
        Node E = new Node ("E");
        Node F = new Node ("F");

        Edge e1_A = new Edge(A,B);
        Edge e1_B = new Edge(B,A);
        A.addEdge(e1_A);
        B.addEdge(e1_B);

        Edge e2_A = new Edge(A,C);
        Edge e2_C = new Edge(C,A);
        A.addEdge(e2_A);
        C.addEdge(e2_C);

        Edge e3_D = new Edge(D,A);
        Edge e3_A = new Edge(A,D);
        D.addEdge(e3_D);
        A.addEdge(e3_A);

        Edge EC = new Edge (E,C);
        Edge CE = new Edge (C,E);
        E.addEdge(EC);
        C.addEdge(CE);

        Edge EF = new Edge(E,F);
        Edge FE = new Edge (F,E);

        E.addEdge(EF);
        F.addEdge(FE);

        Edge BF = new Edge(B,F);
        Edge FB = new Edge(F,B);

        B.addEdge(BF);
        F.addEdge(FB);

        Edge DF = new Edge(D,F);
        Edge FD = new Edge(F,D);

        D.addEdge(DF);
        F.addEdge(FD);

        Graph graph1 = new Graph();
        graph1.addNode(A);
        graph1.addNode(B);
        graph1.addNode(C);
        graph1.addNode(D);
        graph1.addNode(E);
        graph1.addNode(F);

        graph1.addServer(F);
        graph1.calculateUsagesAndNeighbourhoodsAfterServerAddition();
        //FEHLER
        assertTrue(BF.getTimesUsed()==2);
        assertTrue(DF.getTimesUsed()==2);




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
        Graph graph1 = new Graph();
        graph1.addNode(A);
        graph1.addNode(B);
        graph1.addNode(C);
        graph1.addNode(D);
        Node path = graph1.BFS2(A,G);


    }
    public void snapshotG(Graph g){
        List<String> dirty = new ArrayList<String>();
        dirty.add("-g");
        GraphPrinter gp = GraphPrinter.ParseArgs(dirty);
        gp.put("testgraphprint", g.getGraph().toArray(new Traversable[g.size()]), false);
        gp.print();

    }





}
