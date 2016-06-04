package genome;

// TODO: Auto-generated Javadoc
/**
 * The Class StrandEdge.
 */
public class StrandEdge  {

    /** The start. */
    private Strand start;
    
    /** The end. */
    private Strand end;
    
    /** The weight. */
    private int weight;
	
	/**
	 * Instantiates a new strand edge.
	 *
	 * @param startEdge the start edge
	 * @param endEdge the end edge
	 */
    public StrandEdge(Strand startEdge, Strand endEdge) {
        this.start = startEdge;
        this.end = endEdge;
        this.weight = 1;

    }

//    public StrandEdge(Object o) {
//        super(0, 0);
//        Relationship rela = (Relationship) o;
//        setStartId(java.lang.Math.toIntExact((long) rela.getStartNode().getProperty("id")));
//        setEndId(java.lang.Math.toIntExact((long) rela.getEndNode().getProperty("id")));
//    }


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
