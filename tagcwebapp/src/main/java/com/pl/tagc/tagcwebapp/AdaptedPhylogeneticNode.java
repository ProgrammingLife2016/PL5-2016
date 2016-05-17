package com.pl.tagc.tagcwebapp;

import java.util.ArrayList;

import phylogenetictree.PhylogeneticNode;


public class AdaptedPhylogeneticNode {
	
    /**
     * The Genomes contained in this nodes children.
     */
    private ArrayList<String> genomes;
    private ArrayList<PhylogeneticNode> children;

    /**
     * The name of this genome, "" if not a leaf.
     */
    private String nameLabel;
    /**
     * The distance to its parent.
     */
    private double distance;

    /**
     * returns a string of lenght 0 if the node is not a leaf.
     *
     * @return the node name label
     */
    public String getNameLabel() {
        return nameLabel;
    }

    /**
     * Get the distance.
     *
     * @return The distance.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Get the genomes that are children of this node.
     *
     * @return a list of genome labels
     */
    public ArrayList<String> getGenomes() {
        return genomes;
    }

	public void setGenomes(ArrayList<String> genomes) {
		this.genomes = genomes;
	}

	public void setNameLabel(String nameLabel) {
		this.nameLabel = nameLabel;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public ArrayList<PhylogeneticNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<PhylogeneticNode> children) {
		this.children = children;
	}

}
