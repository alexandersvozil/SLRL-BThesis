package Parsing;


import Graph.Graph;
import Graph.Node;
import Graph.Edge;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * User: svozil
 * Date: 8/14/13
 * Time: 4:07 PM
 * The parser for data.txt
 */
public class ParserAlternative {

    private static Logger log = Logger.getLogger(ParserAlternative.class);
    private HashMap<Integer, Node> reference = new HashMap<Integer, Node>();
    private Scanner sc2 = null;

    public List<SLRLInstance> parse() {
        ArrayList<SLRLInstance> slrlInstances = new ArrayList<SLRLInstance>();
        Graph currGraph = new Graph();
        List<Node> currNodes = new ArrayList<Node>();
        SLRLInstance slrlInstance = null;
        try {
            Scanner sc = new Scanner(new File("data.txt"));
            while (sc.hasNextLine()) {
                String next = sc.nextLine();

                //cities
                if (next.length() >= 1 && next.charAt(0) == 'C') {
                    sc2 = new Scanner(next);
                    sc2.next();//C
                    sc2.next();//^
                    Integer key = sc2.nextInt();
                    sc2.next();//^
                    sc2.next(); //lat
                    sc2.next(); //^
                    sc2.next(); //lon
                    sc2.next(); // ^
                    String cityname = "";
                    while (sc2.hasNext()) {
                        cityname += sc2.next();
                    }
//                log.debug(next+ " the key:"+ key + " cityname: "+ cityname);
                    reference.put(key, new Node(cityname));
                }
                if (next.length() >= 1 && next.charAt(0) == 'I') {
                    sc2 = new Scanner(next);
                    sc2.next(); // I
                    sc2.next(); // ^
                    sc2.next(); // isp_index unique id
                    sc2.next(); // ^
                    sc2.next(); // isp_type research or industrial
                    sc2.next(); // ^
                    String ispname = "";
                    while (sc2.hasNext()) {
                        ispname += " " +sc2.next();
                    }
                    ispname = ispname.substring(1);
                    //log.debug("ispname: " + ispname);
                    slrlInstance = new SLRLInstance();
                    slrlInstance.setTestInstanceName(ispname);
                    currGraph = new Graph();
                    currNodes = new ArrayList<Node>();
                }
                if (next.length() >= 1 && (next.charAt(0) == 'P')) {
                    sc2 = new Scanner(next);
                    sc2.next(); //P
                    sc2.next(); //^
                    int city1Key = sc2.nextInt();//city1
                    sc2.next();// ^
                    int city2Key = sc2.nextInt();//city2
                    sc2.next();//^
                    sc2.next();//bandwidth
                    Node city1 = reference.get(city1Key);
                    Node city2 = reference.get(city2Key);

                    Node nodeInSet1 = null;
                    Node nodeInSet2 = null;

                    if (currNodes.contains(city1)) {
                        Iterator<Node> nodeIterator = currNodes.iterator();
                        while (nodeIterator.hasNext()) {
                            Node currNode = nodeIterator.next();
                            if (currNode.equals(city1)) {
                                nodeInSet1 = currNode;
                            }
                        }
                    } else {
                        Node newNode1 = new Node(city1);
                        nodeInSet1 = newNode1;
                        currNodes.add(newNode1);
                    }

                    if (currNodes.contains(city2)) {
                        Iterator<Node> nodeIterator = currNodes.iterator();
                        while (nodeIterator.hasNext()) {
                            Node currNode = nodeIterator.next();
                            if (currNode.equals(city2)) {
                                nodeInSet2 = currNode;
                            }
                        }

                    } else {
                        Node newNode2 = new Node(city2);
                        nodeInSet2 = newNode2;
                        currNodes.add(newNode2);
                    }


                    Edge newEdge = new Edge(nodeInSet1, nodeInSet2);
                    Edge newEdge2 = new Edge(nodeInSet2, nodeInSet1);

                    nodeInSet1.addEdge(newEdge);
                    nodeInSet2.addEdge(newEdge2);

                }

                if (next.length() >= 1 && (next.charAt(0) == 'E')) {
                    int sumEdges=0;
                    int maximumDegree= 0;
                    for(Node n : currNodes){
                        currGraph.addNode(n);
                        sumEdges+=n.getEdges().size();
                        if(maximumDegree < n.getEdges().size()){
                            maximumDegree= n.getEdges().size();
                        }

                    }
                    slrlInstance.setV(currNodes.size());
                    slrlInstance.setE(sumEdges/2);
                    slrlInstance.setGraph(currGraph);
                    slrlInstance.setMaxDegree(maximumDegree);
                    slrlInstance.setK( (int) Math.ceil((double) slrlInstance.getV()*0.1));
                    slrlInstance.setR_lower((int) Math.ceil((double) slrlInstance.getV() / (double) slrlInstance.getK()));

                    int d = 4;
                    slrlInstance.setC((int) Math.ceil((double) slrlInstance.getV() / (double) (slrlInstance.getK() * maximumDegree)*d));
                    slrlInstances.add(slrlInstance);
                    log.debug("Name: " + slrlInstance.getTestInstanceName() + "Nodes: "+ slrlInstance.getGraph().size() + " Edges: " + sumEdges/2);
                    //slrlInstances.add(slrlInstance);



                }


            }
        } catch (FileNotFoundException e) {
            log.error("File data.txt was not found");
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return slrlInstances;
    }


}
