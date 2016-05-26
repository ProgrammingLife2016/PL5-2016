package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Strand;
import genome.StrandEdge;
import genome.Genome;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthijs on 18-5-2016.
 */
public final class RibbonController {

    /**
     * Create ribbonController object.
     * This should not be possible, throw an exception.
     */
    private RibbonController() {
        throw new UnsupportedOperationException();
    }

    /**
     * Get the ribbon nodes with edges for a certain view in the GUI.
     *
     * @param minX        the minx of the view.
     * @param maxX        the maxx of the view.
     * @param zoomLevel   the zoomlevel of the view.
     * @param genomeGraph the genome graph
     * @return The list of ribbonNodes.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public static ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel,
                                                       GenomeGraph genomeGraph, DataTree dataTree) {
        ArrayList<String> activeGenomes = genomeGraph.getActiveGenomes();

        activeGenomes.add("TKK_02_0010.fasta");
        activeGenomes.add("TKK_02_0006.fasta");
        activeGenomes.add("TKK_02_0007.fasta");
        activeGenomes.add("TKK_02_0001.fasta");


        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<DataNode> filteredNodes = dataTree.getDataNodes(minX, maxX, activeGenomes, 100);

        for (DataNode dataNode : filteredNodes) {
            for (Strand strand : dataNode.getStrands()) {
                RibbonNode ribbon = new RibbonNode(0, strand.getGenomes());
                ribbon.setX(strand.getX());
                ribbon.setY(ribbon.getGenomes().size());
                for (StrandEdge strandEdge : strand.getEdges()) {
                    RibbonEdge edge = new RibbonEdge(strandEdge.getStart(), strandEdge.getEnd());
                    ribbon.addEdge(edge);
                }
                result.add(ribbon);


            }


        }


        return result;

    }


}
