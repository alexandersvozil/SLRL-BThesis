package Algorithms;

import Graph.Graph;
import Algorithms.Solution;
import Graph.Node;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 9/12/13
 * Time: 8:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class LocalSearch {
    private static Logger log = Logger.getLogger(LocalSearch.class);

    /**
     * This search changes one server of the solution in hope to get a better solution.
     * It is a best improvement search (BI).
     *
     * @param instance
     * @return
     */
    public SLRLInstance localSearchBI(SLRLInstance instance){
        Solution initialSolution = new Solution(instance.getGraph(),instance.getK(),instance.getC(), instance.getRatio_r(), instance.getcLast(), instance.getSolved(),instance.getR_lower());
        int nrOfServers = initialSolution.getK();
        Solution bestSolution = initialSolution;


        for(Node server : initialSolution.getGraph().getServers()){
            initialSolution.getGraph().removeServer(server);
            for(Node otherNode: initialSolution.getGraph().getGraph()){
             if(!otherNode.equals(server) && !initialSolution.getGraph().getServers().contains(otherNode)){
             initialSolution.getGraph().clearUsages();
             initialSolution.getGraph().addServer(otherNode);
             initialSolution.getGraph().calculateUsagesAndNeighbourhoodsAfterServerAddition();

                int max_usage = initialSolution.getGraph().getMaxUsage();
                int max_neighbourset = initialSolution.getGraph().getMaxNeighbourSet();


                 //if better exchange
                if(max_usage<=bestSolution.getC() && ((double)(max_neighbourset+1)/instance.getR_lower()) < bestSolution.getR()){
                    double newr =((double)(max_neighbourset+1)/(double)instance.getR_lower());
                    int c = initialSolution.getC();

                    log.debug("^^^^^^^^^Found better (solved)solution"+  " new k: "+ newr+" old k: " +initialSolution.getR()+ " new Max usage: " + max_usage);
                    bestSolution = new Solution(initialSolution.getGraph(),initialSolution.getK(),c,newr,max_usage,true,bestSolution.getR_lower());
                }

                if(!bestSolution.isSolved() && bestSolution.getLastC() > max_usage)
                {
                    double newr =((double)(max_neighbourset+1)/(double)instance.getR_lower());
                    int c = initialSolution.getC();

                    log.debug("^^^^^^^^^Found better solution in terms of c"+  " new k: "+ newr+" old k: " +initialSolution.getR()+ " new Max usage: " + max_usage);
                    bestSolution = new Solution(initialSolution.getGraph(),initialSolution.getK(),c,newr,max_usage, max_usage <= c,bestSolution.getR_lower());


                }

                //for debugging purpose
                /* else {
                    double newk =((double)(max_neighbourset+1)/(double)instance.getR_lower());
                    int c = initialSolution.getC();
                    int newMaxUsage = initialSolution.getGraph().getMaxUsage();
                }*/
                initialSolution.getGraph().removeServer(otherNode);
             }
            }
            initialSolution.getGraph().addServer(server);
        }
        instance.setSolution(bestSolution);
        instance.snapshotL();
      return instance;
    }




}
