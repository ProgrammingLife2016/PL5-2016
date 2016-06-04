package genome;

/**
 * Created by Matthijs on 24-4-2016.
 */

import abstractdatastructure.Edge;
import org.neo4j.graphdb.Relationship;

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */
public class StrandEdge  {

    private Strand start;
    private Strand end;
    private int weight;
	/**
     * Constructor to create an edge.
     *
     * @param startId Start id.
     * @param endId   End id.
     */
    public StrandEdge(Strand startEdge, Strand endEdge) {
        this.start = startEdge;
        this.end = endEdge;
        this.weight = 1;

    }

    /**
     * Constructor to create a new genome.
     * @param o The Object (returned by the Cypherquery) from which a StrandEdge should be made.
     */
//    public StrandEdge(Object o) {
//        super(0, 0);
//        Relationship rela = (Relationship) o;
//        setStartId(java.lang.Math.toIntExact((long) rela.getStartNode().getProperty("id")));
//        setEndId(java.lang.Math.toIntExact((long) rela.getEndNode().getProperty("id")));
//    }

	public Strand getStart() {
		return start;
	}

	public Strand getEnd() {
		return end;
	}

	public boolean contains(Strand strand) {
		// TODO Auto-generated method stub
		return start == strand || end == strand;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}
