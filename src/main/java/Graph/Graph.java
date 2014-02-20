package Graph;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/5/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private List<Node> graph;
    private List<Node> servers;
    private Logger log = Logger.getLogger(Graph.class);
    private List<Edge> usedEdgesInShortestPaths;
    long sumsum;
    Map<Node, ShortestPaths> pathsMap;

    public void resetDistance() {
        for (Node n : graph) {
            n.setDistance(-1);
        }

    }

    public void addServer(Node server) {
        server.setServer(true);
        servers.add(server);
    }

    public void removeServer(Node exServer) {
        exServer.setServer(false);
        servers.remove(exServer);
    }

    public Graph(List<Node> graph) {
        this.graph = graph;
        this.servers = new CopyOnWriteArrayList<Node>();
        this.usedEdgesInShortestPaths = new ArrayList<Edge>();

    }

    public Graph(List<Node> graph, List<Node> servers) {
        this.graph = new ArrayList<Node>();
        this.graph.addAll(graph);
        this.servers = new CopyOnWriteArrayList<Node>();
        this.servers.addAll(servers);
        this.usedEdgesInShortestPaths = new ArrayList<Edge>();
        this.pathsMap = createPathsMap();
    }

    public Map<Node, ShortestPaths> createPathsMap() {

        Map<Node, ShortestPaths> pathsMap = new HashMap<Node, ShortestPaths>();
        List<Node> nodeList = getGraph();
        for(Node n : nodeList){
            pathsMap.put(n,new ShortestPaths(n,this));
        }
       log.debug("precalculations done");
        this.pathsMap = pathsMap;
        return pathsMap;  //To change body of created methods use File | Settings | File Templates.
    }

    public Graph() {
        graph = new ArrayList<Node>();
        this.servers = new CopyOnWriteArrayList<Node>();
        this.usedEdgesInShortestPaths = new ArrayList<Edge>();
    }

    public void addNode(Node n) {
        if (n == null)
            throw new IllegalArgumentException();
        graph.add(n);
    }

    public int size() {
        return graph.size();
    }

    public void clearUsages() {
        for (Node n : graph) {
            n.resetNeighbourhood();
            n.setTmpNeighbourhood(0);
            for (Edge e : n.getEdges()) {
                e.setTimesUsed(0);
            }
        }
    }

    /**
     * returns maximum of m(e)
     */
    public int getMaxUsage() {
        int max = 0;
        for (Node n : graph) {
            for (Edge e : n.getEdges()) {
                if (max < e.getTimesUsed())
                    max = e.getTimesUsed();
            }
        }
        //System.out.println("Max usage: " + max);
        return max;

    }

    public List<Node> getGraph() {
        return graph;
    }

    public int getMaxNeighbourSet() {
        int max = 0;
        for (Node n : graph) {
            if (max < n.getNeighbourhood()) {
                max = n.getNeighbourhood();
            }
        }
        //System.out.println("Maximal neighbourhood: " + max);
        return max;  //To change body of created methods use File | Settings | File Templates.
    }

    public void clearParents() {
        for (Node n : graph) {
            n.setParent(null);
            n.getParents().clear();
        }

    }
    public void updateConstraints(){
        clearUsages();

        /*Map<Node, Integer> maxNeighborhood = new HashMap<Node, Integer>();
        for(Node n : getServers()){
            maxNeighborhood.put(n,0);
        }*/

        //check for the nearest servers of every member of the graph
        for(Node n : getGraph()){

            List<Node> nearestServers = new ArrayList<Node>();
            int currentLowest = -1;
            if(!getServers().contains(n)){
                for(Node server : getServers()){
                    ShortestPaths n_paths = pathsMap.get(n);
                    int i = n_paths.getPathLength().get(server);
                    if(currentLowest == -1 || i <= currentLowest){

                        if(currentLowest == i){
                            nearestServers.add(server);

                        }else{
                            nearestServers.clear();
                            nearestServers.add(server);
                            currentLowest = i;
                        }
                    }

                }

                //increment usage of each and every server
                //increment usage of the edges
                for(Node k : nearestServers){
                   // maxNeighborhood.put(k,maxNeighborhood.get(k)+1);
                    k.increaseNeighbourhood();
                    List<Edge> usedEdges = pathsMap.get(n).getShortest_Paths().get(k);

                    //mark used edges
                    for(Edge e : usedEdges){
                        e.setTimesUsed(e.getTimesUsed()+1);
                    }
                }
            }else{
                //maxNeighborhood.put(n,maxNeighborhood.get(n)+1);
                n.increaseNeighbourhood();
            }

        }

    }

    public void calculateUsagesAndNeighbourhoodsAfterServerAddition() {
        //log.debug("calculateUsages got called, current server size: "+servers.size());

        List<Node> nearestServers = new ArrayList<Node>();
        for (Node n : graph) {
            nearestServers.clear();
            int minStepsToServer = 0;
            if (!servers.contains(n)) {
                //look for nearest servers
                // log.debug("server size: " + servers.size());
                for (Node server : servers) {
                    Node p = null;
                    try {
                        p = BFS2(n, server);
                    } catch (NodeNotFoundException e) {
                        log.error("Node not found");
                        e.printStackTrace();
                        return;
                    }
                    int serverDistance = server.getDistance();
                    if (serverDistance <= minStepsToServer || nearestServers.isEmpty()) {
                        if (serverDistance < minStepsToServer) {
                            for (Node nServer : nearestServers) {
                                nServer.setTmpNeighbourhood(0);
                            }
                            nearestServers.clear();
                            usedEdgesInShortestPaths.clear();
                        }
                        minStepsToServer = serverDistance;
                        nearestServers.add(server);
                        server.setTmpNeighbourhood(server.getTmpNeighbourhood() + 1);
                        markPath2(server);
                    }
                }

                for (Node nearestServer : nearestServers) {

                    nearestServer.setNeighbourhood(nearestServer.getTmpNeighbourhood() + nearestServer.getNeighbourhood());
                    nearestServer.setTmpNeighbourhood(0);
                }
                //mark used edges
                markEdges();
            }
        }
    }

    /**
     * BFS2. Gets all paths from a node to another and stores it into the parents list of each node
     *
     * @param goalNode
     * @return
     * @throws NodeNotFoundException
     */
   /* public Node BFS2(Node from, Node goalNode) throws NodeNotFoundException {
        clearParents(); // VERY EXPENSIVE 5 Seconds!!!
        resetDistance();

        // pseudocode partly taken from wikipedia: http://de.wikipedia.org/wiki/Breitensuche
        if (goalNode == null)
            throw new IllegalArgumentException("goalNode is null");
        Queue<Node> nodeQueue = new LinkedList<Node>();
        HashMap<Integer, Node> nodeHash= new HashMap<Integer, Node>();
        //for the nodes already visited. HashSet because "contains" is O(1)
        HashSet<Node> visited = new HashSet<Node>();
        visited.add(from);
        nodeQueue.offer(from);
        nodeHash.put(from.getId(), from);
        while (!nodeQueue.isEmpty()) {
            Node curNode = nodeQueue.poll();

            visited.add(curNode);
            // log.debug("expanding"+nodepair.node);
            if (curNode.equals(goalNode)) {
                //ziel erreicht
                //log.debug("Found node"+ goalNode+"coming from"+ node.getParent());
                return goalNode;
            }

            for (Edge e : curNode.getEdges()) {
                if (!visited.contains(e.getNode2())) {
                    if (e.getNode2().getDistance() == curNode.getDistance() + 1 || e.getNode2().getDistance() == -1) {
                        if(nodeHash.containsKey(e.getNode2().getId())){
                            nodeHash.get(e.getNode2().getId()).addParent(curNode);

                        }else{
                            e.getNode2().addParent(curNode);
                            nodeQueue.offer(e.getNode2());
                            nodeHash.put(e.getNode2().getId(), e.getNode2());

                        }

                        e.getNode2().setDistance(curNode.getDistance() + 1);

                    }

                }
            }
        }

        throw new NodeNotFoundException("Node was not found by bfs: Goal: " + goalNode.getName() + " Start: " + from.getName());
    }*/
    public Node BFS2(Node from, Node goalNode) throws NodeNotFoundException {
        clearParents(); // VERY EXPENSIVE 5 Seconds!!!
        resetDistance();

        // pseudocode partly taken from wikipedia: http://de.wikipedia.org/wiki/Breitensuche
        if (goalNode == null)
            throw new IllegalArgumentException("goalNode is null");
        Queue<Node> nodeQueue = new LinkedList<Node>();
        //for the nodes already visited. HashSet because "contains" is O(1)
        HashSet<Node> visited = new HashSet<Node>();
        visited.add(from);
        nodeQueue.offer(from);
        while (!nodeQueue.isEmpty()) {
            Node curNode = nodeQueue.poll();
            visited.add(curNode);
            // log.debug("expanding"+nodepair.node);
            if (curNode.equals(goalNode)) {
                //ziel erreicht
                //log.debug("Found node"+ goalNode+"coming from"+ node.getParent());
                return goalNode;
            }

            for (Edge e : curNode.getEdges()) {
                if (!visited.contains(e.getNode2())) {
                    if (e.getNode2().getDistance() == curNode.getDistance() + 1 || e.getNode2().getDistance() == -1) {
                        boolean alreadyVis = false;
                        for(Node node : nodeQueue){
                            if(node.equals(e.getNode2()) ){
                                node.addParent(curNode);
                                alreadyVis = true;
                                break;
                            }
                        }

                        if(!alreadyVis){
                        nodeQueue.offer(e.getNode2());
                        e.getNode2().addParent(curNode);
                        }
                        e.getNode2().setDistance(curNode.getDistance() + 1);
                        }

                    }
                }
            }

        throw new NodeNotFoundException("Node was not found by bfs: Goal: " + goalNode.getName() + " Start: " + from.getName());
    }

    /**
     * Marks all edges used by the paths. Then clears the list
     */
    public void markEdges() {
        for (Edge e : usedEdgesInShortestPaths) {
            e.setTimesUsed(e.getTimesUsed() + 1);
        }
        usedEdgesInShortestPaths.clear();
    }

    public int getServerSize() {
        return servers.size();
    }

    public List<Node> getServers() {
        return servers;
    }

    class Nodepair {
        Node node;
        Node parent;

        public Nodepair(Node node, Node parent) {
            this.node = node;
            this.parent = parent;
        }

    }

    /**
     * FOR BFS2
     * marks all edges used and puts them into the usedEdgesInShortestPaths List. This List will mark all used Edges
     *
     * @param n
     */
    public void markPath2(Node n) {
        Node tmp = n;

        if (tmp.getParents().isEmpty())
            return;

        int number = 0;
        for (Node parent : tmp.getParents()) {
            for (Edge e : tmp.getEdges()) {
                if (e.getNode2().equals(parent)) {
                    if (!usedEdgesInShortestPaths.contains(e)) {
                        usedEdgesInShortestPaths.add(e);
                    }
                }
            }
            for (Edge e : parent.getEdges()) {
                if (e.getNode2().equals(tmp)) {
                    if (!usedEdgesInShortestPaths.contains(e)) {
                        usedEdgesInShortestPaths.add(e);
                    }


                }
            }
            markPath2(parent);

        }

    }
    /**
     * FOR BFS2
     * marks all edges used and puts them into a List that is returned.
     *
     * @param n
     */
    public List<Edge> markPath2_list(Node n) {
        Node tmp = n;
        List<Edge> usedEdges = new ArrayList<Edge>();

        if (tmp.getParents().isEmpty())
            return usedEdges;

        for (Node parent : tmp.getParents()) {
            for (Edge e : tmp.getEdges()) {
                if (e.getNode2().equals(parent)) {
                    if (!usedEdges.contains(e)) {
                        usedEdges.add(e);
                    }
                }
            }
            for (Edge e : parent.getEdges()) {
                if (e.getNode2().equals(tmp)) {
                    if (!usedEdges.contains(e)) {
                        usedEdges.add(e);
                    }


                }
            }
            markPath2_list(parent);

        }
        return usedEdges;

    }
}
