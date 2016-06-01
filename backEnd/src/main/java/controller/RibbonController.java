package controller;

import datatree.DataTree;
import genome.Strand;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;


import java.awt.Color;
import java.util.ArrayList;


/**
 * Class that calculates and returns the Ribbons and Edges to be drawn on the screen,
 * based on the data stored in genomeGraph and dataTree.
 */
public final class RibbonController {

	/**
	 * The graph that contains the geographic information of the stands.
	 */
    private GenomeGraph genomeGraph;
    /**
     * The tree that contains the phylogenetic information of the strands.
     */
    private DataTree dataTree;

    /**
     * Create ribbonController object.
     *
     * @param genomeGraph the graph that contains the geographic information of the stands.
     * @param dataTree    datatree that contains the phylogenetic information of the strands
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
        ArrayList<String> actGen = new ArrayList<>();
        //HARD CODED ACTIVE GENOMES.
        if (actGen.size() < 2) {
            actGen.add("TKK_02_0010.fasta");
            actGen.add("TKK_02_0025.fasta");



        }
        genomeGraph.setActiveGenomes(actGen);

        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, actGen, zoomLevel + 1);

        int id = 0;
        for (Strand strand : filteredNodes) {
            RibbonNode ribbon = new RibbonNode(id, strand.getGenomes());
            ribbon.setX(strand.getX());
            ribbon.addStrand(strand);

            id++;
            result.add(ribbon);


        }


        result.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(o2.getX()));
        calcYcoordinates(result);
        addEdges(result);


        return result;

    }

    /**
     * Collapses the ribbon Nodes and edges in nodes.
     *
     * @param nodes The ribbonNode Graph to collapse.
     * @return A collapsed graph.
     */
    public ArrayList<RibbonNode> collapseRibbons(ArrayList<RibbonNode> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);
            if (node != null) {
                if (node.getOutEdges().size() == 1) {
                    RibbonNode other = getNodeWithId(node.getOutEdges().get(0).getEnd(), nodes);
                    if (other.getInEdges().size() == 1) {
                        node.addStrands(other.getStrands());
                        for (RibbonEdge edge : other.getOutEdges()) {
                            edge.setStartId(node.getId());
                        }
                        node.setOutEdges(other.getOutEdges());
                        nodes.remove(other);
                    }
                }
            }
        }
        return nodes;


    }

    /**
     * Return a node with a certain id contained in a Ribbon Graph.
     *
     * @param id    The id to return for.
     * @param nodes The RibbonGraph.
     * @return null if that id is not found.
     */

    public RibbonNode getNodeWithId(int id, ArrayList<RibbonNode> nodes) {
        for (RibbonNode node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }


    /**
     * Calculate the Y coordinates for the nodes in a ribbonGraph.
     *
     * @param nodes The ribbonGraph to calculate y cooridnates for.
     * @return The ribbonGraph with added y coordinates.
     */
    public ArrayList<RibbonNode> calcYcoordinates(ArrayList<RibbonNode> nodes) {
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

        return nodes;
    }


    /**
     * Calculate and add edges to a ribbonGraph.
     *
     * @param nodes the RibbinGraph to calculate edges for.
     * @return The ribbonGraph with added edges.
     */
    public ArrayList<RibbonNode> addEdges(ArrayList<RibbonNode> nodes) {
        for (String genomeID : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genomeID, -1);
            while (currentNode != null) {
                currentNode = addEdgeReturnEnd(nodes, currentNode, genomeID);
            }

        }
        return nodes;


    }

    /**
     * Finds the next node that contains a certain genome.
     * Creates an edge between the two nodes and returns the end Node of the edge.
     *
     * @param nodes       The RibbonGraph.
     * @param currentNode The start node of the edge.
     * @param genomeID    The genome id to find an edge for.
     * @return The end node of the edge.
     */
    public RibbonNode addEdgeReturnEnd(ArrayList<RibbonNode> nodes, 
    		RibbonNode currentNode, String genomeID) {
        RibbonNode next = findNextNodeWithGenome(nodes, genomeID, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(currentNode.getId(), next.getId());
                edge.setColor(getColorForGenomeID(genomeID));
                currentNode.addEdge(edge);
                next.addEdge(edge);
            } else {
                currentNode.getOutEdge(currentNode.getId(), 
                		next.getId()).addGenomeToEdge(getColorForGenomeID(genomeID));
            }


        }
        return next;

    }

    /**
     * Finds the next node in a ribbongraph that contains a certain genome.
     *
     * @param nodes        The ribbonGraph to search through.
     * @param genome       The genome to find the next edge for.
     * @param currentIndex The current node index to start searching.
     * @return The next node that contains genome.
     */
    public RibbonNode findNextNodeWithGenome(ArrayList<RibbonNode> nodes, 
    		String genome, int currentIndex) {
        for (int i = currentIndex + 1; i < nodes.size(); i++) {
            if (nodes.get(i).getGenomes().contains(genome)) {
                return nodes.get(i);
            }
        }
        return null;

    }

    /**
     * Return the color associated with a genome.
     *
     * @param genomeID The genome to return the color for.
     * @return The color that is associated with this genomeid.
     */
    public Color getColorForGenomeID(String genomeID) {
        Color[] colors = {new Color(0, 0, 255),
                new Color(0, 255, 0),
                new Color(255, 0, 0),
                new Color(0, 255, 255),
                new Color(255, 0, 255),
                new Color(255, 255, 0),
                new Color(0, 0, 128),
                new Color(0, 128, 0),
                new Color(128, 0, 0),
                new Color(128, 128, 128)};

        return colors[genomeGraph.getActiveGenomes().indexOf(genomeID)];

    }


}
