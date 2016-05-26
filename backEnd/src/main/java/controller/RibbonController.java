package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Strand;
import genome.StrandEdge;
import genome.Genome;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Comparator;
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

        if (activeGenomes.size() < 4) {
            activeGenomes.add("TKK_02_0010.fasta");
            activeGenomes.add("TKK_02_0006.fasta");
            activeGenomes.add("TKK_02_0007.fasta");
            activeGenomes.add("TKK_02_0001.fasta");
        }


        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, activeGenomes, zoomLevel);

        int id=0;
        for (Strand strand : filteredNodes) {
            RibbonNode ribbon = new RibbonNode(id, strand.getGenomes());
            ribbon.setX(strand.getX());
            ribbon.setY(ribbon.getGenomes().size() * 10);
            for (StrandEdge strandEdge : strand.getEdges()) {
                RibbonEdge edge = new RibbonEdge(strandEdge.getStart(), strandEdge.getEnd());
                ribbon.addEdge(edge);
            }
            id++;
            result.add(ribbon);

        }


        result.sort(new Comparator<RibbonNode>() {
            @Override
            public int compare(RibbonNode o1, RibbonNode o2) {
                if (o1.getX() > o2.getX()) {
                    return 1;
                } else if (o1.getX() < o2.getX()) {
                    return -1;
                }
                return 0;
            }
        });


        return result;

    }


}
