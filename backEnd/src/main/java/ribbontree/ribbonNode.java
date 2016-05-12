package ribbontree;

import abstracttree.AbstractTreeNode;
import genome.Strand;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class ribbonNode extends AbstractTreeNode<ribbonNode> {

    ArrayList<Strand> strands;
    ArrayList<String> genomes;

    /**
     * creates a ribbon Node.
     * @param parent The parent of this node, null if root.
     */
    public ribbonNode(ribbonNode parent, int childNumber) {
        super(parent, childNumber);
    }
}
