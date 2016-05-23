package com.pl.tagc.tagcwebapp;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import phylogenetictree.PhylogeneticNode;

/**
 * The Class PhylogeneticTreeObject.
 */
@XmlRootElement
@XmlType(propOrder = {"name", "children"})
public class PhylogeneticTreeObject {

    /**
     * Indicator that this is the root. Needed in the resulting JSON
     */
    private String name = "root";

    /**
     * The children.
     */
    private ArrayList<PhylogeneticNode> children;

    /**
     * Instantiates a new phylogenetic tree object.
     */
    public PhylogeneticTreeObject() {
    }

    /**
     * Instantiates a new phylogenetic tree object.
     *
     * @param phylogeneticTreeRoot the phylogenetic tree root
     */
    public PhylogeneticTreeObject(PhylogeneticNode phylogeneticTreeRoot) {
        this.children = phylogeneticTreeRoot.getChildren();
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the children.
     *
     * @return the children
     */
    public ArrayList<PhylogeneticNode> getChildren() {
        return children;
    }

    /**
     * Sets the children.
     *
     * @param children the new children
     */
    public void setChildren(ArrayList<PhylogeneticNode> children) {
        this.children = children;
    }
}