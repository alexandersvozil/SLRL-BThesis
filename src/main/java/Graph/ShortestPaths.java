package Graph;
/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 2/19/14
 * Time: 1:53 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class stores all the shortest paths for a node to every other node, and also the
 * pathlength to every node.
 */
public class ShortestPaths {
    private Map<Node, List<Edge>> shortest_Paths;
    private Map<Node, Integer> pathLength;
    public ShortestPaths(Node n, Graph graph){
        shortest_Paths = new HashMap<Node, List<Edge>>();
        pathLength = new HashMap<Node, Integer>();

        enrichPaths(n,graph);
    }

    private void enrichPaths(Node n, Graph graph) {
        for(Node curNode : graph.getGraph())
            if(!curNode.equals(n)){
                try {
                    Node path = graph.BFS2(n,curNode);
                    pathLength.put(curNode, path.getDistance());
                    //the edges used, are stored in the parent nodes, so we need to extract them
                    List<Edge> usedEdges = graph.markPath2_list(path);
                    shortest_Paths.put(curNode,usedEdges);
                    graph.clearParents();
                } catch (NodeNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    public Map<Node, List<Edge>> getShortest_Paths() {
        return shortest_Paths;
    }

    public Map<Node, Integer> getPathLength() {
        return pathLength;
    }
}
