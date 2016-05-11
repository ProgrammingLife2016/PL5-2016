package phylogenetictree;


import abstractTree.AbstractTreeNode;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.util.ArrayList;

/**
 * Created by Matthijs on 4-5-2016.
 * A node in the phylogenetic tree. Stores a name if the node is a leaf.
 */
public class PhylogeneticNode extends AbstractTreeNode<PhylogeneticNode> {


    /**
     * The Genomes contained in this nodes children.
     */
    private ArrayList<String> genomes;

    /**
     * The name of this genome, "" if not a leaf.
     */
    private String nameLabel;
    /**
     * The distance to its parent.
     */
    private double distance;


    /**
     * Initialize this node from tree node, recursively.
     *
     * @param node     The node.
     * @param parent   The parentNode of this node, null if root.
     * @param distance The distance.
     * @param id       The id of this node, root being 0. Incrementing breath first.
     */
    public PhylogeneticNode(final TreeNode node, final PhylogeneticNode parent,
                            final double distance, final int id) {

        super(id, parent);
        nameLabel = node.getName();
        this.distance = distance;
        genomes = new ArrayList<>();

        addChildren(node);
        checkLeaf();

    }

    @Override
    public void addChildren(TreeNode node) {

        for (int i = 0; i < node.numberChildren(); i++) {
            TreeNode child = node.getChild(i);
            addChild(new PhylogeneticNode(child, this, child.getWeight(), id + 1 + i));
        }
    }

    /**
     * If this node is a leaf, add the genome it contains to all its parents.
     */
    private void checkLeaf() {
        if (!nameLabel.equals("")) {
            if (this.parent != null) {
                parent.addGenome(nameLabel);
            }
        }
    }


    /**
     * returns a string of lenght 0 if the node is not a leaf.
     *
     * @return the node name label
     */
    public String getNameLabel() {
        return nameLabel;
    }

    /**
     * Get the distance.
     *
     * @return The distance.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Get the genomes that are children of this node.
     *
     * @return a list of genome labels
     */
    public ArrayList<String> getGenomes() {
        return genomes;
    }

    /**
     * Add a genome to this node and its parents, if its parent is not the root.
     *
     * @param genome The genome to add.
     */
    public void addGenome(final String genome) {
        genomes.add(genome);
        if (parent != null) {
            parent.addGenome(genome);
        }
    }

}
