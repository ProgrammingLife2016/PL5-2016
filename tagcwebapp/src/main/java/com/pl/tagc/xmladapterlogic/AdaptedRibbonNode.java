package com.pl.tagc.xmladapterlogic;

import java.util.ArrayList;
import java.util.Locale;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import phylogenetictree.PhylogeneticNode;
import ribbonnodes.RibbonEdge;

//@XmlType(propOrder={"nameLabel","originalChildOrder","children","distance","annotation"})
public class AdaptedRibbonNode {
	

    /**
     * The name of this genome, "" if not a leaf.
     */
    private int id;
    private ArrayList<RibbonEdge> edges;
    private ArrayList<String> genomes;
    private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<RibbonEdge> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<RibbonEdge> edges) {
		this.edges = edges;
	}

	public ArrayList<String> getGenomes() {
		return genomes;
	}

	public void setGenomes(ArrayList<String> genomes) {
		this.genomes = genomes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
