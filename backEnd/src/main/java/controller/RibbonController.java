package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Strand;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by Matthijs on 18-5-2016.
 */
public final class RibbonController {


    /**
     * Get the ribbon nodes with edges for a certain view in the GUI.
     *
     * @param minX          the minx of the view.
     * @param maxX          the maxx of the view.
     * @param zoomLevel     the zoomlevel of the view.
     * @param dataTree      the filled and processed tree of the data.
     * @param activeGenomes the genomes to filter for.
     * @return The list of ribbonNodes.
     */
    public static ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel, DataTree dataTree, ArrayList<String> activeGenomes) {
        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<DataNode> filteredNodes =
                dataTree.getDataNodes(minX, maxX, activeGenomes, zoomLevel);

        for (DataNode node : filteredNodes) {
            for (Strand strand : node.getStrands()) {
                //Here the nodes are placed in order
                //(notice node.getgenomes and not ribbon.getgenomes).
                RibbonNode ribbonNode = new RibbonNode(strand.getId(), new ArrayList<>(Arrays.asList(strand.getGenomes())));
                ribbonNode.setY(strand.getWeight() * 10);
                result.add(ribbonNode);
            }
        }

        addRibbonEdges(result, activeGenomes);

        return result;

    }

    /**
     * Calculate and add the edges between the ribbon nodes.
     *
     * @param nodes   The ribbonnodes to connect.
     * @param genomes The active Genomes.
     */

    public static void addRibbonEdges(ArrayList<RibbonNode> nodes, ArrayList<String> genomes) {
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
            System.out.println(genome);
            for (int i = 0; i < nodes.size(); i++) {
                RibbonNode startNode = nodes.get(i);

                if (startNode.getGenomes().contains(genome) && i + 1 < nodes.size()) {
                    int j = i + 1;
                    RibbonNode endNode = nodes.get(j);
                    while (!checkEdge(startNode, endNode, genome) && j < nodes.size()) {
                        j++;
                        endNode = nodes.get(j);
                    }
                    i = j-1;
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

    public static boolean checkEdge(RibbonNode startNode, RibbonNode endNode, String genomeID) {
        if (endNode.getGenomes().contains(genomeID)) {
            if (startNode.getEdge(startNode.getId(), endNode.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(startNode.getId(), endNode.getId());
                startNode.addEdge(edge);

            } else {
                startNode.getEdge(startNode.getId(), endNode.getId()).incrementWeight();
            }
            return true;

        }
        return false;
    }
}
