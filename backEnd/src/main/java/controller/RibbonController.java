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

        System.out.println(minX + ", " + maxX);
        ArrayList<String> activeGenomes = genomeGraph.getActiveGenomes();

        if (activeGenomes.size() < 8) {
            activeGenomes.add("TKK_02_0010.fasta");
            activeGenomes.add("TKK_02_0006.fasta");
            activeGenomes.add("TKK_02_0007.fasta");
            activeGenomes.add("TKK_02_0001.fasta");
            activeGenomes.add("TKK_02_0008.fasta");
            activeGenomes.add("TKK_02_0010.fasta");
            activeGenomes.add("TKK_02_0018.fasta");
            activeGenomes.add("TKK_02_0025.fasta");

        }


        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, activeGenomes, 1);

        int id = 0;
        for (Strand strand : filteredNodes) {
            RibbonNode ribbon = new RibbonNode(id, strand.getGenomes());
            ribbon.setX(strand.getX());

            id++;
            result.add(ribbon);

        }
        result.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(new Integer(o2.getX())));
        calcYcoordinates(result);
        addEdges(result,activeGenomes);


        return result;

    }

    public static void calcYcoordinates(ArrayList<RibbonNode> nodes) {
        int currentX = 0;
        ArrayList<RibbonNode> currentXNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);
            if (node.getX() == currentX) {
                currentXNodes.add(node);
            } else {
                if (currentXNodes.size() > 1) {
                    for (int j = 0; j < currentXNodes.size(); j++) {
                        int minY = (currentXNodes.size() / 2) * -10;
                        currentXNodes.get(j).setY(minY + 10 * j);
                    }
                }
                currentXNodes = new ArrayList<>();
                currentXNodes.add(node);
                currentX = node.getX();
            }


        }

    }


    public static void addEdges(ArrayList<RibbonNode> nodes, ArrayList<String> activeGenomes) {
        for (String genome : activeGenomes) {
            int currentIndex = 0;

            while (findNextNodeWithGenome(nodes, genome, currentIndex) != null) {
                RibbonNode currentNode = findNextNodeWithGenome(nodes, genome, currentIndex);
                currentIndex = nodes.indexOf(currentNode);
                RibbonNode nextNode = findNextNodeWithGenome(nodes, genome, currentIndex);
                if (nextNode != null) {
                    if (currentNode.getOutEdge(currentNode.getId(), nextNode.getId()) == null) {
                        RibbonEdge edge = new RibbonEdge(currentNode.getId(), nextNode.getId());
                        currentNode.addEdge(edge);
                    } else {
                        currentNode.getOutEdge(currentNode.getId(), nextNode.getId()).incrementWeight();
                    }
                }
                currentIndex = nodes.indexOf(nextNode);

            }


        }


    }

    public static RibbonNode findNextNodeWithGenome(ArrayList<RibbonNode> nodes, String genome, int currentIndex) {
        for (int i = currentIndex+1; i < nodes.size(); i++) {
            if (nodes.get(i).getGenomes().contains(genome)) {
                return nodes.get(i);
            }
        }
        return null;

    }


}
