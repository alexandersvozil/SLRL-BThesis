package Algorithms;

import Graph.Node;
import Graph.Graph;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;


/**
 * Created by alexander on 2/24/14.
 */
public class GreedyDegree {
    private static Logger log = Logger.getLogger(GreedyDegree.class);

    /**
     * @param instance the SLRLInstance to be solved
     *                 choose vertex v such that the maximum size of the neighbour sets of S U {v} is the minimum for all vertices v € V.
     *                 under the constraint that m(e) <= c for all edges e.
     *                 if there is no vertex satisfying this constraint, choose v under the constraint that max_e€Em(e) is the minium.
     */
    public SLRLInstance solve(SLRLInstance instance) {
        //number of max connections passing one link
        Graph graph = instance.getGraph();
        graph.createPathsMap();
        //number of servers allowed
        int k = instance.getK();
        // as long as there are servers left to be set, set a server
        while (graph.getServerSize() < k) {
            int c =(int) Math.ceil((double)instance.getV() /(double) ((graph.getServerSize()+1) * instance.getMaxDegree()) * 4);
            instance.setC(c);

            int mostedges = 0;
            Node mostEdgesNode = null;
            for(Node n : graph.getGraph()){
                int degree = n.getEdges().size();
                if(mostedges < degree && !graph.getServers().contains(n)){
                    mostEdgesNode = n;
                    mostedges = degree;
                }
            }

            graph.addServer(mostEdgesNode);
        }
       /* if(!solved){
            log.debug("-----------  Greedy Location didn't solve the instance -----------");
        }*/
        graph.updateConstraints();
        instance.setcLast(graph.getMaxUsage());
        instance.setR(graph.getMaxNeighbourSet());
        instance.setSolved(instance.getC() >= graph.getMaxUsage());
        instance.setRatio_r((double)graph.getMaxNeighbourSet() / (double)instance.getR_lower());

        // make a screenshot
        // instance.snapshotG();

        return instance;
    }
}
