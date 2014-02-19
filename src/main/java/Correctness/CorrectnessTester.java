package Correctness;

import Graph.Graph;
import Graph.Node;
import Graph.Edge;
import Graph.ShortestPaths;
import Parsing.SLRLInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alexander
 * Date: 2/19/14
 * Time: 1:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class CorrectnessTester {
    /**
     *  Tests correctness of given SLRL Soluion, i.e., neighbourhood constraint r, edge usage c,
     *  size of Server set k
     * @param slrlInstance the solved instance of the problem
     * @throws SolutionWrongException this is thrown if the solution is not correct.
     */
    public void testCorrectness(SLRLInstance slrlInstance) throws SolutionWrongException{
        //The instance was not solved, cannot check for correctness here
        Graph graph = slrlInstance.getGraph();
        if(slrlInstance.getSolved()==false)
            return;

        //k does not equal server size
        if(slrlInstance.getGraph().getServers().size() != slrlInstance.getK()){
            throw new SolutionWrongException("Server size does not equal constraint k");
        }

        /*prior to checking the neighborhood constraint, we will create a map pathsMap with
          <Node_a,ShortestPaths>. ShortestPaths is a object that stores every shortest Path from Node_a to any other
          Node in the Graph. This will allow us to get the nearest Server in O(s) and also to mark the Path very fast
          to keep track of the usage
        */
        Map<Node, ShortestPaths> pathsMap = createPathsMap(slrlInstance.getGraph());
        graph.clearUsages();

        /* set the neighborhoods to zero at the beginning */
        Map<Node, Integer> maxNeighborhood = new HashMap<Node, Integer>();
        for(Node n : slrlInstance.getGraph().getServers()){
            maxNeighborhood.put(n,0);
        }

        //check for the nearest servers of every member of the graph
        for(Node n : slrlInstance.getGraph().getGraph()){

            List<Node> nearestServers = new ArrayList<Node>();
            int currentLowest = -1;
            if(!graph.getServers().contains(n)){
                for(Node server : slrlInstance.getGraph().getServers()){
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
                    maxNeighborhood.put(k,maxNeighborhood.get(k)+1);
                    List<Edge> usedEdges = pathsMap.get(n).getShortest_Paths().get(k);

                    //mark used edges
                    for(Edge e : usedEdges){
                        e.setTimesUsed(e.getTimesUsed()+1);
                    }
                }
            }else{
                maxNeighborhood.put(n,maxNeighborhood.get(n)+1);
            }

        }

        int max = -1;
        for(Node n : maxNeighborhood.keySet()){
            //first test, read out the usages

            System.out.println("Server "+ n.getName()+ " has neighborhood of " + maxNeighborhood.get(n));
            if(max < maxNeighborhood.get(n))
            {
                max = maxNeighborhood.get(n);
            }
        }
        //check if the neighhborhood constraint is fulfilled
        if(max>slrlInstance.getR())
        {
            throw new SolutionWrongException("The neighborhood constraint was not fulfilled:" +
                    "" + max +" is more than the allowed r:" + slrlInstance.getR());
        }

        int max_edge = -1;
        for(Node e : graph.getServers()){
            for(Edge edge : e.getEdges()){
                if(max_edge < edge.getTimesUsed()){
                    max_edge = edge.getTimesUsed();
                }
            }
        }
        System.out.println("m(e) is " + max_edge);
        if(max_edge > slrlInstance.getC()){
            throw  new SolutionWrongException("The edge usage constraint is was not fulfilled:" + max_edge + "is bigger" +
                    "than c: " + slrlInstance.getC());
        }



    }

    private Map<Node, ShortestPaths> createPathsMap(Graph graph) {
        Map<Node, ShortestPaths> pathsMap = new HashMap<Node, ShortestPaths>();
        List<Node> nodeList = graph.getGraph();
        for(Node n : nodeList){
            pathsMap.put(n,new ShortestPaths(n,graph));
        }
        return pathsMap;  //To change body of created methods use File | Settings | File Templates.
    }

}
