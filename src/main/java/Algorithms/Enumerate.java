package Algorithms;

import Graph.Graph;
import Parsing.SLRLInstance;
import Graph.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by svozil on 3/7/14.
 */
public class Enumerate {

    SLRLInstance slrlInstance;

    Graph g;
    Solution best = null;
    public SLRLInstance start (SLRLInstance slrlInstance){
        this.slrlInstance = slrlInstance;
        slrlInstance.getGraph().createPathsMap();
        Solution s = new Solution();
        g =  slrlInstance.getGraph();
        accumulate(s);

        slrlInstance.setSolution(best);


        return slrlInstance;
    }

    private void accumulate(Solution sol) {
        if(sol.getServers().size() == slrlInstance.getK())
        {
            g.clearUsages();
            List<Node> servers = new ArrayList<Node>(sol.getServers());
            g.setServers(servers);
            g.updateConstraints();
            sol.setR(g.getMaxNeighbourSet());
            sol.setLastC(g.getMaxUsage());

            //System.out.println(sol);
            if(best == null || slrlInstance.getC() > sol.getLastC() && best.getR() > sol.getR() ){
                best = sol;
            }
            return;
        }
        Solution adds = new Solution(sol);
        Solution dAddS =  new Solution(sol);

        if(addNewServer(adds)){
            accumulate(adds);
        }

        if(dontaddNewServer(dAddS)){
            accumulate(dAddS);
        }


    }

    private boolean addNewServer(Solution adds) {
        if(adds.getServers().size() < slrlInstance.getK()){
            Node nextNode = nextNode(adds.getLastNode());
            if(nextNode==null){
                return false;
            }
            adds.setLastNode(nextNode);
            adds.getServers().add(nextNode);
            return true;
        }
        return  false;
    }

    private Node nextNode(Node node) {
        if(node != null){
            int n = slrlInstance.getGraph().getGraph().indexOf(node)+1;

            if(n >= slrlInstance.getGraph().getGraph().size()){
               return null;
            }

            return slrlInstance.getGraph().getGraph().get(n);
        }else{
            Node n = slrlInstance.getGraph().getGraph().get(1);
            return n;
        }

    }

    private boolean dontaddNewServer(Solution adds) {
        if(adds.getServers().size() < slrlInstance.getK()){
            Node nextNode = nextNode(adds.getLastNode());
            if(nextNode==null){
                return false;
            }
            adds.setLastNode(nextNode);
            return true;
        }
        else
        {
            return false;
        }
    }

}
