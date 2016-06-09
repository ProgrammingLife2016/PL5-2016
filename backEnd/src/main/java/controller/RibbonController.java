package controller;

import datatree.DataTree;
import genome.Genome;
import genome.Strand;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonEdgeFactory;
import ribbonnodes.RibbonNode;
import ribbonnodes.RibbonNodeFactory;

import java.util.ArrayList;


/**
 * Class that calculates and returns the Ribbons and Edges to be drawn on the screen,
 * based on the data stored in genomeGraph and dataTree.
 */
public final class RibbonController {

    /**
     * The graph that contains the geographic information of the stands.
     */
    private static GenomeGraph genomeGraph;

    /**
     * The tree that contains the phylogenetic information of the strands.
     */
    private static DataTree dataTree;

    /**
     * The current maximum ID in the ribbonnode Graph.
     */
    private int maxId;


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
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {

        System.out.println(minX + ", " + maxX);
        ArrayList<Genome> actGen = genomeGraph.getActiveGenomes();

        ArrayList<String> actIds = new ArrayList<>();
        for (Genome genome : actGen) {
            actIds.add(genome.getId());
        }

        maxId = 0;
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, actGen, zoomLevel + 1);
        ArrayList<RibbonNode> result = createNodesFromStrands(filteredNodes, actIds, zoomLevel);
        spreadYCoordinates(result, actIds);
        addEdges(result);
        //   collapseRibbons(result);

        System.out.println(result.size() + " nodes returned");
        return result;

    }

    /**
     * Create RibbonNodes from a list of strands.
     *
     * @param filteredNodes The filtered strand list.
     * @param actIds        The active Genome Ids.
     * @param zoomLevel     The current Zoomlevel.
     * @return The created List of RibbonNodes.
     */
    protected ArrayList<RibbonNode> createNodesFromStrands(ArrayList<Strand> filteredNodes,
                                                           ArrayList<String> actIds,
                                                           int zoomLevel) {
        ArrayList<RibbonNode> result = new ArrayList<>();


        for (Strand strand : filteredNodes) {
            if (strand.getSequence().length() > 100 - zoomLevel * 8) {
                RibbonNode ribbon = RibbonNodeFactory.makeRibbonNodeFromStrand(
                        maxId++,
                        strand,
                        actIds);
                result.add(ribbon);
            }
        }

        return result;
    }

    /**
     * Collapses the ribbon Nodes and edges in nodes.
     *
     * @param nodes The ribbonNode Graph to collapse.
     */
    protected void collapseRibbons(ArrayList<RibbonNode> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);
            System.out.println(nodes.size() + " Before collapsing");
            if (node != null) {
                while (node.getOutEdges().size() == 1) {
                    RibbonNode other = getNodeWithId(node.getOutEdges().get(0).getEnd(), nodes, i);
                    if (other.getInEdges().size() == 1) {
                        node = RibbonNodeFactory.collapseNodes(node, other);
                        nodes.remove(other);
                    } else {
                        break;
                    }


                }
            }

        }


    }


    /**
     * Return a node with a certain id contained in a Ribbon Graph.
     *
     * @param id       The id to return for.
     * @param nodes    The RibbonGraph.
     * @param minIndex The minimal index to start looking (for speedup)
     * @return null if that id is not found.
     */

    protected RibbonNode getNodeWithId(int id, ArrayList<RibbonNode> nodes, int minIndex) {
        for (int i = minIndex; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }


    /**
     * Place all the nodes in common in the middle, and copy and distribute
     * all others along the y coordinate associated with that genome.
     *
     * @param nodes         The nodes to calculate the Y for.
     * @param activeGenomes The active genomes to spread out.
     */
    protected void spreadYCoordinates(ArrayList<RibbonNode> nodes, ArrayList<String> activeGenomes) {
        ArrayList<RibbonNode> splitNodes = new ArrayList<>();
        ArrayList<RibbonNode> newNodes = new ArrayList<>();
        for (RibbonNode node : nodes) {
            if (node.getGenomes().size() != activeGenomes.size()) {
                ArrayList<RibbonNode> ribbonSplitCopies =
                        RibbonNodeFactory.makeRibbonNodesFromSplit(node, maxId);
                for (RibbonNode splitNode : ribbonSplitCopies) {
                    int genIndex = activeGenomes.indexOf(splitNode.getGenomes().get(0));
                    int newY = (int) ((Math.ceil((genIndex + 1) / 2.) * 20)
                            * Math.pow(-1, genIndex));
                    splitNode.setY(newY);
                }
                maxId += ribbonSplitCopies.size();
                splitNodes.add(node);
                newNodes.addAll(ribbonSplitCopies);
            }
        }
        nodes.removeAll(splitNodes);
        nodes.addAll(newNodes);


    }


    /**
     * Calculate and add edges to a ribbonGraph.
     *
     * @param nodes the RibbinGraph to calculate edges for.
     */

    protected void addEdges(ArrayList<RibbonNode> nodes) {
        nodes.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(o2.getX()));
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
    protected RibbonNode addEdgeReturnEnd(ArrayList<RibbonNode> nodes,
                                          RibbonNode currentNode, Genome genome) {
        RibbonNode next = findNextNodeWithGenome(nodes, genome, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(
                        currentNode.getId(),
                        next.getId(),
                        genome);
                currentNode.addEdge(edge);
                next.addEdge(edge);
            } else {
                RibbonEdge edge = currentNode.getOutEdge(currentNode.getId(), next.getId());
                //temp fix for color visibilty.
                RibbonEdge colorEdge = RibbonEdgeFactory.createRibbonEdge(
                        currentNode.getId(),
                        next.getId(),
                        genome);
                edge.addGenomeToEdge(colorEdge.getColor());
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
    protected RibbonNode findNextNodeWithGenome(ArrayList<RibbonNode> nodes,
                                                Genome genome, int currentIndex) {
        for (int i = currentIndex + 1; i < nodes.size(); i++) {
            if (nodes.get(i).getGenomes().contains(genome.getId())) {
                return nodes.get(i);
            }
        }
        return null;

    }


    /**
     * Add mutationLabels to nodes.
     *
     * @param nodes The nodes to add labels to.
     */
    protected void addMutationLabels(ArrayList<RibbonNode> nodes) {
        for (RibbonNode node : nodes) {
            Strand strand = node.getStrands().get(0);
            if (strand.getMutations().size() > 0) {
                System.out.println("Mutation added");
                node.setLabel(strand.getMutations().get(0).toString());
            }
        }
    }
}
