package controller;

import datatree.DataTree;
import genome.Genome;
import genome.Strand;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;




import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class that calculates and returns the Ribbons and Edges to be drawn on the screen,
 * based on the data stored in genomeGraph and dataTree.
 */
public final class RibbonController {

    private GenomeGraph genomeGraph; //The graph that contains the geographic information of the stands.
    private DataTree dataTree; //The tree that contains the phylogenetic information of the strands.

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
        ArrayList<Genome> actGen = genomeGraph.getActiveGenomes();


        //HARD CODED ACTIVE GENOMES.
        if (actGen.size() < 2) {
            actGen.add(genomeGraph.getGenomes().get("TKK_02_0010.fasta"));
            actGen.add(genomeGraph.getGenomes().get("TKK_02_0006.fasta"));
            actGen.add(genomeGraph.getGenomes().get("TKK_02_0025.fasta"));
            actGen.add(genomeGraph.getGenomes().get("TKK_02_0005.fasta"));
            actGen.add(genomeGraph.getGenomes().get("TKK_02_0008.fasta"));
            actGen.add(genomeGraph.getGenomes().get("TKK_02_0004.fasta"));


        }


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
        for (Genome genome : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genome, -1);
            while (currentNode != null) {
                currentNode = addEdgeReturnEnd(nodes, currentNode, genome);
            }

        }
        return nodes;


    }

    /**
     * Finds the next node that contains a certain genome, creates an edge between the two nodes and returns the end Node of the edge.
     *
     * @param nodes       The RibbonGraph.
     * @param currentNode The start node of the edge.
     * @param genomeID    The genome id to find an edge for.
     * @return The end node of the edge.
     */
    public RibbonNode addEdgeReturnEnd(ArrayList<RibbonNode> nodes, RibbonNode currentNode, Genome genome) {
        RibbonNode next = findNextNodeWithGenome(nodes, genome, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(currentNode.getId(), next.getId());
                edge.setColor(getColorForGenomeID(genome));
                currentNode.addEdge(edge);
                next.addEdge(edge);
            } else {
                currentNode.getOutEdge(currentNode.getId(), next.getId()).addGenomeToEdge(getColorForGenomeID(genome));
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
    public RibbonNode findNextNodeWithGenome(ArrayList<RibbonNode> nodes, Genome genome, int currentIndex) {
        for (int i = currentIndex + 1; i < nodes.size(); i++) {
            if (nodes.get(i).getGenomes().contains(genome.getId())) {
                return nodes.get(i);
            }
        }
        return null;

    }

    /**
     * Return the color associated with a genome.
     *
     * @param GenomeID The genome to return the color for.
     * @return The color that is associated with this genomeid.
     */
    public Color getColorForGenomeID(Genome genome) {
    	HashMap<String,Color> colorMap =  new HashMap<String, Color>();

    	colorMap.put("LIN 1",Color.decode("0xed00c3"));	 
    	colorMap.put("LIN 2",Color.decode("0x0000ff"));	 
    	colorMap.put("LIN 3",Color.decode("0x500079"));	 
    	colorMap.put("LIN 4",Color.decode("0xff0000"));	 
    	colorMap.put("LIN 5",Color.decode("0x4e2c00"));	 
    	colorMap.put("LIN 6",Color.decode("0x69ca00"));	 
    	colorMap.put("LIN 7",Color.decode("0xff7e00"));	 
    	colorMap.put("LIN animal", Color.decode("0x00ff9c"));	 
    	colorMap.put("LIN B", Color.decode("0x00ff9c"));	 
    	colorMap.put("LIN CANETTII", Color.decode("0x00ffff"));
    	

        return colorMap.get(genome.getMetadata().getLineage());

    }


}
