import Algorithms.GreedyLocation;
import Algorithms.LocalSearch;
import Algorithms.TabuSearch;
import Graph.Node;
import Graph.NodeNotFoundException;
import Parsing.ParseTestInstances;
import Parsing.SLRLInstance;
import ParsingData.Parser;
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
        //Parser parser = new Parser();
        //parser.parse();
        ParseTestInstances parseTestInstances = new ParseTestInstances();
        List<SLRLInstance> instanceList = parseTestInstances.parse();//parser.parse();
        log.debug("Instance Size: "+instanceList.size());
        GreedyLocation greedyLocation = new GreedyLocation() ;
        //LocalSearch localSearch = new LocalSearch();
        TabuSearch tabuSearch = new TabuSearch();

        long start = System.currentTimeMillis();
        for(SLRLInstance slrlInstance: instanceList){
            //if(slrlInstance.getTestInstanceName().equals("UUNET")) {
            log.debug("------------------------------");
            greedyLocation.solve(slrlInstance);
            log.debug(slrlInstance.toString());

            for(Node n : slrlInstance.getGraph().getServers()){
                log.debug(n.getName());
            }
            //tabuSearch.tabu_search(slrlInstance);

            //localSearch.localSearchBI(slrlInstance);
            log.debug(slrlInstance.toString());
           //}
        }
        long end = System.currentTimeMillis();

        log.debug("Execution time was "+(end-start)+" ms.");

    }
}
