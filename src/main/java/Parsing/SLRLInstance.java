package Parsing;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/12/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SLRLInstance {
    private final static Logger log = Logger.getLogger(SLRLInstance.class);

    public String getTestInstanceName() {
        return testInstanceName;
    }


    //servers
    private List<ParseNode> servers;

    /**
     * Name of the instance
     */
    private String testInstanceName;


    /**
     * maximum # of servers for the solution, calculated by number of (vertices * 0.1) and then rounded up (ceil)
     * e.g. 14 vertices: 14 * 0.1 = 1,4 => r = 2
     */
    private int k;

    /**
     * max size of neighbour set
     */
    private int r;

    /**
     * lower bound of r, calculated by: |E| / k
     */
    private int r_lower;

    /**
     * ratio of calculated solution and r_lower: r/r*, shall not be more than 2
     */
    private double ratio_r;


    /**
     * maximum # of shortest paths which pass any edge e, calculated by ceil(|V|/(k * maxDegree))*d, where d = 4
     */
    private int c;

    /**
     * lower bound for c, calculated by ceil(|V|/(k*maxDegree))
     */
    private int c_lower;


    /**
     * maximum Degree of the SLRL Instance
     */
    private int maxDegree;

    /**
     * number of Vertices
     */
    private int V;


    /**
     * number of Edges
     */
    private int E;


    /**
     * Adjacence List for storing all the Nodes, with their neighbours
     */
    private Map<ParseNode, Vector<ParseNode>> nodeVectorHashMap = new HashMap<ParseNode, Vector<ParseNode>>();


    public void setTestInstanceName(String testInstanceName) {
        this.testInstanceName = testInstanceName;
    }

    public void setNodeVectorHashMap(Map<ParseNode, Vector<ParseNode>> nodeVectorHashMap) {
        this.nodeVectorHashMap = nodeVectorHashMap;
        setV(nodeVectorHashMap.size());
        int sum = 0;
        int curMaxDegree = 0;
        for (Map.Entry<ParseNode, Vector<ParseNode>> entry : nodeVectorHashMap.entrySet()) {
            sum += entry.getValue().size();
                /* check for max degree */
            if (entry.getValue().size() > curMaxDegree) {
                curMaxDegree = entry.getValue().size();
            }
                /* set max degree */
            setMaxDegree(curMaxDegree);
        }
        setE(sum / 2);

        //set k
        this.k = (int) Math.ceil(((double) getV()) * 0.1);

        //set r_lower
        if (k != 0) {
            this.r_lower = Math.round(getE() / k);
        } else {
            log.error("this instance doesnt work: (same for the paper)\t  " + this.toString());
        }

        //set c
        int d = 4;
        this.c = (int) Math.ceil(((double)getV()/(double)(this.k * maxDegree)) * (double)d);

        this.c_lower = (int) Math.ceil(((double)getV()/(double)(this.k * maxDegree)));



    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SLRLInstance that = (SLRLInstance) o;
        if (!testInstanceName.equals(that.testInstanceName)) return false;
        if (E != that.E) return false;
        if (V != that.V) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = testInstanceName != null ? testInstanceName.hashCode() : 0;
        result = 31 * result + k;
        result = 31 * result + r;
        result = 31 * result + r_lower;
        temp = Double.doubleToLongBits(ratio_r);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + c;
        result = 31 * result + c_lower;
        result = 31 * result + maxDegree;
        result = 31 * result + V;
        result = 31 * result + E;
        result = 31 * result + (nodeVectorHashMap != null ? nodeVectorHashMap.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Parsing.SLRLInstance{" +
                "testInstanceName='" + testInstanceName + '\'' +
                ", k=" + k +
                ", r=" + r +
                ", r_lower=" + r_lower +
                ", ratio_r=" + ratio_r +
                ", c=" + c +
                ", c_lower=" + c_lower +
                ", maxDegree=" + maxDegree +
                ", V=" + V +
                ", E=" + E +
                //", nodeVectorHashMap=" + graphToString() +
                '}';
    }

    public String graphToString() {
        String totalString = "";
        for (Map.Entry<ParseNode, Vector<ParseNode>> entry : nodeVectorHashMap.entrySet()) {
            totalString += "\nKey: " + entry.getKey().toString(); //+ "\n Adj. Nodes: \n";
            for (ParseNode n : entry.getValue()) {
                totalString += n.toString() + "\n ";

            }
            totalString += "\n";

        }
        return totalString;  //To change body of created methods use File | Settings | File Templates.
    }

    public Map<ParseNode, Vector<ParseNode>> getNodeVectorHashMap() {
        return nodeVectorHashMap;
    }

    public int getE() {
        return E;
    }

    public void setE(int e) {
        E = e;
    }

    public int getV() {
        return V;
    }

    public void setV(int v) {
        V = v;
    }

    public int getMaxDegree() {
        return maxDegree;
    }

    public void setMaxDegree(int maxDegree) {
        this.maxDegree = maxDegree;
    }

    public int getC() {
        return c;
    }

    public int getK() {
        return k;
    }
    public List<ParseNode> getServers() {
        return servers;
    }

    public void setServers(List<ParseNode> servers) {
        this.servers = servers;
    }
}
