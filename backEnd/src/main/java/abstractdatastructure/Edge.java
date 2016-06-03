package abstractdatastructure;

/**
 * Created by Matthijs on 24-4-2016.
 */

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */
public class Edge {
	
	/**
	 * Edge start id.
	 */
    private int startId;
    
    /**
     * Edge end id.
     */
    private int endId;
    
    /**
     * The number of genomes going through the edge.
     */
    private int weight;

    /**
     * Constructor to create an edge.
     * @param startId Start id.
     * @param endId End id.
     */
    public Edge(int startId, int endId) {
    	this.startId = startId;
    	this.endId = endId;
        this.weight = 1;
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

    /**
     * Set the startId.
     * @param startId New startId. 
     */
    public void setStartId(int startId) {
        this.startId = startId;
    }

    /**
     * Set the endId.
     * @param endId New endId.
     */
    public void setEndId(int endId) {
        this.endId = endId;
    }
}
