package controller;

import datatree.DataTree;
import genome.Genome;
import genome.Strand;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;


/**
 * Created by Matthijs on 18-5-2016.
 */
public final class RibbonController {

    private GenomeGraph genomeGraph;
    private DataTree dataTree;

    /**
     * Create ribbonController object.
     * This should not be possible, throw an exception.
     */
    public RibbonController(GenomeGraph genomeGraph, DataTree dataTree) {
        this.genomeGraph = genomeGraph;
        this.dataTree = dataTree;
    }

    /**
     * Get the ribbon nodes with edges for a certain view in the GUI.
     *
     * @param minX      the minx of the view.
     * @param maxX      the maxx of the view.
     * @param zoomLevel the zoomlevel of the view.
     * @return The list of ribbonNodes.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {

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
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, activeGenomes, zoomLevel + 1);

        int id = 0;
        for (Strand strand : filteredNodes) {
            RibbonNode ribbon = new RibbonNode(id, strand.getGenomes());
            ribbon.setX(strand.getX());

            id++;
            result.add(ribbon);

        }
        result.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(o2.getX()));
        calcYcoordinates(result);
        addEdges(result);


        return result;

    }

    public void calcYcoordinates(ArrayList<RibbonNode> nodes) {
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
                        currentXNodes.get(j).setY(minY + 15 * j);
                    }
                }
                currentXNodes = new ArrayList<>();
                currentXNodes.add(node);
                currentX = node.getX();
            }


        }

    }


    public void addEdges(ArrayList<RibbonNode> nodes) {
        for (String genomeID : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genomeID, -1);
            while (currentNode!=null){
                currentNode=addEdgeReturnNext(nodes,currentNode,genomeID);
            }

        }


    }

    public RibbonNode addEdgeReturnNext(ArrayList<RibbonNode> nodes, RibbonNode currentNode, String genomeID) {
        RibbonNode next = findNextNodeWithGenome(nodes, genomeID, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                currentNode.addEdge(new RibbonEdge(currentNode.getId(), next.getId()));
            } else {
                currentNode.getOutEdge(currentNode.getId(), next.getId()).incrementWeight();
            }
        }
        return next;

    }

    public RibbonNode findNextNodeWithGenome(ArrayList<RibbonNode> nodes, String genome, int currentIndex) {
        for (int i = currentIndex + 1; i < nodes.size(); i++) {
            if (nodes.get(i).getGenomes().contains(genome)) {
                return nodes.get(i);
            }
        }
        return null;

    }


}
