package Algorithms;

import Graph.Edge;
import Graph.Node;
import Graph.Graph;
import Graph.NodeNotFoundException;
import Parsing.ParseNode;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;

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
    private static Logger log = Logger.getLogger(GreedyLocation.class);

    /**
     * @param instance the SLRLInstance to be solved
     *                 choose vertex v such that the maximum size of the neighbour sets of S U {v} is the minimum for all vertices v € V.
     *                 under the constraint that m(e) <= c for all edges e.
     *                 if there is no vertex satisfying this constraint, choose v under the constraint that max_e€Em(e) is the minium.
     */
    public SLRLInstance solve(SLRLInstance instance) {
        boolean solved = false;
        //number of max connections passing one link
        Graph graph = instance.getGraph();
        graph.createPathsMap();
        //number of servers allowed
        int k = instance.getK();
        // as long as there are servers left to be set, set a server
        int cLast = Integer.MAX_VALUE;
        while (graph.getServerSize() < k) {
            int c =(int) Math.ceil((double)instance.getV() /(double) ((graph.getServerSize()+1) * instance.getMaxDegree()) * 4);
            instance.setC(c);

            int maxEdgeUsage = Integer.MAX_VALUE;
            int maxEdgeUsageNeighbourSet = Integer.MAX_VALUE;
            Node bestNodeEdgeUsage = null;

            int maxNeighbourSet = Integer.MAX_VALUE;
            Node bestNodeMinumNeighbourSet = null;


            //try each Node as Server
            for (Node node : graph.getGraph()) {
                if (node.isServer())
                    continue;


                graph.addServer(node);

                //this is the method that calculates all the stuff after adding a server. m(e) for each Edge and the neighbourhood of the servers
                graph.updateConstraints();
                int newMaxEdgeUsage = graph.getMaxUsage();
                int newMaxNeighbourSet = graph.getMaxNeighbourSet();
              // System.out.println(node.getName() +  "  " + newMaxNeighbourSet + " " +graph.getMaxUsage());
                node.setMaxEdgeUsage(newMaxEdgeUsage);
                node.setLabel("NH: "+newMaxNeighbourSet+ " m(e): " + newMaxEdgeUsage+" " ); /*for printing the graph */

                /*
                Two possibilities :
                 1.either is it a new minimum for the max m(e) € E
                 2. or it is a new minimum for maximum size of the neighbour sets
                */

                /*Possibility 1:
                    new minimum for the max m(e) € E
                 */
                if(( (maxEdgeUsage > newMaxEdgeUsage)) || (maxEdgeUsage >= newMaxEdgeUsage &&  maxEdgeUsageNeighbourSet > newMaxNeighbourSet)) {
                    //log.debug("found new  minimal max edge usage "+ newMaxEdgeUsage + " old " + maxEdgeUsage+ "node: " + node);
                    maxEdgeUsage = newMaxEdgeUsage;
                    bestNodeEdgeUsage = node;
                    maxEdgeUsageNeighbourSet = newMaxNeighbourSet;
                    if ((maxNeighbourSet > newMaxNeighbourSet  && newMaxEdgeUsage <= c)) {
                        maxNeighbourSet = newMaxNeighbourSet;
                        bestNodeMinumNeighbourSet = node;
                    }


                }

                /*Possibility 2:
                    new minimum for maximum size of the neighbour sets under the constraint that m(e) <= c
                 */
                if ((maxNeighbourSet > newMaxNeighbourSet  && newMaxEdgeUsage <= c)) {
                    bestNodeMinumNeighbourSet = node;
                    maxNeighbourSet = newMaxNeighbourSet;
                   // log.debug("new bestnodeminimumneighbourset " + node);
                }
                // As we have to try every node, reset usages of the edges, and size of neighbhouring sets
                graph.clearUsages();
                //remove the node to try new one
                instance.getGraph().removeServer(node);
            }

            /* After we tried every node choose the node with the lowest max neighbour set. */

            if (bestNodeMinumNeighbourSet == null ) {
                //if there was none under the constraint m(e) < c, we choose the node with minimum maxEdge usage
                bestNodeEdgeUsage.setServer(true);
                graph.addServer(bestNodeEdgeUsage);
                solved = bestNodeEdgeUsage.getMaxEdgeUsage() <= c;
                cLast = bestNodeEdgeUsage.getMaxEdgeUsage();
            } else {
                //else choose the node with minimum maxneighbour set
                bestNodeMinumNeighbourSet.setServer(true);
                cLast=bestNodeMinumNeighbourSet.getMaxEdgeUsage();
                graph.addServer(bestNodeMinumNeighbourSet);
                solved=true;
            }

            instance.setR(maxNeighbourSet);
            instance.setRatio_r((double) (maxNeighbourSet) / (double) instance.getR_lower());
            //log.debug("max neighbour set:" + (1 + maxNeighbourSet) + " R_lower: " + instance.getR_lower());
        }
       /* if(!solved){
            log.debug("-----------  Greedy Location didn't solve the instance -----------");
        }*/

        instance.setSolved(solved);

       // make a screenshot
       // instance.snapshotG();
        instance.setcLast(cLast);

        return instance;
    }
}
