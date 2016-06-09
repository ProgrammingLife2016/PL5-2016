package ribbonnodes;

import genome.Strand;

import java.util.ArrayList;

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

        ArrayList<String> actGen = strand.getGenomes();
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
            ArrayList<String> ribbonGenome = new ArrayList<>();
            ribbonGenome.add(genome);
            RibbonNode ribbon = new RibbonNode(++maxId, ribbonGenome);
            ribbon.setX(node.getX());
            ribbon.addStrands(node.getStrands());
            result.add(ribbon);
        }
        return result;


    }

    /**
     * Collapse a group of nodes and lead all edges the right way.
     *
     * @param nodesToCollapse The nodes to collapse in to the first node of the array.
     * @return The collapsed node.
     */
    public static RibbonNode collapseNodes(ArrayList<RibbonNode> nodesToCollapse) {
        RibbonNode node = nodesToCollapse.get(0);
        if (nodesToCollapse.size() > 1) {
            for (int i = 0; i < nodesToCollapse.size(); i++) {
                RibbonNode node2 = nodesToCollapse.get(i);
                node.addStrands(node2.getStrands());

                if (i == nodesToCollapse.size() - 1) {
                    for (RibbonEdge edge : node2.getOutEdges()) {
                        edge.setStartId(node.getId());
                    }
                    node.setOutEdges(node2.getOutEdges());
                }

            }
        }


        return node;
    }


}
