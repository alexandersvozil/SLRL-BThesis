package Algorithms;

import Graph.Node;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * User: svozil
 * Date: 9/16/13
 * Time: 4:19 PM
 */
public class TabuSearch {
    /**
     * Pseudocode from Algorithem und Datenstrukturen 2 Skriptum [Univ.-Prof. Günther Raidl und Univ.-Ass. Bin Hu im SS2013] S.39
     * Metaheuristic introduced by Glover 1986
     * @param instance
     * @return instance after tabu search
     */
    private static Logger log = Logger.getLogger(TabuSearch.class);
    private List<Solution> tabuList = new ArrayList<Solution>();

    public SLRLInstance tabu_search(SLRLInstance instance){

        Solution currentSol;
        Solution neighbourSol;
        int t_L=5;


        Solution bestSolution = currentSol = new Solution(instance.getGraph(),instance.getK(),instance.getC(), instance.getRatio_r(), instance.getcLast(), instance.getSolved(), instance.getR_lower());
        tabuList.add(currentSol);

        for(int i = 0; i<50; i++){
            //search the best out of N(currentSol)
            long start = System.currentTimeMillis();
            neighbourSol = local_search_withTabuList(currentSol);
            //add the best neighbourSol to the tabulist
            tabuList.add(neighbourSol);
            //delete elements of tabulist which are older than t_L iterations
            if(tabuList.size()==t_L){
                tabuList.remove(0);
            }
            currentSol = neighbourSol;

            //if better exchange
            if(currentSol.getLastC()<=bestSolution.getC() && currentSol.getR() < bestSolution.getR() ){
                log.debug("TABU^^^^^^^^^Found better solution"+  " new r: "+ currentSol.getR()+" old r: " +bestSolution.getR()+ " new max usage: " + currentSol.getLastC());
                bestSolution.setSolved(true);
                bestSolution = currentSol;
                String servers = "";
                for(Node n : bestSolution.getGraph().getServers()){
                    servers +=" " + n .getName();
                }
                log.debug(servers);
            }

            if(!bestSolution.isSolved() && bestSolution.getLastC() > currentSol.getLastC() )
            {
                log.debug("TABU^^^^^^^^^Found better solution in terms of c"+  " new r: "+ currentSol.getR()+" old r: " +bestSolution.getR()+ "" +
                        "old max usage: "+ bestSolution.getLastC()+ " new max usage: " + currentSol.getLastC());
                currentSol.setSolved(currentSol.getLastC() <= currentSol.getC());
                bestSolution = currentSol;
            }
           // log.debug("TABUDEBUG"+  " new r: "+ currentSol.getR()+" old r: " +bestSolution.getR()+ "" +
            //        "old max usage: "+ bestSolution.getLastC()+ " new max usage: " + currentSol.getLastC());

            long end = System.currentTimeMillis();
            log.debug("--Execution time was "+(end-start)+" ms.");
        }


        instance.setSolution(bestSolution);
        instance.snapshotL();
        return instance;
    }
    private Solution local_search_withTabuList(Solution initialSolution){
        //log.debug("-------------------NEW LOCAL SEARCH INDUCED BY TABU -------------");

        int nrOfServers = initialSolution.getK();
        Solution bestSolution =  new Solution(initialSolution.getGraph(),initialSolution.getK(),initialSolution.getC(), Double.MAX_VALUE, Integer.MAX_VALUE,false
                , initialSolution.getR_lower());
        Solution currentSolution =new Solution(initialSolution.getGraph(),initialSolution.getK(),initialSolution.getC(), initialSolution.getR(),
                initialSolution.getLastC(),initialSolution.isSolved(), initialSolution.getR_lower());


        for(Node server : currentSolution.getGraph().getServers()){
            currentSolution.getGraph().removeServer(server);
            for(Node otherNode: currentSolution.getGraph().getGraph()){
                if(!otherNode.equals(server) && !currentSolution.getGraph().getServers().contains(otherNode)){
                    currentSolution.getGraph().clearUsages();
                    currentSolution.getGraph().addServer(otherNode);
                    currentSolution.getGraph().calculateUsagesAndNeighbourhoodsAfterServerAddition();

                    int max_usage = currentSolution.getGraph().getMaxUsage();
                    int max_neighbourset = currentSolution.getGraph().getMaxNeighbourSet();


                    //if better exchange
                   // log.debug(max_usage +" "+ max_neighbourset+ " " + currentSolution.getGraph().size());


                    if(max_usage<=bestSolution.getC() && ((double)(max_neighbourset+1)/bestSolution.getR_lower()) < bestSolution.getR() && !tabuList.contains(currentSolution)){
                        double newr =((double)(max_neighbourset+1)/(double)bestSolution.getR_lower());
                        int c = currentSolution.getC();

                   //  log.debug("^^^^^^^^^Found better solution"+  " new r: "+ newr+" old r: " +currentSolution.getR()+ " new Max usage: " + max_usage);
                        bestSolution = new Solution(currentSolution.getGraph(),currentSolution.getK(),c,newr,max_usage,true,bestSolution.getR_lower());
                        String servers = "";
                       // for(Node n : currentSolution.getGraph().getServers()){
                        //    servers +=" " + n .getName();
                       // }
                       // log.debug("Local search returning"+ bestSolution.getR() + " " + bestSolution.getLastC()+ " servers: "+ servers+ " neighbourset: "+ max_neighbourset);
                    }

                    if(!bestSolution.isSolved() && bestSolution.getLastC() > max_usage && !tabuList.contains(currentSolution))
                    {
                        double newr =((double)(max_neighbourset+1)/(double)bestSolution.getR_lower());
                        int c = currentSolution.getC();
                    //    log.debug("^^^^^^^^^Found better solution in terms of c"+  " new r: "+ newr+" old r: " +currentSolution.getR()+ " new Max usage: " + max_usage);
                        bestSolution = new Solution(currentSolution.getGraph(),currentSolution.getK(),c,newr,max_usage, max_usage<=c, bestSolution.getR_lower());
                    }
                    currentSolution.getGraph().removeServer(otherNode);
                }
            }
            currentSolution.getGraph().addServer(server);
        }

        return  bestSolution;
    }
}
