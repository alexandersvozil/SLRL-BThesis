package Algorithms;

import Graph.Node;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;
import Graph.Graph;
import org.omg.PortableInterceptor.INACTIVE;

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
        Graph g = instance.getGraph();
        int t_L=180;
//        g.createPathsMap();



        Solution bestSolution = currentSol = new Solution(instance.getGraph(), instance.getR(), instance.getcLast(), instance.getSolved());
        tabuList.add(currentSol);
        System.out.println(instance);

        for(int i = 0; i<1000; i++){
            //search the best out of N(currentSol)
            neighbourSol = local_search_withTabuList(currentSol, g, instance.getK(), instance.getC());
            //add the best neighbourSol to the tabulist
            tabuList.add(neighbourSol);
            //delete elements of tabulist which are older than t_L iterations
            if(tabuList.size()==t_L){
                tabuList.remove(0);
            }
            currentSol = neighbourSol;
            //if better exchange
            if(currentSol.getLastC()<=instance.getC() && currentSol.getR() < bestSolution.getR() ){
                //log.debug("TABU^^^^^^^^^Found better solution"+  " new r: "+ currentSol.getR()+" old r: " +bestSolution.getR()+ " new max usage: " + currentSol.getLastC());
                bestSolution.setSolved(true);
                bestSolution = currentSol;
               /* String servers = "";
                for(Node n : bestSolution.getServers()){
                    servers +=" " + n .getName();
                }
               // log.debug(servers); */

                if(currentSol.getR()==1)
                    break;
               // log.debug(servers);
            }

            if(!bestSolution.isSolved() && bestSolution.getLastC() > currentSol.getLastC() )
            {
                 // log.info("TABU^^^^^^^^^Found better solution in terms of c"+  " new r: "+ currentSol.getR()+" old r: " +bestSolution.getR()+ "" +
                 //       "old max usage: "+ bestSolution.getLastC()+ " new max usage: " + currentSol.getLastC());
                currentSol.setSolved(currentSol.getLastC() <= instance.getC());
                bestSolution = currentSol;
            }
            //  log.info("TABUDEBUG"+  " new r: "+ currentSol.getR()+" old r: " +bestSolution.getR()+ "" +
            //        "old max usage: "+ bestSolution.getLastC()+ " new max usage: " + currentSol.getLastC());

        }


        instance.setSolution(bestSolution);

        /* Snapshot */
        //instance.snapshotL();
        return instance;
    }

    private Solution local_search_withTabuList(Solution initialSolution,Graph g,int k ,int c){
        Solution bestSolution =  new Solution(initialSolution, Integer.MAX_VALUE, Integer.MAX_VALUE,false);
        Solution currentSolution =new Solution(initialSolution, initialSolution.getR(), initialSolution.getLastC(),
                initialSolution.isSolved());

        g.getServers().clear();
        for(Node n : currentSolution.getServers()){
            g.getServers().add(n);
        }

         for(Node server : g.getServers()){
            g.removeServer(server);
            for(Node otherNode: g.getGraph()){
                if(!otherNode.equals(server) && !g.getServers().contains(otherNode)){
                    g.clearUsages();
                    g.addServer(otherNode);
                    g.updateConstraints();

                    int max_usage = g.getMaxUsage();
                    int max_neighbourset = g.getMaxNeighbourSet();

                    if(max_usage<=c
                      && ((double)(max_neighbourset)) < bestSolution.getR()
                      && !tabuList.contains(g.getServers())){


                     //log.debug("^^^^^^^^^Found better solution"+  " new r: "+ newr+" old r: " +currentSolution.getR()+ " new Max usage: " + max_usage);
                        bestSolution = new Solution(g,max_neighbourset,max_usage,true);

                        // String servers = "";
                        // for(Node n : currentSolution.getGraph().getServers()){
                        //    servers +=" " + n .getName();
                        // }
                        // log.debug("Local search returning"+ bestSolution.getR() + " " + bestSolution.getLastC()+ " servers: "+ servers+ " neighbourset: "+ max_neighbourset);
                    }

                    if(!bestSolution.isSolved() && bestSolution.getLastC() > max_usage && !tabuList.contains(g.getServers()))
                    {
                        //log.debug("^^^^^^^^^Found better solution in terms of c"+  " new r: "+ newr+" old r: " +currentSolution.getR()+ " new Max usage: " + max_usage);
                        bestSolution = new Solution(g,max_neighbourset,max_usage, max_usage<=c);
                    }
                    g.removeServer(otherNode);
                }
            }
             g.addServer(server);
        }

        return  bestSolution;
    }
}
