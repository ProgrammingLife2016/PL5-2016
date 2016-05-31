package com.pl.tagc.tagcwebapp;

import java.util.ArrayList;
import ribbonnodes.RibbonEdge;

/**
 * The Class AdaptedRibbonNode.
 */
public class AdaptedRibbonNode {
	

    /**
     * The name of this genome, "" if not a leaf.
     */
    private int id;
    
    /** The edges. */
    private ArrayList<RibbonEdge> edges;
    
    /** The genomes. */
    private ArrayList<String> genomes;
    
    /** The label. */
    private String label;
	private int x;
	private int y;

	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label.
	 *
	 * @param label the new label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the edges.
	 *
	 * @return the edges
	 */
	public ArrayList<RibbonEdge> getEdges() {
		return edges;
	}

	/**
	 * Sets the edges.
	 *
	 * @param edges the new edges
	 */
	public void setEdges(ArrayList<RibbonEdge> edges) {
		this.edges = edges;
	}

	/**
	 * Gets the genomes.
	 *
	 * @return the genomes
	 */
	public ArrayList<String> getGenomes() {
		return genomes;
	}

	/**
	 * Sets the genomes.
	 *
	 * @param genomes the new genomes
	 */
	public void setGenomes(ArrayList<String> genomes) {
		this.genomes = genomes;
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

	/**
	 * Gets the x coordinate.
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x coordinate.
	 * @param x The x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate.
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}


	/**
	 * Sets the y coordinate.
	 * @param y The y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

}
