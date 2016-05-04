package PhylogenicTree;


import java.util.HashMap;

/**
 * Created by Matthijs on 4-5-2016.
 *
 * A node in the phylogenic tree. Stores a name if the node is a leaf.
 *
 */
public class PhylogenicNode {

    private HashMap<PhylogenicNode,Double> children;
    private String nameLabel;


    public PhylogenicNode() {
        nameLabel="";
    }

    public HashMap<PhylogenicNode, Double> getChildren() {
        return children;
    }

    /**
     * Adds child node and stores the distance to that node
     * @param node the node
     * @param dist the distance
     */
    public void addChild(PhylogenicNode node, double dist) {
        if(children == null)
            children= new HashMap<>();

        children.put(node,dist);
    }


    /**
     * returns a string of lenght 0 if the node is not a leaf.
     * @return the node name label
     */
    public String getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(String nameLabel) {
        this.nameLabel = nameLabel;
    }
}
