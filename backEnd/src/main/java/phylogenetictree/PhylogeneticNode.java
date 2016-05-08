package phylogeneticTree;


import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.util.ArrayList;

/**
 * Created by Matthijs on 4-5-2016.
 *
 * A node in the phylogenetic tree. Stores a name if the node is a leaf.
 *
 */
public class PhylogeneticNode {

    private ArrayList<PhylogeneticNode> children;
    private String nameLabel;
    private double distance;

    /**
     * Initialize an empty node.
     */
    public PhylogeneticNode() {
        nameLabel = "";
        distance = 0.;
        children = new ArrayList<>();
    }

    /**
     * Initialize this node from tree node.
     * @param node The node.
     * @param distance The distance.
     */
    public PhylogeneticNode(TreeNode node, double distance) {
        nameLabel = node.getName();
        this.distance = distance;
        children = new ArrayList<>();
        for (int i = 0; i < node.numberChildren(); i++) {
            addChild(new PhylogeneticNode(node.getChild(i), node.getChild(i).getWeight()));
        }

    }

    /**
     * Get the children of a node.
     * @return The children as ArryList.
     */
    public ArrayList<PhylogeneticNode> getChildren() {
        return children;
    }

    /**
     * Adds child node and stores the distance to that node.
     * @param node the node
     */
    public void addChild(PhylogeneticNode node) {
        children.add(node);
    }

    /**
     * returns a string of lenght 0 if the node is not a leaf.
     * @return the node name label
     */
    public String getNameLabel() {
        return nameLabel;
    }

    /**
     * Get the distance.
     * @return The distance.
     */
    public double getDistance() {
        return distance;
    }



}
