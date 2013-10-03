import Algorithms.GreedyLocation;
import Algorithms.TabuSearch;
import Graph.NodeNotFoundException;
import Parsing.ParseTestInstances;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/13/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Application {
    public static void main (String args[]) throws NodeNotFoundException {
        final Logger log = Logger.getLogger(Application.class);
        //ParserAlternative parser = new ParserAlternative();
        //parser.parse();
        ParseTestInstances parseTestInstances = new ParseTestInstances();
        List<SLRLInstance> instanceList = parseTestInstances.parse();//parser.parse();
        log.debug("Instance Size: "+instanceList.size());
        GreedyLocation greedyLocation = new GreedyLocation() ;
        //LocalSearch localSearch = new LocalSearch();
        TabuSearch tabuSearch = new TabuSearch();

        for(SLRLInstance slrlInstance: instanceList){
            if(!slrlInstance.getTestInstanceName().equals("UUNET")) {
            log.debug("------------------------------");
            long start = System.currentTimeMillis();
            greedyLocation.solve(slrlInstance);
            long end = System.currentTimeMillis();
            log.debug ("Greedy location costed "+ (end-start) + "ms");
            log.debug(slrlInstance.toString());

            start = System.currentTimeMillis();
            tabuSearch.tabu_search(slrlInstance);
            end = System.currentTimeMillis();
            log.debug ("tabu search costed "+ (end-start) + "ms");
            log.debug(slrlInstance.toString());
           }
        }
    }

}
