package com.pl.tagc.tagcwebapp;


import javax.xml.bind.annotation.XmlTransient;

public class Edge {
    @XmlTransient public Node target;
    public int weight;
    public int nodeId;

    public Edge() {} // JAXB needs this

    public Edge(Node target, int weight) {
        this.target = target;
        this.weight = weight;
    }
}