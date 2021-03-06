package com.pl.tagc.tagcwebapp;

import mutation.AbstractMutation;
import ribbonnodes.RibbonEdge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class AdaptedRibbonNode.
 */
public class AdaptedRibbonNode {

    /**
     * The name of this genome, "" if not a leaf.
     */
    private int id;

    /**
     * The edges.
     */
    private ArrayList<RibbonEdge> edges;

    /**
     * The genomes.
     */
    private HashSet<String> genomes;

    /**
     * The genomes.
     */
    private ArrayList<String> mutations;

    /**
     * The label.
     */
    private String label;

	/**
	 * The annotations within this node.
	 */
	private ArrayList<String> annotations;
    

    /**
     * The x coordinate.
     */
    private int x;

    /**
     * The y coordinate.
     */
    private int y;

    /**
     * The is visible.
     */
    private boolean isVisible;

	/** The convergence map. */
	private Map<String, Double> convergenceMap = new HashMap<String, Double>();

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
    public HashSet<String> getGenomes() {
        return genomes;
    }

    /**
     * Sets the genomes.
     *
     * @param genomes the new genomes
     */
    public void setGenomes(HashSet<String> genomes) {
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
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate.
     *
     * @param x The x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }


    /**
     * Sets the y coordinate.
     *
     * @param y The y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * Getter for is visible.
     *
     * @return whether node is visible.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Setter for visible.
     *
     * @param visible true if node should be drawn.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }

	/**
	 * Getter for annotations.
	 *
	 * @return The annotations.
	 */
	public ArrayList<String> getAnnotations() {
		return annotations;
	}

	/**
	 * Setter for annotations.
	 *
	 * @param annotations The annotations to be set.
	 */
	public void setAnnotations(ArrayList<String> annotations) {
		this.annotations = annotations;

	}
    /**
     * Getter for mutations.
     *
     * @return The list of set mutations.
     */
    public ArrayList<String> getMutations() {
        return mutations;
    }

    /**
     * Setter for mutations.
     *
     * @param mutations The list of mutations to be set.
     */
	public void setMutations(ArrayList<AbstractMutation> mutations) {
		this.mutations = new ArrayList<String>();
		for (AbstractMutation m : mutations) {
			this.mutations.add(m.toString());
			this.convergenceMap = m.getConvergenceMap();
		}
	}

	/**
	 * Gets the convergence map.
	 *
	 * @return the convergence map
	 */
	public Map<String, Double> getConvergenceMap() {
		return convergenceMap;
	}

	/**
	 * Sets the convergence map.
	 *
	 * @param convergenceMap the convergence map
	 */
	public void setConvergenceMap(Map<String, Double> convergenceMap) {
		this.convergenceMap = convergenceMap;
	}
}
