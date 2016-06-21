package com.pl.tagc.tagcwebapp;

/**
 * RibbonEdge class for later extension.
 * Created by Matthijs on 12-5-2016.
 */
public class AdaptedRibbonEdge {

    /**
     * Constructor to create an edge.
     */
    private int startId;

    /**
     * The end id.
     */
    private int endId;

    /**
     * The weight.
     */
    private int weight;

    private String color;

    private boolean suggested;

    /**
     * Increment the weight.
     */
    public void incrementWeight() {
        this.setWeight(this.getWeight() + 1);
    }

    /**
     * Gets the start id.
     *
     * @return the start id
     */
    public int getStartId() {
        return startId;
    }

    /**
     * Sets the start id.
     *
     * @param startId the new start id
     */
    public void setStartId(int startId) {
        this.startId = startId;
    }

    /**
     * Gets the end id.
     *
     * @return the end id
     */
    public int getEndId() {
        return endId;
    }

    /**
     * Sets the end id.
     *
     * @param endId the new end id
     */
    public void setEndId(int endId) {
        this.endId = endId;
    }

    /**
     * Gets the weight.
     *
     * @return the weight
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight.
     *
     * @param weight the new weight
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color.
     *
     * @param color the new weight
     */
    public void setColor(String color) {
        this.color = color;
    }

    public boolean isSuggested() {
        return suggested;
    }

    public void setSuggested(boolean suggested) {
        this.suggested = suggested;
    }
}