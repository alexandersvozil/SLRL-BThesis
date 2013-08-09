package Parsing;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 7/12/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParseNode {

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Name of the Parsing.ParseNode e.g. "San Francisco"
     */
    private String name;
    /**
     * Naive implementation of the nodes that used this path as shortest
     */
    @Override
    public String toString() {
        return "Parsing.ParseNode: {" +
                "name='" + name + '\'' +
                '}';
    }

    private boolean isServer;
    private int neighbhouringSetSize;

    public ParseNode(){
    }
    public ParseNode(String name){
        this.name = name;
    }
    public ParseNode(ParseNode n){
        this.name = n.name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParseNode node = (ParseNode) o;

        if (name != null ? !name.equals(node.name) : node.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public String getName() {
        return name;
    }
}
