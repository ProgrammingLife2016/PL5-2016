package com.pl.tagc.tagcwebapp;


import java.util.ArrayList;

public class Node {
    public String info;
    public int x;
    public int y;
    public int weight;
    public ArrayList<Edge> edges = new ArrayList<>();


    public Node() {} // JAXB needs this

    public Node(String info, int x, int y, int weight) {
        this.info = info;
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public void addEdge(Node target, int weight) {
        for (Edge edge: edges) {
            if (edge.target.equals(target)) {
                edge.weight += weight;
                return;
            }
        }
        edges.add(new Edge(target, weight));
    }
}