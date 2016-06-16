package ribbonnodes;

import genome.Strand;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Factory for the making of ribbons derived from strands.
 * Created by Matthijs on 7-6-2016.
 */
public abstract class RibbonNodeFactory {

    /**
     * Create a ribbon node based on a strand.
     *
     * @param id            The id of the ribbon node.
     * @param strand        The strand to base the ribbon node form.
     * @param activeGenomes The genomes to filter this node for.
     * @return The created ribbonNode.
     */
    public static RibbonNode makeRibbonNodeFromStrand(int id,
                                                      Strand strand,
                                                      ArrayList<String> activeGenomes) {

        HashSet<String> actGen = strand.getGenomes();
        actGen.retainAll(activeGenomes);
        RibbonNode ribbon = new RibbonNode(id, actGen);
        ribbon.setX(strand.getX());
        ribbon.addStrand(strand);

        return ribbon;
    }


    /**
     * Collapse a group of nodes and lead all edges the right way.
     *
     * @param nodesToCollapse The nodes to collapse in to the first node of the array.
     * @return The (Empty) endnode of the collapsed ribbon part, null if no collapsing was done.
     */
    public static RibbonNode collapseNodes(ArrayList<RibbonNode> nodesToCollapse) {
        RibbonNode startNode = nodesToCollapse.get(0);
        RibbonNode oldEnd = nodesToCollapse.get(nodesToCollapse.size() - 1);
        RibbonNode newEnd = new RibbonNode(oldEnd.getId(),
                oldEnd.getGenomes());
        if (nodesToCollapse.size() > 1) {
            for (int i = 1; i < nodesToCollapse.size(); i++) {
                RibbonNode node2 = nodesToCollapse.get(i);
                startNode.addStrands(node2.getStrands());

            }
            newEnd.setX(oldEnd.getX());
            newEnd.setY(oldEnd.getY());
            newEnd.setOutEdges(nodesToCollapse.get(nodesToCollapse.size() - 1).getOutEdges());
            startNode.getOutEdges().get(0).setEnd(newEnd);
            newEnd.addEdge(startNode.getOutEdges().get(0));
            newEnd.setVisible(false);
            return newEnd;
        }


        return null;
    }


}
