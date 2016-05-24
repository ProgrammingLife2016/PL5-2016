package ribbonnodes;

import java.awt.Color;

import abstractdatastructure.Edge;

/**
 * The Edges between the Ribbon Nodes.
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonEdge extends Edge {

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
     * Increment the weight.
     */
    public void incrementWeight() {
        this.setWeight(this.getWeight() + 1);
    }

    /**
     * The getter of color.
     * @return The color of this Ribbon.
     */
    public Color getColor() {
        return color;
    }

    /**
     * The setter of color.
     * @param color The color to set.
     */
    public void setColor(Color color) {
        this.color = color;
    }
}