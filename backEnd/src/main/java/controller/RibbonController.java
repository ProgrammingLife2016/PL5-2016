package controller;

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
     * @param minX          the minx of the view.
     * @param maxX          the maxx of the view.
     * @param zoomLevel     the zoomlevel of the view.
     * @param dataTree      the filled and processed tree of the data.
     * @param activeGenomes the genomes to filter for.
     * @param genomes       All the genomes in the data.
     * @return The list of ribbonNodes.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public static ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel,
                                                       DataTree dataTree, ArrayList<String> activeGenomes,
                                                       HashMap<String, Genome> genomes) {
        ArrayList<RibbonNode> result = new ArrayList<>();
        HashMap<Integer, RibbonNode> tempResult = new HashMap<>();
        //ArrayList<DataNode> filteredNodes = dataTree.getDataNodes(minX, maxX, activeGenomes, 100);

        String reference = "MT_H37RV_BRD_V5.ref.fasta";
        Genome referenceGenome = genomes.get(reference);
        int x = 0;
        for (Strand strand : referenceGenome.getStrands()) {
            RibbonNode ribbon = new RibbonNode(strand.getId(), strand.getGenomes());
            ribbon.setX(x);
            ribbon.setY(0);
            for (StrandEdge edge : strand.getEdges()) {
                ribbon.addEdge(new RibbonEdge(edge.getStart(), edge.getEnd()));
            }
            tempResult.put(ribbon.getId(), ribbon);
            result.add(ribbon);
            x += 10;
        }

        for (String reference2 : activeGenomes) {
            int y = 0;
            if (activeGenomes.indexOf(reference2) % 2 == 0) {
                y = (activeGenomes.indexOf(reference2) / 2 + 1) * 20;
            } else {
                y = (activeGenomes.indexOf(reference2) + 1) * -10;
            }
            if (genomes.containsKey(reference2)) {
                Genome genome = genomes.get(reference2);
                x = 0;
                for (int i = 0; i < genome.getStrands().size(); i++) {
                    Strand strand = genome.getStrands().get(i);
                    if (!tempResult.containsKey(strand.getId())) {
                        x += 10;
                        while (!tempResult.containsKey(strand.getId())) {
                            if (i + 1 == genome.getStrands().size()) {
                                break;
                            }
                            Strand temp = genome.getStrands().get(i + 1);
                            RibbonNode ribbon = new RibbonNode(strand.getId(), strand.getGenomes());
                            ribbon.setX(x);
                            ribbon.setY(y);
                            ribbon.addEdge(new RibbonEdge(ribbon.getId(), temp.getId()));
                            i++;
                            tempResult.put(ribbon.getId(), ribbon);
                            result.add(ribbon);
                            strand = genome.getStrands().get(i);
                        }

                    } else {
                        tempResult.get(strand.getId()).getEdges().get(0).incrementWeight();
                    }
                    if (tempResult.containsKey(strand.getId())) {
                        x = tempResult.get(strand.getId()).getX();
                    }
                }
            }
        }
        return result;

    }

    /**
     * Calculate and add the edges between the ribbon nodes.
     *
     * @param nodes   The ribbonnodes to connect.
     * @param genomes The active Genomes.
     */

    public void addRibbonEdges(ArrayList<RibbonNode> nodes, ArrayList<String> genomes) {
        nodes.sort(new Comparator<RibbonNode>() {
            @Override
            public int compare(RibbonNode o1, RibbonNode o2) {
                if (o1.getId() > o2.getId()) {
                    return 1;
                } else if (o1.getId() < o2.getId()) {
                    return -1;
                }
                return 0;
            }
        });
        for (String genome : genomes) {
            for (int i = 0; i < nodes.size(); i++) {
                RibbonNode startNode = nodes.get(i);

                if (startNode.getGenomes().contains(genome)) {
                    i++;
                    RibbonNode endNode = nodes.get(i);
                    while (!checkEdge(startNode, endNode, genome) && i < nodes.size()) {
                        i++;
                        endNode = nodes.get(i);
                    }
                }
            }
        }
    }


    /**
     * Check if an edge should exist between two ribbon nodes, and add it if it should.
     *
     * @param startNode The start node.
     * @param endNode   The end node.
     * @param genomeID  The Genome of this path.
     * @return True if edge was found.
     */

    public boolean checkEdge(RibbonNode startNode, RibbonNode endNode, String genomeID) {
        if (endNode.getGenomes().contains(genomeID)) {
            if (startNode.getEdge(startNode.getId(), endNode.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(startNode.getId(), endNode.getId());
                startNode.addEdge(edge);
                endNode.addEdge(edge);

            } else {
                startNode.getEdge(startNode.getId(), endNode.getId()).incrementWeight();
            }
            return true;

        }
        return false;
    }


}
