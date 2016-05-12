package abstracttree;

/**
 * Created by Matthijs on 24-4-2016.
 */

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */
public class Edge {
    private int startId;
    private int endId;
    private int weight;

    /**
     * Constructor to create an edge.
     * @param startId Start id.
     * @param endId End id.
     */
    public Edge(int startId, int endId) {
    	this.startId = startId;
    	this.endId = endId;
    }

    /**
     * Set the weight of an edge.
     * @param weight The new weight.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * Get the start id of an edge.
     * @return The start id.
     */
    public int getStart() {
        return startId;
    }

    /**
     * Get the end id of an edge.
     * @return The end id.
     */
    public int getEnd() {
        return endId;
    }

    /**
     * Get the weight of an edge.
     * @return The weight.
     */
    public int getWeight() {
        return weight;
    }
}
