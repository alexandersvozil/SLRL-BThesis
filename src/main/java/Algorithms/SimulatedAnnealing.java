package Algorithms;

import Graph.Graph;
import Graph.Node;
import Parsing.SLRLInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by svozil on 2/22/14.
 */
public class SimulatedAnnealing {
    private SLRLInstance instance;
    private Random rand;


    public SimulatedAnnealing(SLRLInstance instance){
        this.instance = instance;
        rand = new Random();
    }
    public SLRLInstance calculate (){
        double t = 0.99;
        double temperature = 3.5;
        int runtimeS = 10;

        Solution solX = new Solution(instance.getGraph(), instance.getR(), instance.getcLast(), instance.getSolved());
        long startTime = System.currentTimeMillis()/1000;
        while(true){
            // for(int i = 0; i < 100000; i++){
            Solution solY = randomNeighbor(solX);
            if(compareSolutions(solX,solY)){
                solX = solY;
            }else{
                if(metropoliscriteria(solX,solY,temperature)){
                    solX = solY;
                }
            }
            //geometric cooling
            temperature = temperature*t;
            if(((System.currentTimeMillis()/1000)- startTime) > runtimeS){
                break;
            }
            if(solX.getR() == instance.getR_lower() && solX.getLastC() <= instance.getC()){
                break;
            }
        }
        instance.setSolution(solX);

        return instance;
    }

    private boolean metropoliscriteria(Solution solX, Solution solY, double temperature) {
        double random = rand.nextDouble();
        BigDecimal calc = new BigDecimal((1/(Math.exp((Math.abs(rating(solX) - rating(solY) )))/temperature)));
        // System.out.println((Math.abs(rating(solX) - rating(solY) ))/temperature);
        //       System.out.println(Math.exp((Math.abs(rating(solX) - rating(solY) ))/temperature));


        // System.out.println("random: "+ random + ", calc:" + calc+ " " + (random < calc.doubleValue()));
        return random < calc.doubleValue();
    }

    private double rating(Solution solution) {
        double rating = 0;

        //solution is valid
        if (solution.getLastC() < instance.getC()){
            rating += 1;
        }

        rating+=(double)instance.getC() / (double)solution.getLastC();
        // System.out.println((double) instance.getR_lower()/(double)solution.getR());
        rating += ((double) instance.getR_lower()/(double)solution.getR());
//          System.out.println(rating);
        return rating;
    }

    /**
     * returns true if the second solution is better than the first one
     * @param solX the first solution
     * @param solY the second solution
     * @return
     */
    private boolean compareSolutions(Solution solX, Solution solY) {

        if(solX.getLastC() <= instance.getC() && solY.getLastC() <= instance.getC()){ /*both solutions fulfill c */
            if(solX.getR() < solY.getR()){
                return false;
            }
            else {
                return true;
            }
        }
        else if(solX.getLastC() <= instance.getC() && !(solY.getLastC() <= instance.getC())){
            return false;
        }
        else if(!(solX.getLastC() <= instance.getC()) && solY.getLastC() <= instance.getC()){
            return true;
        }
        else { /*both solutions don't fulfill c constraint */
            if(solX.getLastC() < solY.getLastC()){
                return false;
            }
            else if(solX.getLastC() > solY.getLastC()){
                return true;
            }
            else if(solX.getLastC() == solY.getLastC()){

                if(solX.getR() < solY.getR()){
                    return false;
                }
                else {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * returns a new random neighbor
     * @param sol
     * @return
     */
    private Solution randomNeighbor(Solution sol){

        Graph g = instance.getGraph();

        List<Node> servers = new ArrayList<Node>(sol.getServers());
        List<Node> available_nodes = instance.getGraph().getGraph();

        Node randomNode = available_nodes.get(rand.nextInt(available_nodes.size()));
        while(servers.contains(randomNode)){
            randomNode = available_nodes.get(rand.nextInt(available_nodes.size()));
        }
        servers.remove(rand.nextInt(servers.size()));
        servers.add(randomNode);
        g.clearUsages();
        g.setServers(servers);
        g.updateConstraints();

        int lastC = g.getMaxUsage();
        int newR = g.getMaxNeighbourSet();
        return new Solution(instance.getGraph(), newR, lastC,
                instance.getC() >= lastC);
    }
}
