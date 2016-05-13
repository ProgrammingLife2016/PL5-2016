package genome;

/**
 * Created by Matthijs on 24-4-2016.
 */

import abstracttree.Edge;

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


}
