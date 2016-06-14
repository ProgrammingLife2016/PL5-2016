package ribbonnodes;

import datatree.DataTree;
import genome.Genome;
import genome.GenomeGraph;
import genome.Strand;
import mutation.AbstractMutation;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonEdgeFactory;
import ribbonnodes.RibbonNode;
import ribbonnodes.RibbonNodeFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;


/**
 * Class that calculates and returns the Ribbons and Edges to be drawn on the screen,
 * based on the data stored in genomeGraph and dataTree.
 */
public class RibbonController {

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
     * @param isMiniMap Boolean if this is the minimap.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, 
    		int zoomLevel, boolean isMiniMap) {


        ArrayList<ArrayList<Genome>> actGen = genomeGraph.getActiveGenomes();

        ArrayList<String> actIds = new ArrayList<>();
        for (ArrayList<Genome> genome : actGen) {
            actIds.add(genome.get(0).getId());
        }

        maxId = 0;
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, actGen, zoomLevel + 1);
        ArrayList<RibbonNode> result = createNodesFromStrands(filteredNodes, actIds);
        spreadYCoordinates(result, actIds);
        addEdges(result, isMiniMap);


        collapseRibbons(result, zoomLevel);


        addMutationLabels(result, actIds);
        System.out.println(result.size() + " nodes returned");
        return result;

    }

    /**
     * Create RibbonNodes from a list of strands.
     *
     * @param filteredNodes The filtered strand list.
     * @param actIds        The active Genome Ids.
     * @return The created List of RibbonNodes.
     */
    protected ArrayList<RibbonNode> createNodesFromStrands(ArrayList<Strand> filteredNodes,
                                                           ArrayList<String> actIds) {
        ArrayList<RibbonNode> result = new ArrayList<>();


        for (Strand strand : filteredNodes) {
            RibbonNode ribbon = RibbonNodeFactory.makeRibbonNodeFromStrand(
                    maxId++,
                    strand,
                    actIds);
            result.add(ribbon);

        }

        return result;
    }

    /**
     * Collapses the ribbon Nodes and edges in nodes.
     *
     * @param nodes The ribbonNode Graph to collapse.
     * @param zoomLevel The zoomlevel.
     */
    @SuppressWarnings("checkstyle:methodlength")
    protected void collapseRibbons(ArrayList<RibbonNode> nodes, int zoomLevel) {
        System.out.println(nodes.size() + " Before collapsing");

        HashMap<Integer, RibbonNode> ribbonHash = new HashMap<>();
        for (RibbonNode node : nodes) {
            ribbonHash.put(node.getId(), node);
        }

        for (int id : ribbonHash.keySet()) {
            RibbonNode node = ribbonHash.get(id);
            if (node != null) {
                ArrayList<RibbonNode> nodesToCollapse = new ArrayList<>();
                nodesToCollapse.add(node);
                int length = node.getLabel().length();
                while (node.getOutEdges().size() == 1 && length < 10000 / zoomLevel) {
                    RibbonNode other = ribbonHash.get(node.getOutEdges().get(0).getEnd());
                    if (other != null) {
                        if (other.getInEdges().size() == 1) {
                            nodesToCollapse.add(other);
                            node = other;
                            length += node.getLabel().length();
                            ribbonHash.put(other.getId(), null);
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }


                }

                RibbonNode newNode = RibbonNodeFactory.collapseNodes(nodesToCollapse);
                if (newNode != null) {
                    ribbonHash.put(newNode.getId(), newNode);
                }
            }
        }
        nodes.clear();
        for (RibbonNode node : ribbonHash.values()) {
            if (node != null) {
                nodes.add(node);
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
    protected void spreadYCoordinates(ArrayList<RibbonNode> nodes,
                                      ArrayList<String> activeGenomes) {
        ArrayList<RibbonNode> splitNodes = new ArrayList<>();
        ArrayList<RibbonNode> newNodes = new ArrayList<>();
        for (RibbonNode node : nodes) {
            if (node.getGenomes().size() != activeGenomes.size()) {
                ArrayList<RibbonNode> ribbonSplitCopies =
                        RibbonNodeFactory.makeRibbonNodesFromSplit(node, maxId);
                for (RibbonNode splitNode : ribbonSplitCopies) {
                    int genIndex = activeGenomes.indexOf(splitNode.getGenomes().iterator().next());
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
     * @param isMiniMap Boolean if this is the minimap.
     */

    protected void addEdges(ArrayList<RibbonNode> nodes, boolean isMiniMap) {
        nodes.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(o2.getX()));
        for (ArrayList<Genome> genome : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genome.get(0), -1);
            while (currentNode != null) {
                currentNode = addEdgeSetXReturnEnd(nodes, currentNode, genome.get(0), isMiniMap);
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
     * @param isMiniMap Boolean if this is the minimap.
     * @return The end node of the edge.
     */
    protected RibbonNode addEdgeSetXReturnEnd(ArrayList<RibbonNode> nodes,
                               RibbonNode currentNode, Genome genome, boolean isMiniMap) {
        RibbonNode next = findNextNodeWithGenome(nodes, genome, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(
                        currentNode.getId(),
                        next.getId(),
                        genome);
                currentNode.addEdge(edge);
                next.addEdge(edge);
                if (!isMiniMap) {
                    if (next.getInEdges().isEmpty()) {
                        next.setX(currentNode.getX() + currentNode.getLabel().length());
                    } else if (next.getX() < currentNode.getX() + currentNode.getLabel().length()) {
                        next.setX(currentNode.getX() + currentNode.getLabel().length());
                    }
                }
            } else {
                if (!isMiniMap && next.getX() 
                		< currentNode.getX() + currentNode.getLabel().length()) {
                    next.setX(currentNode.getX() + currentNode.getLabel().length());
                }
                RibbonEdge edge = currentNode.getOutEdge(currentNode.getId(), next.getId());
                RibbonEdge colorEdge = RibbonEdgeFactory.createRibbonEdge(
                        currentNode.getId(), next.getId(), genome);
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
     * Adds the mutations to the labels.
     *
     * @param nodes      The nodes in the graph.
     * @param actGenomes The active genomes.
     */
    protected void addMutationLabels(ArrayList<RibbonNode> nodes, ArrayList<String> actGenomes) {
        for (RibbonNode node : nodes) {
            if (!node.getStrands().isEmpty()) {
                boolean mutationAdded = false;
                Strand strand = node.getStrands().get(node.getStrands().size() - 1);
                StringBuilder label = new StringBuilder();
                label.append(System.lineSeparator());
                for (AbstractMutation mutation : strand.getMutations()) {
                    if (!Collections.disjoint(mutation.getReferenceGenomes(), actGenomes)
                            && !Collections.disjoint(mutation.getOtherGenomes(), actGenomes)) {
                        label.append(mutation.toString());
                        label.append(", ");
                        mutationAdded = true;
                    }
                }
                if (mutationAdded) {
                    label.setLength(label.length() - 2);
                    node.setLabel(node.getLabel() + label.toString());
                }
            }
        }
    }
}
