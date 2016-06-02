package genome;

/**
 * Created by Matthijs on 24-4-2016.
 */

import abstractdatastructure.Edge;
import org.neo4j.graphdb.Relationship;

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */
public class StrandEdge extends Edge {

    /**
     * Constructor to create an edge.
     *
     * @param startId Start id.
     * @param endId   End id.
     */
    public StrandEdge(int startId, int endId) {
        super(startId, endId);

    }

    /**
     * Constructor to create a new genome.
     * @param o The Object (returned by the Cypherquery) from which a StrandEdge should be made.
     */
    public StrandEdge(Object o) {
        super(0, 0);
        Relationship rela = (Relationship) o;
        setStartId(java.lang.Math.toIntExact((long) rela.getStartNode().getProperty("id")));
        setEndId(java.lang.Math.toIntExact((long) rela.getEndNode().getProperty("id")));
    }

}
