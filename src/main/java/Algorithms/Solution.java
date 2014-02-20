package Algorithms;

import Graph.Graph;
import Graph.Node;

/**
 * Created with IntelliJ IDEA.
 * User: svozil
 * Date: 9/16/13
 * Time: 12:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class Solution {
    private Graph graph;
    private int k;
    private int c;
    private double r;
    private int lastC;
    private boolean solved;
    private int r_lower;

    public Solution(Graph graph, int k, int c, double r, int lastC, boolean solved, int r_lower) {
        this.graph = new Graph(graph.getGraph(), graph.getServers());
        this.k = k;
        this.c = c;
        this.r = r;
        this.lastC = lastC;
        this.solved = solved;
        this.r_lower = r_lower;
    }


    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    public double getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getLastC() {
        return lastC;
    }

    public void setLastC(int lastC) {
        this.lastC = lastC;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getR_lower() {
        return r_lower;
    }
    public void setR_lower(int r_lower){

        this.r_lower = r_lower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Solution solution = (Solution) o;

        boolean differenceCheck = true;
        for(Node n : solution.getGraph().getServers()){
            if(!graph.getServers().contains(n)){
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return graph != null ? graph.hashCode() : 0;
    }

}
