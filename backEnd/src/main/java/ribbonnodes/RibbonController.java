package ribbonnodes;

import datatree.DataTree;
import genome.Genome;
import genome.GenomeGraph;
import genome.Strand;
import mutation.AbstractMutation;
import mutation.MutationIndel;
import mutation.Mutations;

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


        ArrayList<Genome> actGen = genomeGraph.getActiveGenomes();

        ArrayList<String> actIds = new ArrayList<>();
        for (Genome genome : actGen) {
            actIds.add(genome.getId());
        }

        maxId = 0;
        ArrayList<Strand> filteredNodes = dataTree.getStrands(minX, maxX, actGen, zoomLevel + 1);
        ArrayList<RibbonNode> result = createNodesFromStrands(filteredNodes, actIds);
        addEdges(result, isMiniMap);
        collapseRibbons(result, zoomLevel);


        Mutations mutations = new Mutations(result);
        mutations.computeAllMutations();
        addMutationLabels(result);
        spreadYCoordinates(result, actIds);


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
     * @param nodes     The ribbonNode Graph to collapse.
     * @param zoomLevel The zoomlevel.
     */
    @SuppressWarnings("checkstyle:methodlength")
    protected void collapseRibbons(ArrayList<RibbonNode> nodes, int zoomLevel) {
        System.out.println(nodes.size() + " Before collapsing");

        ArrayList<RibbonNode> newNodes = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            RibbonNode node = nodes.get(i);

            if (node != null) {
                ArrayList<RibbonNode> nodesToCollapse = new ArrayList<>();
                nodesToCollapse.add(node);
                while (node.getOutEdges().size() == 1) {
                    RibbonNode other = node.getOutEdges().get(0).getEnd();
                    if (other != null) {
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

        for (RibbonNode node : nodes) {
            spreadYCoordinates(node, activeGenomes);

        }


    }

    /**
     * Place all the nodes in common in the middle, and copy and distribute
     * all others along the y coordinate associated with that genome.
     *
     * @param node          The node to calculate the Y for.
     * @param activeGenomes The active genomes to spread out.
     */
    protected void spreadYCoordinates(RibbonNode node,
                                      ArrayList<String> activeGenomes) {
        if(node.getGenomes().size()==activeGenomes.size()){
            node.setY(0);
            node.setyFixed(true);
        }
        for (int i = 0; i < node.getOutEdges().size(); i++) {
            RibbonEdge edge = node.getOutEdges().get(i);
            RibbonNode endNode = edge.getEnd();
            if (endNode.getGenomes().size() <= node.getGenomes().size()) { //split apart
                int newY = (int) (Math.abs(node.getGenomes().size() - endNode.getGenomes().size()) * 20 * Math.pow(-1, i));
                endNode.setY(node.getY() + newY);
                if(node.getOutEdges().size()>1&&endNode.getGenomes().size()==node.getGenomes().size()){
                    endNode.setyFixed(true);
                }
            } else if (endNode.getGenomes().size() > node.getGenomes().size()&&!endNode.isyFixed()) {//bring together
                endNode.setY(endNode.getY() + ((node.getY() * node.getGenomes().size()) / endNode.getGenomes().size()));
            }

        }


    }



    /**
     * Calculate and add edges to a ribbonGraph.
     *
     * @param nodes     the RibbinGraph to calculate edges for.
     * @param isMiniMap Boolean if this is the minimap.
     */

    protected void addEdges(ArrayList<RibbonNode> nodes, boolean isMiniMap) {
        nodes.sort((RibbonNode o1, RibbonNode o2) -> new Integer(o1.getX()).compareTo(o2.getX()));
        for (Genome genome : genomeGraph.getActiveGenomes()) {
            RibbonNode currentNode = findNextNodeWithGenome(nodes, genome, -1);
            while (currentNode != null) {
                currentNode = addEdgeReturnEnd(nodes, currentNode, genome, isMiniMap);

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
     * @param isMiniMap   Boolean if this is the minimap.
     * @return The end node of the edge.
     */
    protected RibbonNode addEdgeReturnEnd(ArrayList<RibbonNode> nodes,
                                          RibbonNode currentNode,
                                          Genome genome,
                                          boolean isMiniMap) {
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

    /**
     * Adds the mutations to the labels.
     *
     * @param nodes      The nodes in the graph.
     */
    protected void addMutationLabels(ArrayList<RibbonNode> nodes) {
    	for (RibbonNode node : nodes) {
    		if (node.hasMutation()) {
    			StringBuilder mutationLabel = new StringBuilder();
    			for (AbstractMutation mutation : node.getMutations()) {
    				mutationLabel.append(mutation.toString());
    				mutationLabel.append(", ");
    			}
    			if (node.isVisible()) {
    				node.setLabel(mutationLabel.toString() + node.getLabel());
    			} else {
    				RibbonNode withLabel = node.getInEdges().get(0).getStart();
    				withLabel.setLabel(mutationLabel.toString() + withLabel.getLabel());
    			}
    		}
    	}
    }
}
