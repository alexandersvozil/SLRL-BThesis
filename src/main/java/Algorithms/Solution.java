package Algorithms;

import Graph.Graph;

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

    public Solution(Graph graph, int k, int c, double r, int lastC, boolean solved) {
        this.graph = new Graph(graph.getGraph(), graph.getServers());
        this.k = k;
        this.c = c;
        this.r = r;
        this.lastC = lastC;
        this.solved = solved;
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
}
