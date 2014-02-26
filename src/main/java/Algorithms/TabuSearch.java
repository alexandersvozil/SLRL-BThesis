package Algorithms;

import Graph.Node;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;
import Graph.Graph;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Solution> tabuList = new HashSet<Solution>();

    public SLRLInstance tabu_search(SLRLInstance instance){

        //cannot be optimized further in regard to R
        if(instance.getR() == 1 && instance.getcLast() <= instance.getC())
            return instance;

        Solution currentSol;
        Solution neighbourSol;
        Graph g = instance.getGraph();
        int t_L=580;
        Solution bestSolution = currentSol = new Solution(instance.getGraph(), instance.getR(), instance.getcLast(), instance.getSolved());
        tabuList.add(currentSol);

        for(int i = 0; i<4000; i++){
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
                bestSolution.setSolved(true);
                bestSolution = currentSol;

                //if the solution is the best possible solution, stop.
                if(currentSol.getR()==1)
                    break;
            }

            if(!bestSolution.isSolved() && bestSolution.getLastC() > currentSol.getLastC() )
            {
                currentSol.setSolved(currentSol.getLastC() <= instance.getC());
                bestSolution = currentSol;
            }

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
                        bestSolution = new Solution(g,max_neighbourset,max_usage,true);
                    }
                    if(!bestSolution.isSolved() && bestSolution.getLastC() > max_usage && !tabuList.contains(g.getServers()))
                    {
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
