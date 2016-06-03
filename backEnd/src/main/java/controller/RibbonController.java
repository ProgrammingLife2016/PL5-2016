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

    /**
     * The graph that contains the geographic information of the stands.
     */
    private GenomeGraph genomeGraph;

    /**
     * The tree that contains the phylogenetic information of the strands.
     */
    private DataTree dataTree;

    /**
     * The color map used to color the genomes.
     */
    private HashMap<String, Color> colorMap;

    /**
     * The maximal amount of strands to return.
     */
    private int maxStrandsToReturn = 0;

    /**
     * Create ribbonController object.
     *
     * @param genomeGraph the graph that contains the geographic information of the stands.
     * @param dataTree    datatree that contains the phylogenetic information of the strands
     */
    public RibbonController(GenomeGraph genomeGraph, DataTree dataTree) {
        this.genomeGraph = genomeGraph;
        this.dataTree = dataTree;
        colorMap = constructColorMap();
    }

    /**
     * Construct color map.
     *
     * @return the hash map
     */
    private HashMap<String, Color> constructColorMap() {
        HashMap<String, Color> map = new HashMap<String, Color>();
        map.put("LIN 1", Color.decode("0xed00c3"));
        map.put("LIN 2", Color.decode("0x0000ff"));
        map.put("LIN 3", Color.decode("0x500079"));
        map.put("LIN 4", Color.decode("0xff0000"));
        map.put("LIN 5", Color.decode("0x4e2c00"));
        map.put("LIN 6", Color.decode("0x69ca00"));
        map.put("LIN 7", Color.decode("0xff7e00"));
        map.put("LIN animal", Color.decode("0x00ff9c"));
        map.put("LIN B", Color.decode("0x00ff9c"));
        map.put("LIN CANETTII", Color.decode("0x00ffff"));
        return map;
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
        addEdges(result);
        spreadYCoordinates(result);
        collapseRibbons(result, Math.max(0, 5 - zoomLevel));

        if(zoomLevel<10){
            addMutationLabels(result);
        }

        System.out.println(result.size() + " nodes returned");
        return result;

    }

    /**
     * Collapses the ribbon Nodes and edges in nodes.
     *
     * @param nodes The ribbonNode Graph to collapse.
     * @return A collapsed graph.
     */
    private void collapseRibbons(ArrayList<RibbonNode> nodes, int iterations) {
        int nIter = 0;
        int changedThisPass = 100000;
        while (nIter < iterations && nodes.size() > maxStrandsToReturn && changedThisPass > maxStrandsToReturn / 2) {
            nIter++;
            changedThisPass = 0;
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
                            changedThisPass++;
                        }
                    }
                }
            }
        }

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
    private void spreadYCoordinates(ArrayList<RibbonNode> nodes) {
        int currentX = 0;
        ArrayList<RibbonNode> currentXNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);
            if (node.getX() < currentX + 1000 && node.getX() > currentX - 1000) {
                currentXNodes.add(node);
            } else {
                if (currentXNodes.size() > 1) {
                    for (int j = 0; j < currentXNodes.size(); j++) {
                        currentXNodes.get(j).setY(currentXNodes.get(j).getY() - 10 * (genomeGraph.getActiveGenomes().size() - 1));
                        currentXNodes.get(j).setY((int) (currentXNodes.get(j).getY() * (Math.pow(-1, j))));
                    }
                }
                currentXNodes = new ArrayList<>();
                currentXNodes.add(node);
                currentX = node.getX();
            }


        }


    }


    /**
     * Calculate and add edges to a ribbonGraph.
     *
     * @param nodes the RibbinGraph to calculate edges for.
     * @return The ribbonGraph with added edges.
     */

    public void addEdges(ArrayList<RibbonNode> nodes) {
        for (Genome genome : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genome, -1);
            while (currentNode != null) {
                currentNode = addEdgeReturnEnd(nodes, currentNode, genome);
            }

        }


    }

    /**
     * Finds the next node that contains a certain genome.
     * Creates an edge between the two nodes and returns the end Node of the edge.
     *
     * @param nodes       The RibbonGraph.
     * @param currentNode The start node of the edge.
     * @param genome      The genome to find an edge for.
     * @return The end node of the edge.
     */
    public RibbonNode addEdgeReturnEnd(ArrayList<RibbonNode> nodes,
                                       RibbonNode currentNode, Genome genome) {
        RibbonNode next = findNextNodeWithGenome(nodes, genome, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(currentNode.getId(), next.getId());
                edge.setColor(getColorForGenome(genome));
                currentNode.addEdge(edge);
                next.addEdge(edge);
            } else {
                next.setY(next.getY() + 10);
                RibbonEdge edge = currentNode.getOutEdge(currentNode.getId(), next.getId());
                edge.addGenomeToEdge(getColorForGenome(genome));
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
                                             Genome genome, int currentIndex) {
        for (int i = currentIndex + 1; i < nodes.size(); i++) {
            if (nodes.get(i).getGenomes().contains(genome.getId())) {
                return nodes.get(i);
            }
        }
        return null;

    }

    /**
     * Gets the color for the genome as specified by http://www.abeel.be/wiki/Lineage_colors.
     * Because genomes with an identifier starting with G are not specified in the metadata
     * they get treated as a special case in this method.
     *
     * @param genome the genome
     * @return the color for the genome
     */
    public Color getColorForGenome(Genome genome) {
        Color result;
        if (genome.hasMetadata()) {
            result = colorMap.get(genome.getMetadata().getLineage());
        } else {
            if (genome.getId().startsWith("G")) {
                result = Color.decode("0xff0000");
            } else {
                result = new Color(100, 100, 100);
            }
        }
        return result;
    }

    /**
     * Set the maximal amount of strands to return.
     *
     * @param maxStrandsToReturn
     */
    public void setMaxStrandsToReturn(int maxStrandsToReturn) {
        this.maxStrandsToReturn = maxStrandsToReturn;
    }

    public void addMutationLabels(ArrayList<RibbonNode> nodes) {
        for (RibbonNode node : nodes) {
            Strand strand = node.getStrands().get(0);
            if (strand.getMutations().size() > 0) {
                System.out.println("Mutation added");
                node.setLabel(strand.getMutations().get(0).toString());
            }
        }
    }
}
