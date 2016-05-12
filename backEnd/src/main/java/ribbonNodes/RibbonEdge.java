package ribbonnodes;

import abstracttree.Edge;

/**
 * RibbonEdge class for later extension.
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonEdge extends Edge {
    /**
     * Constructor to create an edge.
     *
     * @param startId Start id.
     * @param endId   End id.
     */
    public RibbonEdge(int startId, int endId) {
        super(startId, endId);
    }
}
