package datatree;

import genome.Genome;
import genome.Strand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import abstractdatastructure.TreeStructure;

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
        
        addCommonStrandsToParent(getRoot());


    }


    /**
     * Recursive method for going through the tree and adding the strands top down.
     *
     * @param currentNode the tree root.
     */
    public void addCommonStrandsToParent(DataNode currentNode) {
    	ArrayList<DataNode> children = currentNode.getChildren();
    	if (children.size() != 0) {
    		addCommonStrandsToParent(children.get(0));
    		addCommonStrandsToParent(children.get(1));
    		ArrayList<Strand> strands1 = children.get(0).getStrands();
    		ArrayList<Strand> strands2 = children.get(1).getStrands();
    		ArrayList<Strand> current = new ArrayList<>(strands1);
    		current.retainAll(strands2);
    		currentNode.setStrands(current);
    		strands1.removeAll(current);
    		strands2.removeAll(current);
    		children.get(0).setStrands(strands1);
    		children.get(1).setStrands(strands2);
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
                                        ArrayList<String> genomes, int level) {
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
    public Set<DataNode> getDataNodesForGenomes(ArrayList<String> genomes, int level) {
        Set<DataNode> result = new HashSet<>();
        for (String genome : genomes) {
            result.addAll(getDataNodesForGenome(genome, level));
        }
        return result;

    }

    /**
     * Get the full datanodes of a single genome.
     *
     * @param genomeId The genome to get the nodes from.
     * @param level    The zoomlevel in the tree.
     * @return The list of unfiltered dataNodes.
     */
    public Set<DataNode> getDataNodesForGenome(String genomeId, int level) {
        Set<DataNode> result = new HashSet<>();
        DataNode currentNode = getRoot();
        while (currentNode.getLevel() <= level) {
            result.add(currentNode);
            currentNode = currentNode.getChildWithGenome(genomeId);
            if (currentNode == null) {
                break;
            }
        }
        return result;


    }


}
