package Graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/5/13
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    private Set<Node> graph;

    public Graph (Set<Node> graph){
        this.graph = graph;
    }

    public Graph(){
        graph = new HashSet<Node>();
    }

    public void addNode(Node n){
        if(n == null)
            throw new IllegalArgumentException();
        graph.add(n);
    }
    public int size() {
        return graph.size();
    }

    public void clearUsages () {
        for(Node n : graph) {
            n.resetNeighbourhood();
            for(Edge e : n.getEdges()){
                e.setTimesUsed(0);
            }
        }
    }

    /**
     * returns maximum of m(e)
     */
    public int getMaxUsage () {
        int max = 0;
        for(Node n : graph) {
            for(Edge e : n.getEdges()){
                if (max < e.getTimesUsed())
                    max = e.getTimesUsed();
            }
        }
        return max;

    }

    public Set<Node> getGraph() {
        return graph;
    }

    public int getMaxNeighbourSet() {
        int max = 0;
        for(Node n : graph) {
            if (max < n.getNeighbourhood())
            {
                max = n.getNeighbourhood();
            }
        }
        return max;  //To change body of created methods use File | Settings | File Templates.
    }

    public void clearParents() {
        for(Node n : graph) {
            n.setParent(null);
        }

    }
}
