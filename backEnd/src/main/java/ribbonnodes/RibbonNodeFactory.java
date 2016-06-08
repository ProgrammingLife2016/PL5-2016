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
    public static RibbonNode makeRibbonNodeFromStrand(int id, Strand strand, ArrayList<String> activeGenomes) {

        ArrayList<String> actGen= strand.getGenomes();
        actGen.retainAll(activeGenomes);
        RibbonNode ribbon = new RibbonNode(id, actGen);
        ribbon.setX(strand.getX());
        ribbon.addStrand(strand);

        return ribbon;
    }


    public static ArrayList<RibbonNode> makeRibbonNodesFromSplit(RibbonNode node, int maxId){
        ArrayList<RibbonNode> result = new ArrayList<>();
        for(String genome:node.getGenomes()){
            ArrayList<String> ribbonGenome = new ArrayList<>();
            ribbonGenome.add(genome);
            RibbonNode ribbon = new RibbonNode(maxId++, ribbonGenome);
            ribbon.setX(node.getX());
            ribbon.addStrands(node.getStrands());
            result.add(ribbon);
        }
        return result;



    }

    public static RibbonNode collapseNodes(RibbonNode node1, RibbonNode node2){

        node1.addStrands(node2.getStrands());
        for (RibbonEdge edge : node2.getOutEdges()) {
            edge.setStartId(node1.getId());
        }
        node1.setOutEdges(node2.getOutEdges());

        return node1;
    }


}
