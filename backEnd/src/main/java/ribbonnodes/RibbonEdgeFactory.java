package ribbonnodes;

import genome.Genome;

/**
 * A factory to create Ribbon Edges.
 * Created by Matthijs on 7-6-2016.
 */
public abstract class RibbonEdgeFactory {

    /**
     * Create and return a Ribbonedge.
     *
     * @param start  The starting node of the edge.
     * @param end    The ending node of the edge.
     * @param genome The genome to color the edge for.
     * @return The Created edge.
     */
    public static RibbonEdge createRibbonEdge(RibbonNode start, RibbonNode end, Genome genome) {
        RibbonEdge edge = new RibbonEdge(start, end);
        edge.setColor(genome.getColor());

        if (!start.getStrands().isEmpty()
                &&!genome.isNextStrand(start.getStrands().get(0), end.getStrands().get(0))) {
            edge.setSuggested(true);
        }
        return edge;
    }

}
