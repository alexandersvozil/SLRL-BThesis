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
        //number of servers allowed
        int k = instance.getK();//(int) Math.ceil( instance.getK());
        List<Node> servers = new ArrayList<Node>();
        // as long as there are servers left to be set, set a server
        while (servers.size() < k) {
            int c =(int) Math.ceil((double)instance.getV() /(double) ((servers.size()+1) * instance.getMaxDegree()) * 4);
            instance.setC(c);

            int maxEdgeUsage = Integer.MAX_VALUE;
            int maxEdgeUsageNeighbourSet = Integer.MAX_VALUE;
            Node bestNodeEdgeUsage = null;

            int maxNeighbourSet = Integer.MAX_VALUE;
            int maxNeighbourSetEdgeUsage = Integer.MAX_VALUE;
            Node bestNodeMinumNeighbourSet = null;

            //try each Node as Server
            for (Node node : graph.getGraph()) {
                if (node.isServer())
                    continue;
                servers.add(node);
                node.setServer(true);
                graph.addServer(node);

                //this is the method that calculates all the stuff after adding a server. m(e) for each Edge and the Neighbourhood of the Server
                graph.calculateUsagesAndNeighbourhoodsAfterServerAddition();

                int newMaxEdgeUsage = graph.getMaxUsage();
                int newMaxNeighbourSet = graph.getMaxNeighbourSet();
                node.setMaxEdgeUsage(newMaxEdgeUsage);
                node.setLabel("NH: "+newMaxNeighbourSet+ " m(e): " + newMaxEdgeUsage+" " );

                /*
                two possibilities now:
                 1.either is it a new minimum for the max m(e) € E
                 2.it is a new minimum for maximum size of the neighbour sets
                */

                /*Possibility 1:
                    new minimum for the max m(e) € E
                 */
                if(( (maxEdgeUsage > newMaxEdgeUsage)) || (maxEdgeUsage >= newMaxEdgeUsage &&  maxEdgeUsageNeighbourSet > newMaxNeighbourSet)) {
                    //log.debug("found new  minimal max edge usage "+ newMaxEdgeUsage + " old " + maxEdgeUsage+ "node: " + node);
                    maxEdgeUsage = newMaxEdgeUsage;
                    bestNodeEdgeUsage = node;
                    maxEdgeUsageNeighbourSet = newMaxNeighbourSet;
                    maxNeighbourSet = newMaxNeighbourSet;

                }
                /*Possibility 2:
                    new minimum for maximum size of the neighbour sets under the constraint that m(e) <= c
                 */
                if ((maxNeighbourSet > newMaxNeighbourSet  && newMaxEdgeUsage <= c)) {
                    bestNodeMinumNeighbourSet = node;
                    //log.debug("new best neighbourset: "+ newMaxNeighbourSet);
                    maxNeighbourSet = newMaxNeighbourSet;
                }


                // As we have to try every node, reset usages of the edges, and size of neighbhouring sets
                graph.clearUsages();

                //remove the node to try new one
                servers.remove(node);
                node.setServer(false);
                instance.getGraph().removeServer(node);
            }

            /* After we tried every node choose the node with the lowest max neighbour set. */

            //if there was none under the constraint m(e) < c, we choose the node with minimum maxEdge usage
            if (bestNodeMinumNeighbourSet == null ) {
                log.debug("C is not yet satisfied with the minimum neighbour set node, upper Bound c:"+ c+" with the lowest neighbourhood usage: " +  maxEdgeUsageNeighbourSet+ " hence" +
                       " I will choose this node with max usage: "+ bestNodeEdgeUsage.getMaxEdgeUsage());
                servers.add(bestNodeEdgeUsage);
                bestNodeEdgeUsage.setServer(true);
                graph.addServer(bestNodeEdgeUsage);
                if(bestNodeEdgeUsage.getMaxEdgeUsage() <= c){
                    solved = true;
                }else{
                    solved = false;
                }
            } else {
                //else choose the node with minimum maxneighbour set
                servers.add(bestNodeMinumNeighbourSet);
                bestNodeMinumNeighbourSet.setServer(true);
                graph.addServer(bestNodeMinumNeighbourSet);
               log.debug("C is satisfied "+bestNodeMinumNeighbourSet.getMaxEdgeUsage()+" thus taking node with lowest neighbhourhood  "+ maxNeighbourSet );
                solved=true;
            }
            //if (bestNodeMinumNeighbourSet == null)
             //   maxNeighbourSet = maxEdgeUsageNeighbourSet;
            instance.setR(maxNeighbourSet + 1);
            instance.setRatio_r((double) (maxNeighbourSet + 1) / (double) instance.getR_lower());
            //log.debug("max neighbour set:" + (1 + maxNeighbourSet) + " R_lower: " + instance.getR_lower());

        }
        if(!solved)
            log.debug("-----------  Greedy Location didn't solve the instance -----------");
        //make the screenshot
        instance.snapshotG();

        return instance;
    }


}
                      /* for (Node secondNode : instance.getGraph().getGraph()) {

                    //if the node is a server, skip it
                    if (servers.contains(secondNode)){
                        continue;
                    }

                    //list of nearest Servers and the Steps to it.

                    List<Node> minServer = new ArrayList<Node>();
                    int minStepsToServer = 0;

                    //look for the nearest server
                    for (Node server : servers) {
                        Node p = null;
                        try {
                            p = secondNode.BFS(server);
                        } catch (NodeNotFoundException e) {
                            e.printStackTrace(); //BFS could not find the server
                            return instance;
                        }
                        int stepCounter = 0;
                        while (p != null) {
                            stepCounter++;
                            p = p.getParent();
                        }
                        instance.getGraph().clearParents();

                        if (stepCounter <= minStepsToServer || minServer.isEmpty()) {
                            //new nearest Server found
                            if(stepCounter < minStepsToServer){
                                minServer.clear();
                            }
                            minStepsToServer = stepCounter;
                            minServer.add(server);

                        }
                    }

                    //increase r of the Server
                    for (Node n : minServer) {
                        n.increaseNeighbourhood();

                        //increase the m(e) of the edges used
                        Node p = null;
                        try {
                            p = secondNode.BFS(n);
                        } catch (NodeNotFoundException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        secondNode.markPath(p);
                        instance.getGraph().clearParents();
                    }

                }*/