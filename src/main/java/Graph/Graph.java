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
            for(Edge e : n.getEdges()){
                e.setTimesUsed(0);
            }
        }
    }
}
