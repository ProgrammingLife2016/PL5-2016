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
     * Constructor to create an edge.
     *
     * @param startId Start id.
     * @param endId   End id.
     */
    public RibbonEdge(int startId, int endId) {
        super(startId, endId);
        color = Color.black;
    }

    /**
     * Update the color of this edge by averaging it with color.
     *
     * @param color The color to average by.
     */
    public void updateColor(Color color) {
        int newBlue = (int) (this.color.getBlue() + color.getBlue()) / 2;
        int newRed = (int) (this.color.getRed() + color.getRed()) / 2;
        int newGreen = (int) (this.color.getGreen() + color.getGreen()) / 2;

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
}