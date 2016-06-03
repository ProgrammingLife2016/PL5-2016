package datatree;

import abstractdatastructure.AbstractTreeNode;
import genome.Strand;
import phylogenetictree.PhylogeneticNode;

import java.util.ArrayList;

/**
 * A Node in the DataTree.
 * Created by Matthijs on 12-5-2016.
 */
public class DataNode extends AbstractTreeNode<DataNode> {

    private ArrayList<Strand> strands; // The strands contained in this node.
    private ArrayList<String> genomes; // The genomeIds contained in this node.
    private int level; //The level of this node in the tree, root having level 0;

    /**
     * creates a data Node.
     *
     * @param parent      The parent of this node, null if root.
     * @param childNumber The index of this node in the children array of the parent.
     */
    public DataNode(DataNode parent, int childNumber) {
        super(parent, childNumber);
        strands = new ArrayList<>();
        genomes = new ArrayList<>();
        if (parent == null) {
            level = 0;
        } else {
            level = parent.getLevel() + 1;
        }

    }

    /**
     * Adapt DataNode from a phylogeneticNode, recursively.
     *
     * @param phyloNode   The phylo to adapt.
     * @param parent      the parent of this node.
     * @param childNumber The index of this node in the children array of the parent.
     */
    public DataNode(PhylogeneticNode phyloNode, DataNode parent, int childNumber) {
        super(parent, childNumber);
        strands = new ArrayList<>();
        this.genomes = phyloNode.getGenomes();

        if (parent == null) {
            level = 0;
        } else {
            level = parent.getLevel() + 1;
        }

        for (int i = 0; i < phyloNode.getChildren().size(); i++) {
            this.addChild(new DataNode(phyloNode.getChildren().get(i), this, i));
        }
    }

    /**
     * Get the child of this node that contains genome.
     *
     * @param genome The genome to find.
     * @return The child if a child contains this genome, otherwise null.
     */
    public DataNode getChildWithGenome(String genome) {
        for (DataNode child : getChildren()) {
            if (child.getGenomes().contains(genome)) {
                return child;
            }
        }
        return null;
    }


    /**
     * Get genomeLeaf through depth first search.
     *
     * @param genomeName The name of the node.
     * @return The node, null if it is not contained in this graph.
     */
    public DataNode getGenomeLeaf(final String genomeName) {


        for (DataNode child : this.getChildren()) {
            if (child.getGenomes().size() == 1
                    && child.getGenomes().get(0).equals(genomeName)) {
                return child;
            }

            DataNode node = child.getGenomeLeaf(genomeName);
            if (node != null) {
                return node;
            }
        }

        return null;
    }

    /**
     * Get the stands contained in this node.
     *
     * @return The strands.
     */
    public ArrayList<Strand> getStrands() {
        return strands;
    }

    /**
     * Set the strands.
     *
     * @param strands The strands.
     */
    public void setStrands(ArrayList<Strand> strands) {
        this.strands = strands;
    }

    /**
     * The genomes contained in this node.
     *
     * @return The genomes.
     */
    public ArrayList<String> getGenomes() {
        return genomes;
    }

    /**
     * The level of this node.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set the level.
     *
     * @param level the treeLevel.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Set the genomes contained in this node.
     *
     * @param genomes The genomes.
     */
    public void setGenomes(ArrayList<String> genomes) {
        this.genomes = genomes;
    }
}
