package Algorithms;

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
    public SLRLInstance solve (SLRLInstance instance){
        //number of connections passing one link
        int c = instance.getC();
        //number of servers allowed
        int k = instance.getK();
        List<ParseNode> servers = new ArrayList<ParseNode>();
        while(servers.size() < k) {

        /*choose vertex v such that the maximum size of the neighbour sets of S U {v} is the minimum for all vertices v € V.
        under the constraint that m(e) <= c for all edges e.
        if there is no vertex satisfying this constraint, choose v under the constraint that max_e€Em(e) is the minium.
         */
        }

        return instance;
    }
}
