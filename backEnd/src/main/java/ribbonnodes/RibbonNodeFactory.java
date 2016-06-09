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
     * For every genome contained in a Ribbonnode,
     * make a copy containing only that genome and return it.
     *
     * @param node  the node to split.
     * @param maxId The current max id in the ribbonnode graph (so no double ids get used).
     * @return A list of ribbon Nodes containing only one genome.
     */
    public static ArrayList<RibbonNode> makeRibbonNodesFromSplit(RibbonNode node, int maxId) {
        ArrayList<RibbonNode> result = new ArrayList<>();
        for (String genome : node.getGenomes()) {
            HashSet<String> ribbonGenome = new HashSet<String>();
            ribbonGenome.add(genome);
            RibbonNode ribbon = new RibbonNode(++maxId, ribbonGenome);
            ribbon.setX(node.getX());
            ribbon.addStrands(node.getStrands());
            result.add(ribbon);
        }
        return result;


    }

    /**
     * Collapse node 2 into node 1, and lead all edges the right way.
     *
     * @param node1 The node to collapse in to.
     * @param node2 The node that is collapsed into node1.
     * @return The collapsed node.
     */
    public static RibbonNode collapseNodes(RibbonNode node1, RibbonNode node2) {

        node1.addStrands(node2.getStrands());
        for (RibbonEdge edge : node2.getOutEdges()) {
            edge.setStartId(node1.getId());
        }
        node1.setOutEdges(node2.getOutEdges());

        return node1;
    }


}
