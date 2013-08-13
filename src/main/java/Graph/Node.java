package Graph;

import Graph.Edge;
import org.apache.log4j.Logger;

import java.util.*;

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
    private Logger log =  Logger.getLogger(Node.class);
    private Node parent;
    private boolean isServer;
    private int neighbourhood;
    private int maxEdgeUsage;

    public Node(String name) {
        this.id = counter++;
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
        if (name == null) {
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
        if (e.getNode1() == null)
            throw new IllegalArgumentException("node 1 is null");
        if (e.getNode2() == null)
            throw new IllegalArgumentException("node 2 is null");
        if (e.getNode1() != this)
            throw new IllegalArgumentException("node1 is not the origin node");
        edges.add(e);
    }


    public Node BFS(Node goalNode) throws NodeNotFoundException {
        /** pseudocode von wikipedia entnommen: http://de.wikipedia.org/wiki/Breitensuche **/
        if (goalNode == null)
            throw new IllegalArgumentException("goalNode is null");
        Queue<Node> nodeQueue = new LinkedList<Node>();
        //for the nodes already visited. HashSet because "contains" is O(1)
        HashSet<Node> visited = new HashSet<Node>();
        visited.add(this);
        nodeQueue.offer(this);
        while (!nodeQueue.isEmpty()) {
            Node node = nodeQueue.poll();
            //log.debug("expanding"+node);
            if (node.equals(goalNode)) {
             //ziel erreicht
            //log.debug("Found node"+ goalNode+"coming from"+ node.getParent());
            return goalNode;
            }
                for(Edge e: node.getEdges()){
                    if(!visited.contains(e.getNode2())){
                        nodeQueue.offer(e.getNode2());
                        e.getNode2().setParent(node);
                        visited.add(e.getNode2());
                    }
            }
        }
        throw new NodeNotFoundException("Node was not found by bfs");
    }

    public void markPath(Node n) {
        Node tmp = n;
        Node lastNode = null;
        while(tmp.getParent() != null)
        {
          for(Edge e : tmp.getEdges())
          {
              if(e.getNode2().equals(tmp.getParent()) || e.getNode2().equals(lastNode)){
                e.setTimesUsed(e.getTimesUsed()+1);
              }
          }
          lastNode = tmp;
          tmp = tmp.getParent();
        }
        for(Edge e : this.getEdges())
        {
            if(e.getNode2().equals(tmp.getParent()) || e.getNode2().equals(lastNode)){
                e.setTimesUsed(e.getTimesUsed()+1);
            }
        }

    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;

        return true;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setEdges(Set<Edge> edges) {
        this.edges = edges;
    }

    public boolean isServer() {
        return isServer;
    }

    public void setServer(boolean server) {
        isServer = server;
    }
    public void increaseNeighbourhood(){
        this.neighbourhood++;
    }
    public void resetNeighbourhood(){
        this.neighbourhood = 0;
    }

    public int getNeighbourhood() {
        return neighbourhood;
    }

    public int getMaxEdgeUsage() {
        return maxEdgeUsage;
    }

    public void setMaxEdgeUsage(int maxEdgeUsage) {
        this.maxEdgeUsage = maxEdgeUsage;
    }
}
