package Graph;

/**
 * User: svozil
 * Date: 8/7/13
 * Time: 2:53 PM
 */

/**
 * This Exception is thrown if Node is not found by BFS
 */
public class NodeNotFoundException extends Exception {
    public NodeNotFoundException(String s) {
        super(s);
    }

    public NodeNotFoundException() {
        super();
    }
}
