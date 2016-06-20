package ribbonnodes;

import datatree.DataTree;
import genome.Genome;
import genome.GenomeGraph;
import mutation.Mutations;
import strand.Strand;

import java.util.ArrayList;

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
     * With this object the nodes are selected that will be shown on the screen.
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

        if (isMiniMap) {
            zoomLevel = 3;
        }
        ArrayList<String> actIds = new ArrayList<>();
        for (ArrayList<Genome> genome : actGen) {
            actIds.add(genome.get(0).getId());
        }

        maxId = 0;
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, actGen, zoomLevel);
        ArrayList<RibbonNode> result = createNodesFromStrands(filteredNodes, actIds);
        addEdges(result);
        collapseRibbons(result, minX, maxX);
        
        if (isMiniMap && result.size() < 100) {
            return getRibbonNodes(minX, maxX, 20, false);
        }
        
        spreadYCoordinates(result, actIds);
        if (!isMiniMap) {
            Mutations mutations = new Mutations(result, dataTree);
            mutations.computeAllMutations();
        }
       
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
     * @param minX  The minimal x value to collapse.
     * @param maxX  The maximal x value to collapse.
     */
    @SuppressWarnings("checkstyle:methodlength")
    protected void collapseRibbons(ArrayList<RibbonNode> nodes, int minX, int maxX) {
        System.out.println(nodes.size() + " Before collapsing");

        ArrayList<RibbonNode> newNodes = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);

            if (node != null && node.getX() > minX && node.getX() < maxX) {
                ArrayList<RibbonNode> nodesToCollapse = new ArrayList<>();
                nodesToCollapse.add(node);
                while (node.getOutEdges().size() == 1) {
                    RibbonNode other = node.getOutEdges().get(0).getEnd();
                    if (other != null && other.getX() > minX && other.getX() < maxX) {
                        if (other.getInEdges().size() == 1) {
                            nodesToCollapse.add(other);
                            node = other;
                            nodes.remove(other);
                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                RibbonNode newNode = RibbonNodeFactory.collapseNodes(nodesToCollapse);

                if (newNode != null) {
                    newNodes.add(newNode);

                }
            }
        }
        nodes.addAll(newNodes);
        System.out.println(nodes.size() + " After collapsing");
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
        int level = 0;
        while (activeGenomes.size() != level) {
            for (RibbonNode node : nodes) {
                if (node.getGenomes().size() == activeGenomes.size() - level) {
                    if (!node.isyFixed()) {
                        node.setY(findPreviousNodeSameGenomes(nodes, node).getY());
                        node.setyFixed(true);
                    }
                    spreadYCoordinates(node, activeGenomes, level);
                }
            }
            level++;
        }
    }

    /**
     * Find the closest node to the left that has the same genomes.
     *
     * @param nodes The RibbonNode graph.
     * @param node  Node to find the closest for.
     * @return The first node to the left, or same node if not found.
     */
    protected RibbonNode findPreviousNodeSameGenomes(ArrayList<RibbonNode> nodes,
                                                     RibbonNode node) {
        int endIndex = nodes.indexOf(node);
        RibbonNode result = node;
        for (int i = 0; i < endIndex; i++) {
            RibbonNode other = nodes.get(i);
            if (other.getGenomes().size() == node.getGenomes().size()
                    && other.getGenomes().containsAll(node.getGenomes())) {
                result = other;
            }
        }
        return result;
    }

    /**
     * Place all the nodes in common in the middle, and copy and distribute
     * all others along the y coordinate associated with that genome.
     *
     * @param node          The node to calculate the Y for.
     * @param activeGenomes The ids of the active genomes.
     * @param level         the zoomlevel.
     */
    protected void spreadYCoordinates(RibbonNode node,
                                      ArrayList<String> activeGenomes,
                                      int level) {
        ArrayList<RibbonNode> nextNodes = new ArrayList<>();
        if (level == 0) {
            node.setY(0);
            node.setyFixed(true);
        }
        node.getOutEdges().sort((RibbonEdge o1, RibbonEdge o2) ->
                new Integer(o2.getWeight()).compareTo(o1.getWeight()));

        for (int i = 0; i < node.getOutEdges().size(); i++) {
            RibbonEdge edge = node.getOutEdges().get(i);
            RibbonNode endNode = edge.getEnd();
            if (endNode.getGenomes().size() <= node.getGenomes().size() && !endNode.isyFixed()) {
                int exponent = i;
                int newY = (int) ((node.getGenomes().size() - endNode.getGenomes().size())
                        * 5 * (activeGenomes.size() - level)
                        * Math.pow(-1, exponent));
                endNode.setyFixed(true);
                endNode.setY(node.getY() + newY);
                nextNodes.add(endNode);
            }
        }
    }

    /**
     * Calculate and add edges to a ribbonGraph.
     *
     * @param nodes     the RibbinGraph to calculate edges for.
     */
    protected void addEdges(ArrayList<RibbonNode> nodes) {
        nodes.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(o2.getX()));
        for (ArrayList<Genome> genome : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genome.get(0), -1);
            while (currentNode != null) {
                currentNode = addEdgeReturnEnd(nodes, currentNode, genome.get(0));
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
                                          RibbonNode currentNode,
                                          Genome genome) {
        RibbonNode next = findNextNodeWithGenome(nodes, genome, nodes.indexOf(currentNode));
        if (next != null) {
            if (currentNode.getOutEdge(currentNode.getId(), next.getId()) == null) {
                RibbonEdge edge = RibbonEdgeFactory.createRibbonEdge(
                        currentNode,
                        next,
                        genome);
                currentNode.addEdge(edge);
                next.addEdge(edge);

            } else {

                RibbonEdge edge = currentNode.getOutEdge(currentNode.getId(), next.getId());
                RibbonEdge colorEdge = RibbonEdgeFactory.createRibbonEdge(
                        currentNode, next, genome);
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
}
