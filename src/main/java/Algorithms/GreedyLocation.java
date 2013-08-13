package Algorithms;

import Graph.Edge;
import Graph.Node;
import Graph.NodeNotFoundException;
import Parsing.ParseNode;
import Parsing.SLRLInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/3/13
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class GreedyLocation {
    /**
     * @param instance the SLRLInstance to be solved
     * choose vertex v such that the maximum size of the neighbour sets of S U {v} is the minimum for all vertices v € V.
     *under the constraint that m(e) <= c for all edges e.
     *if there is no vertex satisfying this constraint, choose v under the constraint that max_e€Em(e) is the minium.
     **/
    public SLRLInstance solve (SLRLInstance instance)  {
        //number of connections passing one link
        int c = instance.getC();
        //number of servers allowed
        int k = instance.getK();
        List<Node> servers = new ArrayList<Node>();
        // as long as there are servers left to be set, set a server
        while(servers.size() < k) {

            int maxEdgeUsage = 0;
            Node bestNodeEdgeUsage = null;
            int maxNeighbourSet = 0;
            Node bestNodeMinumNeighbourSet = null;
            for(Node node : instance.getGraph().getGraph()){
                //m(e) <= c, try each Node as Server
                node.setServer(true);
                servers.add(node);
                for(Node secondNode : instance.getGraph().getGraph()){
                    //if the node is a server, skip it
                    if(servers.contains(secondNode))
                        continue;

                    //nearest Server and the Steps to it.
                    Node minServer = null;
                    int minStepsToServer = 0;

                    //look for the nearest server
                   for(Node server : servers){
                       Node p = null;
                       try {
                           p = secondNode.BFS(server);
                       } catch (NodeNotFoundException e) {
                           e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                       }
                       int stepCounter = 0;
                       while (p != null){
                           stepCounter++;
                           p = p.getParent();
                       }
                       instance.getGraph().clearParents();

                       if(stepCounter < minStepsToServer || minServer == null){
                           //new nearest Server found
                           minStepsToServer = stepCounter;
                           minServer = server;

                       }
                   }
                    //increase r of the Server
                    minServer.increaseNeighbourhood();
                    //increase the m(e) of the edges used
                    Node p = null;
                    try {
                        p = secondNode.BFS(minServer);
                    } catch (NodeNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    secondNode.markPath(p);
                    instance.getGraph().clearParents();

                }

                int newMaxEdgeUsage = instance.getGraph().getMaxUsage();
                int newMaxNeighbourSet = instance.getGraph().getMaxNeighbourSet();
                node.setMaxEdgeUsage(newMaxEdgeUsage);
                if(maxEdgeUsage > newMaxEdgeUsage || bestNodeEdgeUsage == null){
                    maxEdgeUsage = newMaxEdgeUsage;
                    bestNodeEdgeUsage = node;
                    maxNeighbourSet = newMaxNeighbourSet;

                }

                //describes condition of greedy location. take the best server neighbourhood-wise as long as it satifies m(e) <= C
                //otherwhise take the one with lowest m(e)
                if(bestNodeMinumNeighbourSet == null ||( maxNeighbourSet > newMaxNeighbourSet && bestNodeMinumNeighbourSet.getMaxEdgeUsage() >= instance.getC()) ||
                        (maxNeighbourSet < newMaxNeighbourSet && bestNodeMinumNeighbourSet.getMaxEdgeUsage() > instance.getC() && node.getMaxEdgeUsage() <= instance.getC())  )
                {
                    maxNeighbourSet = newMaxNeighbourSet;
                    bestNodeMinumNeighbourSet = node;
                    //instance.setRatio_r((double) maxNeighbourSet/(double)instance.getR_lower());
                }
                // RESET usage and neighbour sets
                System.out.println(maxNeighbourSet);
                instance.getGraph().clearUsages();
            }
            if(instance.getC() < bestNodeMinumNeighbourSet.getMaxEdgeUsage() )
            {
                servers.add(bestNodeEdgeUsage);
            }
            else {
                servers.add(bestNodeMinumNeighbourSet);
            }
            instance.setRatio_r((double) maxNeighbourSet/(double)instance.getR_lower());

        }

        return instance;
    }


}
