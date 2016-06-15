package datatree;

import abstractdatastructure.TreeStructure;
import genome.Genome;
import genome.Strand;
import genome.StrandEdge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Tree containing all strands and genomes of the data.
 */
public class DataTree extends TreeStructure<DataNode> {

    /**
     * The minimal amount of strands to return.
     */
    private int minStrandsToReturn = 0;

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

    public void addStrandsFromGenomes(ArrayList<Genome> genomes) {


        //add all strands to the leafs.
        for (Genome genome : genomes) {
            DataNode leaf = getRoot().getGenomeLeaf(genome.getId());
            if (leaf != null) {
                leaf.setStrands(genome.getStrands());
            }

        }

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new AddStrandsFromChildren(getRoot()));
        //TempReadWriteTree.writeTree((getRoot()));

    }

    /**
     * Get the strands within the given parameters.
     *
     * @param xMin    The minimal x value.
     * @param xMax    The maximal x value.
     * @param genomeIDs The genomes to filter for.
     * @param level   The maximum tree level to zoom to.
     * @return A list of datanodes that pertain to the parameters.
     */
    public ArrayList<Strand> getStrands(int xMin, int xMax,
                                        ArrayList<String> genomeIDs, int level) {
        return filterStrandsFromNodes(xMin, xMax, getDataNodesForGenomes(genomeIDs, level), genomeIDs, level);

    }

    /**
     * Remove unwanted strands from the nodes.
     *
     * @param xMin  the minimal id of the strands.
     * @param xMax  the maximal id of the strands.
     * @param nodes the nodes to filter.
     * @return A filtered list of nodes.
     */
    public ArrayList<Strand> filterStrandsFromNodes(int xMin, int xMax, Set<DataNode> nodes, ArrayList<String> genomes, int level) {
        ArrayList<Strand> result = new ArrayList<>();
        Strand leftAllGenomes = new Strand();
        Strand rightAllGenomes = new Strand();
        leftAllGenomes.setX(Integer.MIN_VALUE);
        rightAllGenomes.setX(Integer.MAX_VALUE);
        int minSize = 0;
        if (level < 5) {
            minSize = 200 - level * 40;
        }

        for (DataNode node : nodes) {
            for (Strand strand : node.getStrands()) {
                if (strand.getSequence().length() > minSize) {
                if (strand.getX() > xMin && strand.getX() < xMax) {
                    result.add(strand);
                } else if (strand.getX() < xMin && strand.getX() > leftAllGenomes.getX()
                        && strand.getGenomes().containsAll(genomes)) {
                    leftAllGenomes = strand;
                } else if (strand.getX() > xMax && strand.getX() < rightAllGenomes.getX()
                        && strand.getGenomes().containsAll(genomes)) {
                    rightAllGenomes = strand;
                }
                }
            }

        }

        if(leftAllGenomes.getX()!=Integer.MIN_VALUE) {
            result.add(leftAllGenomes);
            for(StrandEdge edge:leftAllGenomes.getOutgoingEdges()){
                result.add(edge.getEnd());
            }
        }
        if(rightAllGenomes.getX()!=Integer.MAX_VALUE) {
            result.add(rightAllGenomes);
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
     * @param genome The genome to get datanodes for.
     * @param level  The zoomlevel in the tree.
     * @return The list of unfiltered dataNodes.
     */
    public Set<DataNode> getDataNodesForGenome(String genome, int level) {
        Set<DataNode> result = new HashSet<>();
        DataNode currentNode = getRoot();
        int totalStrands = 0;
        while (currentNode.getLevel() <= level) {
            result.add(currentNode);
            totalStrands += currentNode.getStrands().size();
            currentNode = currentNode.getChildWithGenome(genome);
            if (currentNode == null) {
                break;
            }
        }
        if (totalStrands < minStrandsToReturn) {
            result = getDataNodesForGenome(genome, level + 1);
        }
        return result;


    }

    /**
     * Set the minimal strands to return.
     *
     * @param minStrandsToReturn The minimal strands amount to return.
     */
    public void setMinStrandsToReturn(int minStrandsToReturn) {
        this.minStrandsToReturn = minStrandsToReturn;
    }
}
