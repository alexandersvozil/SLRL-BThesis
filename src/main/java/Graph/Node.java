package Graph;


import ads1.graphprinter.Traversable;
import ads1.graphprinter.attributes.GPAttribute;
import ads1.graphprinter.attributes.LabelAttr;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/4/13
 * Time: 6:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node implements Traversable {
    private List<Edge> edges;
    private static int counter;
    private int id;
    private String name;
    private Logger log =  Logger.getLogger(Node.class);
    //to get the shortest path
    private Node parent;
    private boolean isServer;
    private int neighbourhood;
    private int maxEdgeUsage;
    private String label;
    //to get EVERY shortest path
    private List<Node> parents;

    private int distance;

    public Node (Node n){
        this.id = n.id;
        this.name = n.getName();
        edges = new ArrayList<Edge>();
        this.parent = n.getParent();
        isServer = n.isServer();
        this.neighbourhood = n.getNeighbourhood();
        this.maxEdgeUsage = n.getMaxEdgeUsage();
        parents = new CopyOnWriteArrayList<Node>();
    }

    public Node(String name) {
        this.id = counter++;
        this.name = name;
        edges = new ArrayList<Edge>();
        parents = new CopyOnWriteArrayList<Node>();
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

    public List<Edge> getEdges() {
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
        if(!getEdges().contains(e))
        edges.add(e);
    }


    /**
     * BFS. WARNING this does not work with the algorithm, because it doesn't find EVERY shortest path.
     * needs modification
     * @param goalNode
     * @return
     * @throws NodeNotFoundException
     */
    public Node BFS(Node goalNode) throws NodeNotFoundException {
        /** pseudocode von wikipedia entnommen: http://de.wikipedia.org/wiki/Breitensuche **/
        if (goalNode == null)
            throw new IllegalArgumentException("goalNode is null");
        Queue<Node> nodeQueue = new LinkedList<Node>();
        //for the nodes already visited. HashSet because "contains" is O(1)
        HashSet<Node> visited = new HashSet<Node>();
        visited.add(this);
        nodeQueue.offer(this);
        this.distance = 0;
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
        throw new NodeNotFoundException("Node was not found by bfs: Goal: " + goalNode.getName()+ " Start: "+ this.getName());
    }




    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void removeParent(Node parent) {
        if(!(parent==null))
            parents.remove(parent);

    }

    class Nodepair {
        Node node;
        Node parent;
       public Nodepair(Node node, Node parent){
           this.node = node;
           this.parent = parent;
       }

    }


    /**
     * adds parent into parents
     * @param parent
     */
    public void addParent(Node parent){
        if(!(parent==null))
        parents.add(parent);
    }

    /**
     * FOR BFS
     * @param n
     */
    public void markPath(Node n) {
        Node tmp = n;
        Node lastNode = null;
        while(tmp.getParent() != null)
        {
          for(Edge e : tmp.getEdges())
          {
              if(e.getNode2().equals(tmp.getParent()) || e.getNode2().equals(lastNode)){
                //System.out.println(e.getNode1() + "  " + e.getNode2());
                e.setTimesUsed(e.getTimesUsed()+1);
              }
          }
          lastNode = tmp;
          tmp = tmp.getParent();
        }

        for(Edge e : this.getEdges())
        {
            if(e.getNode2().equals(tmp.getParent()) || e.getNode2().equals(lastNode)){
                //System.out.println(e.getNode1() + "  " + e.getNode2());
                e.setTimesUsed(e.getTimesUsed()+1);
            }
        }

    }


    @Override
    public String toString() {
        String parentss = "";
        for(Node n : parents){
            parentss+=" "+n.getName();

        }
        return "Node{" +
                "id=" + id +
                ", name='" + name + '\'' +
                "parents:"+ parentss+
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

    public void setEdges(List<Edge> edges) {
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

    @Override
    public int getID() {
        return id;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLabel() {
        //return getName();
        if(isServer)
        return label+"SERVER ";// + getName() ;  //To change body of implemented methods use File | Settings | File Templates.
        else
        return  label+" ";// + getName() ;
    }

    @Override
    public Traversable[] getChildren() {
        ArrayList<Traversable> traversables = new ArrayList<Traversable>();
        for(Edge e : edges){
            traversables.add(e.getNode2());
        }
        return traversables.toArray(new Traversable[traversables.size()]);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<GPAttribute> getEdgeAttributes(int i) {
       //log.debug(i);
       List<GPAttribute> gpAttributes = new ArrayList<GPAttribute>();
        for(Edge e: edges) {
            GPAttribute attr = new LabelAttr(""+e.getTimesUsed());
            gpAttributes.add(attr);
        }
        return null;
    }

    @Override
    public List<GPAttribute> getNodeAttributes() {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Node> getParents() {
        return parents;
    }

    public void setParents(List<Node> parents) {
        this.parents = parents;
    }
}
