package ribbonnodes;

import abstractdatastructure.Edge;

import java.awt.Color;

/**
 * The Edges between the Ribbon Nodes.
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonEdge extends Edge {

    /**
     * The color of this edge.
     */
    private Color color;

    /**
     * The starting node of this edge.
     */
    private RibbonNode start;

    /**
     * The end node of this edge.
     */
    private RibbonNode end;


    /**
     * Constructor to create an edge.
     */
    public RibbonEdge(RibbonNode start, RibbonNode end) {
        super(start.getId(), end.getId());
        this.start = start;
        this.end = end;
        color = Color.black;
    }

    /**
     * Update the color of this edge by averaging it with color.
     *
     * @param color The color to average by.
     */
    public void updateColor(Color color) {
        int newRed = (int) (this.color.getRed() + color.getRed()) / 2;
        int newGreen = (int) (this.color.getGreen() + color.getGreen()) / 2;
        int newBlue = (int) (this.color.getBlue() + color.getBlue()) / 2;

        this.color = new Color(newRed, newGreen, newBlue);


    }

    /**
     * Add a genome by updating the color and weight of this edge.
     *
     * @param genomeColor the color of the new genome.
     */
    public void addGenomeToEdge(Color genomeColor) {
        updateColor(genomeColor);
        incrementWeight();
    }

    /**
     * Increment the weight.
     */
    public void incrementWeight() {
        this.setWeight(this.getWeight() + 1);
    }

    /**
     * The getter of color.
     *
     * @return The color of this Ribbon.
     */
    public Color getColor() {
        return color;
    }

    /**
     * The setter of color.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Get the starting node of this edge.
     *
     * @return the start node.
     */
    public RibbonNode getStart() {
        return start;
    }

    /**
     * Set the start node and start id of this edge.
     *
     * @param start the start node.
     */
    public void setStart(RibbonNode start) {
        this.start = start;
        this.setStartId(start.getId());
    }

    /**
     * Get the end node of this edge.
     *
     * @return The end node.
     */
    public RibbonNode getEnd() {
        return end;
    }

    /**
     * Set the end node and end id of this edge.
     *
     * @param end the end node to set.
     */
    public void setEnd(RibbonNode end) {
        this.end = end;
        this.setEndId(end.getId());

    }


}