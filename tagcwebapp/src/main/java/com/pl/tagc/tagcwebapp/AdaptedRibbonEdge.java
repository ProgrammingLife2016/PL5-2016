package com.pl.tagc.tagcwebapp;

import abstracttree.Edge;

/**
 * RibbonEdge class for later extension.
 * Created by Matthijs on 12-5-2016.
 */
public class AdaptedRibbonEdge{
    /**
     * Constructor to create an edge.
     *
     * @param startId Start id.
     * @param endId   End id.
     */
	private int startId;
    private int endId;
    private int weight;

    /**
     * Increment the weight.
     */
    public void incrementWeight() {
        this.setWeight(this.getWeight() + 1);
    }

	public int getStartId() {
		return startId;
	}

	public void setStartId(int startId) {
		this.startId = startId;
	}

	public int getEndId() {
		return endId;
	}

	public void setEndId(int endId) {
		this.endId = endId;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}