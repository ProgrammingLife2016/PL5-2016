package com.pl.tagc.tagcwebapp;

import phylogenetictree.PhylogeneticNode;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Locale;


/**
 * The Class AdaptedPhylogeneticNode.
 */
@XmlType(propOrder = {"nameLabel", "originalChildOrder",
        "children", "distance", "annotation", "id"})
public class AdaptedPhylogeneticNode {

    /**
     * The children.
     */
    private ArrayList<PhylogeneticNode> children;

    /**
     * The name of this genome, "" if not a leaf.
     */
    private String nameLabel;
    /**
     * The distance to its parent.
     */
    private int id;

    /**
     * The distance.
     */
    private double distance;

    /**
     * The annotation.
     */
    private String annotation = "";

    /**
     * The original child order.
     */
    private int originalChildOrder;

    /**
     * returns a string of lenght 0 if the node is not a leaf.
     *
     * @return the node name label
     */
    @XmlElement(name = "name")
    public String getNameLabel() {
        return nameLabel;
    }

    /**
     * Sets the name label.
     *
     * @param nameLabel the new name label
     */
    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
    }

    /**
     * Get the distance.
     *
     * @return The distance.
     */
    @XmlElement(name = "attribute")
    private String getDistance() {
        return String.format(Locale.US, "%1.11e", distance);
    }

    /**
     * Sets the distance.
     *
     * @param distance the new distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
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

    /**
     * Gets the annotation.
     *
     * @return the annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * Sets the annotation.
     *
     * @param annotation the new annotation
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    /**
     * Gets the original child order.
     *
     * @return the original child order
     */
    @XmlElement(name = "original_child_order")
    public int getOriginalChildOrder() {
        return originalChildOrder;
    }

    /**
     * Sets the original child order.
     *
     * @param originalChildOrder the new original child order
     */
    public void setOriginalChildOrder(int originalChildOrder) {
        this.originalChildOrder = originalChildOrder;

    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

}
