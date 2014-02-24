package Algorithms;

import Graph.Graph;
import Graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 9/16/13
 * Time: 12:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class Solution {
    private int r;
    private int lastC;
    private boolean solved;
    private List<Node> servers;

    public Solution(Graph g, int r, int lastC, boolean solved) {
        this.r = r;
        this.lastC = lastC;
        this.solved = solved;

        servers = new ArrayList<Node>();
        for(Node n : g.getServers()){
            servers.add(n);
        }
    }

    public Solution(Solution other, int r, int lastC, boolean solved) {
        this.r = r;
        this.lastC = lastC;
        this.solved = solved;

        servers = new ArrayList<Node>();
        for(Node n : other.getServers()){
            servers.add(n);
        }
    }


    public int getR() {
        return r;
    }

    public int getLastC() {
        return lastC;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        for(Node n : getServers()){
            if(!solution.servers.contains(n)){
                return false;
            }
        }

        return true;
    }

    public List<Node> getServers() {
        return servers;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "r=" + r +
                ", lastC=" + lastC +
                ", solved=" + solved +
                ", servers=" + servers +
                '}';
    }
}
