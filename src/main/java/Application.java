import Algorithms.*;
import Correctness.CorrectnessTester;
import Correctness.SolutionWrongException;
import Graph.NodeNotFoundException;
import Parsing.ParseTestInstances;
import Parsing.SLRLInstance;
import org.apache.log4j.Logger;

import java.util.ArrayList;
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
        GreedyDegree greedyDegree = new GreedyDegree();
        //LocalSearch localSearch = new LocalSearch();
        TabuSearch tabuSearch = new TabuSearch();
        int solvedcounter =  0;
        Enumerate enumerate = new Enumerate();

        //to output barchart and the tabella of that algorithm
        List<String> tabella = new ArrayList<String>();
        List<String> barchart= new ArrayList<String>();
        for(SLRLInstance slrlInstance: instanceList){
            // log.debug("------------------------------");
            //enumerate.start(slrlInstance);
            long start = System.currentTimeMillis();
            //greedyDegree.solve(slrlInstance);
            greedyLocation.solve(slrlInstance);
            long end = System.currentTimeMillis();
            //log.debug ("Greedy location "+ (end-start) + "ms");
            //log.debug(slrlInstance.toString());

            start = System.currentTimeMillis();
           //tabuSearch.tabu_search(slrlInstance);
           // tabuSearch.tabu_search_s(slrlInstance);
           //SimulatedAnnealing sim = new SimulatedAnnealing(slrlInstance);
           //sim.calculate();
            end = System.currentTimeMillis();
            //log.debug ("tabu search costed "+ (end-start) + "ms");
            //log.debug(slrlInstance.toString());

            if(slrlInstance.getSolved()){
            barchart.add(slrlInstance.barChart());
            }
            tabella.add(slrlInstance.tabella());

            if(slrlInstance.getSolved()){
                solvedcounter++;
            }
            //log.debug("correctness check:");
            CorrectnessTester k = new CorrectnessTester();
            try {
                k.testCorrectness(slrlInstance);
            } catch (SolutionWrongException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                log.error("solution incorrect");
            }
        }
        for(String n : barchart){
            System.out.println(n);
        }
        for(String n : tabella){
            System.out.println(n);
        }
        log.debug("Number of Instances: " + instanceList.size() + ", Solved:" + solvedcounter);
    }

}
