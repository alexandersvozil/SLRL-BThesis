/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/12/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Node {

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Name of the Node e.g. "San Francisco"
     */
    private String name;

    @Override
    public String toString() {
        return "Node: {" +
                "name='" + name + '\'' +
                '}';
    }

    private boolean isServer;
    private int neighbhouringSetSize;

    public Node(){
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (name != null ? !name.equals(node.name) : node.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
