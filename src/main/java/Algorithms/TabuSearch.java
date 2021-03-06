package Algorithms;

import Graph.Node;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;
import Graph.Graph;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.*;

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
    private Set<Solution> tabuList = new LinkedHashSet<Solution>();
    private Set<Node> tabuList_s = new LinkedHashSet<Node>();


    /**
     * Removes oldest element of the tabuList, which is a LinkedHashSet
     */
    private void  removeOldest(){
       Iterator<Solution> iterator = tabuList.iterator();
       iterator.next();
       iterator.remove();
    }
    public SLRLInstance tabu_search(SLRLInstance instance){

        //cannot be optimized further in regard to R
        if(instance.getR() == 1 && instance.getcLast() <= instance.getC())
            return instance;

        Solution currentSol;
        Solution neighbourSol;
        Graph g = instance.getGraph();
        int t_L=80;
        Solution bestSolution = currentSol = new Solution(instance.getGraph(), instance.getR(), instance.getcLast(), instance.getSolved());
        tabuList.add(currentSol);

        for(int i = 0; i<1000; i++){
            //search the best out of N(currentSol)
            neighbourSol = local_search_withTabuList(currentSol, g, instance.getC());
            //add the best neighbourSol to the tabulist
            tabuList.add(neighbourSol);
            //delete elements of tabulist which are older than t_L iterations
            if(tabuList.size()>=t_L){
                removeOldest();
            }
            currentSol = neighbourSol;

            //if better exchange
            if(currentSol.getLastC()<=instance.getC() && currentSol.getR() <= bestSolution.getR() ){
                bestSolution.setSolved(true);
                bestSolution = currentSol;

                //if the solution is the best possible solution, stop.
                if(currentSol.getR()==instance.getR_lower())
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

    private Solution local_search_withTabuList(Solution initialSolution,Graph g, int c){
        Solution bestSolution =  new Solution(initialSolution, Integer.MAX_VALUE, Integer.MAX_VALUE,false);
        Solution currentSolution = new Solution(initialSolution, initialSolution.getR(), initialSolution.getLastC(),
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

    private void  removeOldest_s(){
        Iterator<Node> iterator = tabuList_s.iterator();
        iterator.next();
        iterator.remove();
    }

    /**
     * Tabu search with a tabu list only saving servers as structure
     * @param instance
     * @return
     */
    public SLRLInstance tabu_search_s(SLRLInstance instance){

        //cannot be optimized further in regard to R
        if(instance.getR() == 1 && instance.getcLast() <= instance.getC())
            return instance;

        Solution currentSol;
        Solution neighbourSol;
        Graph g = instance.getGraph();
        int t_L=instance.getK();
        Solution bestSolution = currentSol = new Solution(instance.getGraph(), instance.getR(), instance.getcLast(), instance.getSolved());

        //add one server
        tabuList_s.add(g.getServers().get(0));

        for(int i = 0; i<3000; i++){
            //search the best out of N(currentSol)
            neighbourSol = local_search_withTabuList_s(currentSol, g, instance.getC());
            //add the best neighbourSol to the tabulist
            //tabuList_s.add(neighbourSol);
            //delete elements of tabulist which are older than t_L iterations
            if(tabuList_s.size()>=t_L){
                removeOldest_s();
            }
            currentSol = neighbourSol;

            //if better exchange
            if(currentSol.getLastC()<=instance.getC() && currentSol.getR() <= bestSolution.getR() ){
                bestSolution.setSolved(true);
                bestSolution = currentSol;

                //if the solution is the best possible solution, stop.
                if(currentSol.getR()==instance.getR_lower())
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

    private Solution local_search_withTabuList_s(Solution initialSolution,Graph g, int c){
        Solution bestSolution =  new Solution(initialSolution, Integer.MAX_VALUE, Integer.MAX_VALUE,false);
        Solution currentSolution = new Solution(initialSolution, initialSolution.getR(), initialSolution.getLastC(),
                initialSolution.isSolved());
        Node move = null;
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
                            && !tabuList_s.contains(otherNode)){
                        bestSolution = new Solution(g,max_neighbourset,max_usage,true);
                        move = otherNode;
                    }
                    if(!bestSolution.isSolved() && bestSolution.getLastC() > max_usage && !tabuList_s.contains(otherNode))
                    {
                        bestSolution = new Solution(g,max_neighbourset,max_usage, max_usage<=c);
                        move = otherNode;
                    }
                    g.removeServer(otherNode);
                }
            }
            g.addServer(server);
        }
        tabuList_s.add(move);

        return  bestSolution;
    }

}
