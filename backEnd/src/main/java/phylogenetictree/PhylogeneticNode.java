package phylogenetictree;


import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.util.ArrayList;

import abstractdatastructure.AbstractTreeNode;

// TODO: Auto-generated Javadoc
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
     * Instantiates a new phylogenetic node.
     *
     * @param parent the parent
     * @param childNumber the child number
     */
    public PhylogeneticNode(PhylogeneticNode parent, int childNumber) {
        super(parent, childNumber);
        this.nameLabel = "";
        this.distance = 0.;
        this.genomes = new ArrayList<>();
    }

    /**
     * Initialize this node from tree node, recursively.
     *
     * @param node        The node.
     * @param parent      The parentNode of this node, null if root.
     * @param distance    The distance.
     * @param childNumber The childNumber.
     */
    public PhylogeneticNode(final TreeNode node, final PhylogeneticNode parent,
                            final double distance, int childNumber) {

        super(parent, childNumber);
        nameLabel = node.getName();
        this.distance = distance;
        genomes = new ArrayList<>();

        adaptChild(node);
        checkLeaf();

    }

    /**
     * Addapt the child node.
     *
     * @param node Node.
     */
    public void adaptChild(TreeNode node) {

        for (int i = 0; i < node.numberChildren(); i++) {
            TreeNode child = node.getChild(i);

            addChild(new PhylogeneticNode(child, this, child.getWeight(), i));
        }
    }

    /**
     * If this node is a leaf, add the genome it contains to all its parents.
     */
    private void checkLeaf() {
        if (!nameLabel.equals("")) {
            nameLabel = nameLabel + ".fasta";
            genomes.add(nameLabel);
            if (this.getParent() != null) {
                getParent().addGenome(nameLabel);
            }

        }
    }

    /**
     * Get node with nameLabel name through depth first search.
     *
     * @param name The name of the node.
     * @return The node, null if it is not contained in this graph.
     */
    public PhylogeneticNode getNodeWithLabel(final String name) {

        if (this.getNameLabel().equals(name)) {
            return this;
        } else if (this.getChildren().size() != 0) {
            for (PhylogeneticNode child : this.getChildren()) {
                if (child.getNameLabel().equals(name)) {
                    return child;
                }
                PhylogeneticNode node = child.getNodeWithLabel(name);
                if (node != null) {
                    return node;
                }
            }
        }

        return null;
    }

   
    /**
     * Sets the name label.
     *
     * @param nameLabel the new name label
     */
    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
        checkLeaf();
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
        if (this.getParent() != null) {
            this.getParent().addGenome(genome);
        }
    }



}
