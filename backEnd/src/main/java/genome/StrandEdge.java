package genome;

/**
 * The Class StrandEdge.
 */
public class StrandEdge {

    /**
     * The start strands.
     */
    private Strand start;

    /**
     * The end strand.
     */
    private Strand end;

    /**
     * The weight.
     * The number of genomes going through the edge.
     */
    private int weight;

    /**
     * Instantiates a new strand edge.
     *
     * @param startEdge the start edge
     * @param endEdge   the end edge
     */
    public StrandEdge(Strand startEdge, Strand endEdge) {
        this.start = startEdge;
        this.end = endEdge;
        this.weight = 1;
    }

    /**
     * Gets the start.
     *
     * @return the start
     */
    public Strand getStart() {
        return start;
    }

    /**
     * Gets the end.
     *
     * @return the end
     */
    public Strand getEnd() {
        return end;
    }

    /**
     * Checks if the edge contains the strand.
     *
     * @param strand the strand
     * @return true, if successful
     */
    public boolean contains(Strand strand) {
        return start == strand || end == strand;
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

}
