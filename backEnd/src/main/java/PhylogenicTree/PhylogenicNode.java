package PhylogenicTree;


import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Matthijs on 4-5-2016.
 *
 * A node in the phylogenic tree. Stores a name if the node is a leaf.
 *
 */
public class PhylogenicNode {

    private ArrayList<PhylogenicNode> children;
    private String nameLabel;
    private double distance;


    public PhylogenicNode() {
        nameLabel="";
        distance=0.;
        children= new ArrayList<>();
    }

    /**
     * initalize this node from tree node
     * @param node
     */
    public PhylogenicNode(TreeNode node, double _distance){
        nameLabel=node.getName();
        distance=_distance;
        children= new ArrayList<>();
        for(int i=0;i<node.numberChildren();i++){
            addChild(new PhylogenicNode(node.getChild(i),node.getChild(i).getWeight()));
        }

    }

    public ArrayList<PhylogenicNode> getChildren() {
        return children;
    }

    /**
     * Adds child node and stores the distance to that node
     * @param node the node
     */
    public void addChild(PhylogenicNode node) {
        children.add(node);
    }



    /**
     * returns a string of lenght 0 if the node is not a leaf.
     * @return the node name label
     */
    public String getNameLabel() {
        return nameLabel;
    }

    public double getDistance() {
        return distance;
    }



}
