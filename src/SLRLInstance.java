import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/12/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class SLRLInstance {

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
    private Map<Node, Node[]> graph = new HashMap<Node,Node[]>();




}
