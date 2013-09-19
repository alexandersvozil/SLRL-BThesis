package Graph;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 8/4/13
 * Time: 1:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge {
 private Node node1;
 private Node node2;
 private int timesUsed;
 private int tmpTimesUsed;

 public Edge(Node n1, Node n2){
        this.node1 = n1;
        this.node2 = n2;
        timesUsed = 0;
 }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        if(node1 == null)
            throw new IllegalArgumentException("node1 is null");
        this.node1 = node1;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        if(node2 == null)
            throw new IllegalArgumentException("node2 is null");
        this.node2 = node2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (node1 != null ? !node1.equals(edge.node1) : edge.node1 != null) return false;
        if (node2 != null ? !node2.equals(edge.node2) : edge.node2 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = node1 != null ? node1.hashCode() : 0;
        result = 31 * result + (node2 != null ? node2.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "node1=" + node1.getName() +
                ", node2=" + node2.getName() +
                ", timesUsed=" + timesUsed +
                '}';
    }
}
