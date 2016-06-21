package datatree;

import abstractdatastructure.TreeStructure;
import genome.Genome;
import strand.Strand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

/**
 * Tree containing all strands and genomes of the data.
 */
public class DataTree extends TreeStructure<DataNode> {

    /**
     * The minimal zoomlevel that shows deviation between the genomes compared.
     */
    int minLevel = Integer.MAX_VALUE;

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
        // Add all strands to the leafs.
        for (Genome genome : genomes) {
            DataNode leaf = getRoot().getGenomeLeaf(genome.getId());
            if (leaf != null) {
                leaf.setStrands(genome.getStrands());
            }
        }

        // Compute the strands that are in both the children of a node.
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new AddStrandsFromChildren(getRoot()));
    }

    /**
     * Get the strands in the tree.
     * These strands need to be between xMin and xMax.
     *
     * @param xMin    The minimal x value.
     * @param xMax    The maximal x value.
     * @param genomes The genomes to filter for.
     * @param level   The maximum tree level to zoom to.
     * @param isMiniMap Boolean if this is the minimap.
     * @return A list of datanodes that pertain to the parameters.
     */
    public ArrayList<Strand> getStrands(int xMin, int xMax,
                                        ArrayList<ArrayList<Genome>> genomes, int level, 
                                        boolean isMiniMap) {
        if (!isMiniMap && level < minLevel) {
            level = minLevel;
        }


        return filterStrandsFromNodes(xMin, xMax,
                getDataNodesForGenomes(genomes, level), genomes, level, isMiniMap);
    }

    /**
     * Remove unwanted strands from the nodes.
     * They are removed when their x lays not between xMax and xMin.
     *
     * @param xMin    the minimal id of the strands.
     * @param xMax    the maximal id of the strands.
     * @param nodes   the nodes to filter.
     * @param genomes The phylo nodes selected.
     * @param level   The zoomlevel.
     * @param isMiniMap Boolean if this is the minimap.
     * @return A filtered list of nodes.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public ArrayList<Strand> filterStrandsFromNodes(int xMin, int xMax,
                                                    Set<DataNode> nodes,
                                                    ArrayList<ArrayList<Genome>> genomes,
                                                    int level,
                                                    boolean isMiniMap) {
        HashSet<Strand> result = new HashSet<>();
        Strand leftAllGenomes = new Strand();
        Strand rightAllGenomes = new Strand();
        leftAllGenomes.setX(Integer.MIN_VALUE);
        rightAllGenomes.setX(Integer.MAX_VALUE);

        int minSize = 400 - level * 20;

        if (isMiniMap) {
            minSize = 400;
        }

        HashSet<String> genomeIDs = new HashSet<>();
        for (ArrayList<Genome> list : genomes) {
            for (Genome genome : list) {
                genomeIDs.add(genome.getId());
            }
        }

        for (DataNode node : nodes) {
            for (Strand strand : node.getStrands()) {
                if (strand.getSequence().length() > minSize) {
                    if (strand.getX() < xMin && strand.getX() > leftAllGenomes.getX()
                            && strand.getGenomes().containsAll(genomeIDs)) {
                        leftAllGenomes = strand;
                    }
                    if (strand.getX() > xMax && strand.getX() < rightAllGenomes.getX()
                            && strand.getGenomes().containsAll(genomeIDs)) {
                        rightAllGenomes = strand;
                    }
                    if (strand.getX() >= xMin && strand.getX() <= xMax) {
                        result.add(strand);
                    } else if (strand.getX() > leftAllGenomes.getX()
                            && strand.getX() < rightAllGenomes.getX()
                            && strand.getSequence().length() > 200) {
                        result.add(strand);
                    }
                }
            }
        }

        if (leftAllGenomes.getX() != Integer.MIN_VALUE) {
            result.add(leftAllGenomes);
        }
        if (rightAllGenomes.getX() != Integer.MAX_VALUE) {
            result.add(rightAllGenomes);
        }
        return new ArrayList<>(result);
    }

    /**
     * Get the full nodes for the different genomes, no duplicates.
     *
     * @param genomes the genomes to get the nodes from.
     * @param level   the maximum level in the tree.
     * @return The full datanodes.
     */

    public Set<DataNode> getDataNodesForGenomes(ArrayList<ArrayList<Genome>> genomes, int level) {
        Set<DataNode> result = new HashSet<>();
        HashSet<String> ids = new HashSet<>();

        for (ArrayList<Genome> genome : genomes) {
            result.addAll(getDataNodesForGenome(genome, level));
            for (Genome g : genome) {
                ids.add(g.getId());
            }
        }
        for (DataNode node : result) {
            if (!node.getGenomes().containsAll(ids) && node.getLevel() < minLevel) {
                minLevel = node.getLevel();
            }

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

    public Set<DataNode> getDataNodesForGenome(ArrayList<Genome> genome, int level) {
        ArrayList<String> ids = new ArrayList<>();
        for (Genome g : genome) {
            ids.add(g.getId());
        }

        Set<DataNode> result = new HashSet<>();
        DataNode currentNode = getRoot();
        while (currentNode.getLevel() <= level) {
            result.add(currentNode);
            currentNode = currentNode.getChildWithGenomes(ids);
            if (currentNode == null) {
                break;
            }
        }
        return result;
    }
    
	/**
	 * Gets the patristic distance which is the sum of the length of the branches 
	 * that connect the first and the second genome in the tree.
	 *
	 * @param firstGenome the first genome
	 * @param secondGenome the second genome
	 * @return the patristic distance
	 */
	public int getPatristicDistance(String firstGenome, String secondGenome) {
		DataNode firstNode = getRoot();
		DataNode secondNode = getRoot();
		while (firstNode.equals(secondNode)) {
			firstNode = firstNode.getChildWithGenome(firstGenome);
			secondNode = secondNode.getChildWithGenome(secondGenome);
		}
		int patristicDistance = 2;
		
		while (firstNode != null) {
			firstNode = firstNode.getChildWithGenome(firstGenome);
			patristicDistance++;
		}
		
		while (secondNode != null) {
			secondNode = secondNode.getChildWithGenome(secondGenome);
			patristicDistance++;
		}
		return patristicDistance;
	}

    /**
     * Reset the minimal level after selecting new genomes to compare.
     */
    public void resetMinLevel() {
        minLevel = Integer.MAX_VALUE;
    }
}
