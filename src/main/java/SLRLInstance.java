import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/12/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SLRLInstance {

    /**
     * Name of the instance
     */
    private String testInstanceName;

    /**
     *maximum # of servers for the solution, calculated by number of (vertices * 0.1) and then rounded up
     * e.g. 14 vertices: 14 * 0.1 = 1,4 => r = 2
     */
    private int k;

    /**
     *max size of neighbour set
     */
    private int r;

    /**
     *lower bound of r, calculated by: |V| / k
     */
    private int r_lower;

    /**
     * ratio of calculated solution and r_lower: r/r*, shall not be more than 2
     */
    private double ratio_r;

    /**
     * maximum # of shortest paths which pass any edge e, calculated by (|V|/(k * maxDegree))*d rounded up, where d = 4
     */
    private int c;

    /**
     * lower bound for c, calculated by (|V|/(k*maxDegree)) rounded up
     */
    private int c_lower;

    /**
     *  maximum Degree of an SLRL Instance
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
    private Map<Node, Vector<Node>> graph = new HashMap<Node,Vector<Node>>();


    public void setTestInstanceName(String testInstanceName) {
        this.testInstanceName = testInstanceName;
    }
    public void setGraph(Map<Node, Vector<Node>> graph) {
        this.graph = graph;
        setV(graph.size());
        int sum= 0;
            for(Map.Entry<Node,Vector<Node>> entry : graph.entrySet() ){
                sum+=entry.getValue().size();
            }
        setE(sum/2);

    }



    @Override
    public String toString() {
        return "SLRLInstance{" +
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
                ", graph=" +// graphToString() +
                '}';
    }

    private String graphToString() {
        String totalString = "";
        for(Map.Entry<Node,Vector<Node>> entry : graph.entrySet() ){
            totalString+= "Key: "+entry.getKey().toString() + " Adj. Nodes: \n";
            for(Node n: entry.getValue()){
                totalString+=n.toString()+" ";

            }
            totalString+= "\n";

        }
        return totalString;  //To change body of created methods use File | Settings | File Templates.
    }

    public Map<Node,Vector<Node>> getGraph() {
        return graph;
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
}
