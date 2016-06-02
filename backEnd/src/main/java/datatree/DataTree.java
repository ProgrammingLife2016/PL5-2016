package datatree;

import abstractdatastructure.TreeStructure;
import genome.Genome;
import genome.Strand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Tree containing all strands and genomes of the data.
 */
public class DataTree extends TreeStructure<DataNode> {


    /**
     * Default constructor.
     *
     * @param root The root.
     */
    public DataTree(DataNode root) {
        super(root);
    }


    /**
     * Add the strands to their correct position in the tree, recursively.
     *
     * @param genomes The Genomes containing the strands.
     */

    public void addStrands(ArrayList<Genome> genomes) {


        //add all strands to the leafs.
        for (Genome genome : genomes) {
            DataNode leaf = getRoot().getGenomeLeaf(genome.getId());
            if (leaf != null) {
                leaf.setStrands(genome.getStrands());
            }

        }

        addStrands(getRoot());


    }


    /**
     * Recursive method for going through the tree and adding the strands top down.
     *
     * @param currentNode the tree root.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public void addStrands(DataNode currentNode) {

        if (currentNode.getChildren().size() > 0) {
            DataNode child1 = currentNode.getChildren().get(0);
            DataNode child2 = currentNode.getChildren().get(1);
            if (child1.getStrands().size() == 0) {
                addStrands(child1);
            }
            if (child2.getStrands().size() == 0) {
                addStrands(child2);
            }
            if (child1.getStrands().size() == 0) {
                if (child2.getStrands().size() != 0) {
                    currentNode.setStrands((ArrayList<Strand>) child2.getStrands().clone());
                    child2.getStrands().removeAll(child2.getStrands());
                }
            } else if (child2.getStrands().size() == 0) {
                if (child1.getStrands().size() != 0) {
                    currentNode.setStrands((ArrayList<Strand>) child1.getStrands().clone());
                    child1.getStrands().removeAll(child1.getStrands());
                }
            } else if (child1.getStrands().size() != 0 && child2.getStrands().size() != 0) {
                ArrayList<Strand> parentStrands = (ArrayList<Strand>) child1.getStrands().clone();
                parentStrands.retainAll(child2.getStrands());
                currentNode.setStrands(parentStrands);
                child1.getStrands().removeAll(parentStrands);
                child2.getStrands().removeAll(parentStrands);
            }
        }
    }

    /**
     * Get the strands within the given parameters.
     *
     * @param xMin    The minimal x value.
     * @param xMax    The maximal x value.
     * @param genomes The genomes to filter for.
     * @param level   The maximum tree level to zoom to.
     * @return A list of datanodes that pertain to the parameters.
     */
    public ArrayList<Strand> getStrands(int xMin, int xMax,
                                        ArrayList<Genome> genomes, int level) {
        return filterStrandsFromNodes(xMin, xMax, getDataNodesForGenomes(genomes, level));

    }

    /**
     * Remove unwanted strands from the nodes.
     *
     * @param xMin  the minimal id of the strands.
     * @param xMax  the maximal id of the strands.
     * @param nodes the nodes to filter.
     * @return A filtered list of nodes.
     */
    public ArrayList<Strand> filterStrandsFromNodes(int xMin, int xMax, Set<DataNode> nodes) {
        ArrayList<Strand> result = new ArrayList<>();

        for (DataNode node : nodes) {
            for (Strand strand : node.getStrands()) {
                if (strand.getX() < xMax + 10 && strand.getX() > xMin - 10) {
                    result.add(strand);
                }
            }
        }

        return result;


    }

    /**
     * Get the full nodes for the different genomes, no duplicates.
     *
     * @param genomes the genomes to get the nodes from.
     * @param level   the maximum level in the tree.
     * @return The full datanodes.
     */
    public Set<DataNode> getDataNodesForGenomes(ArrayList<Genome> genomes, int level) {
        Set<DataNode> result = new HashSet<>();
        for (Genome genome : genomes) {
            result.addAll(getDataNodesForGenome(genome, level));
        }
        return result;

    }

    /**
     * Get the full datanodes of a single genome.
     *
     * @param genome The genome to get datanodes for.
     * @param level  The zoomlevel in the tree.
     * @return The list of unfiltered dataNodes.
     */
    public Set<DataNode> getDataNodesForGenome(Genome genome, int level) {
        Set<DataNode> result = new HashSet<>();
        DataNode currentNode = getRoot();
        while (currentNode.getLevel() <= level) {
            result.add(currentNode);
            currentNode = currentNode.getChildWithGenome(genome.getId());
            if (currentNode == null) {
                break;
            }
        }
        return result;


    }


}
