package phylogenetictree;


import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.util.ArrayList;

/**
 * Created by Matthijs on 4-5-2016.
 * A node in the phylogenetic tree. Stores a name if the node is a leaf.
 */
public class PhylogeneticNode {

    /**
     * The child nodes of this node.
     */
    private ArrayList<PhylogeneticNode> children;
    /**
     * The Genomes contained in this nodes children.
     */
    private ArrayList<String> genomes;
    /**
     * This nodes' parent.
     */
    private PhylogeneticNode parent;
    /**
     * This nodes' genome name ("" if not a leaf).
     */
    private String nameLabel;
    /**
     * The distance to its parent.
     */
    private double distance;
    /**
     * The Id of the node.
     */
    private int id;

    /**
     * Initialize an empty node.
     */
    public PhylogeneticNode() {
        nameLabel = "";
        distance = 0.;
        children = new ArrayList<>();
        genomes = new ArrayList<>();
    }

    /**
     * Initialize this node from tree node, recursively.
     *
     * @param node     The node.
     * @param parent   The parentNode of this node, null if root.
     * @param distance The distance.
     * @param id       The id of this node, root being 0. Incrementing breath first.
     */
    public PhylogeneticNode(final TreeNode node, final PhylogeneticNode parent, final double distance, final int id) {
        nameLabel = node.getName();
        this.distance = distance;
        children = new ArrayList<>();
        genomes = new ArrayList<>();
        this.parent = parent;
        this.id = id;

        for (int i = 0; i < node.numberChildren(); i++) {
            TreeNode child = node.getChild(i);
            addChild(new PhylogeneticNode(child, this, child.getWeight(), id + 1 + i));
        }

        checkLeaf();

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
     * Get the children of a node.
     *
     * @return The children as ArryList.
     */
    public ArrayList<PhylogeneticNode> getChildren() {
        return children;
    }

    /**
     * Adds child node and stores the distance to that node.
     *
     * @param node the node
     */
    public void addChild(final PhylogeneticNode node) {
        children.add(node);
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

    /**
     * Get the id of this node.
     *
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the parent of this node.
     *
     * @return The parent, null if root.
     */
    public PhylogeneticNode getParent() {
        return parent;
    }


}
