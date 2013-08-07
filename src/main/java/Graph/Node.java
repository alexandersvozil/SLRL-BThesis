package Graph;

import Graph.Edge;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/4/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {
   private Set<Edge> edges;
   private static int counter;
   private int id;
   private String name;

   public Node (String name,Set<Edge> edges){
    this.id= counter++;
    this.edges = edges;
    this.name = name;
   }
    public Node (String name)
    {
        this.id= counter++;
        this.name = name;
        edges = new LinkedHashSet<Edge>();
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Node.counter = counter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null){
            throw new IllegalArgumentException("Node name is null");
        }
        this.name = name;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge e) {
        if (e == null)
                throw new IllegalArgumentException("Edge is null");
        if(e.getNode1() == null)
                throw new IllegalArgumentException("node 1 is null");
        if(e.getNode2() == null)
                throw new IllegalArgumentException("node 2 is null");
        if(e.getNode1() != this)
                throw new IllegalArgumentException("node1 is not the origin node");
        edges.add(e);
    }


    public void BFS(Node e) {

    }
}
