package ribbontree;

import abstracttree.AbstractTreeNode;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class ribbonNode extends AbstractTreeNode {

    /**
     * creates a ribbon Node.
     * @param parent The parent of this node, null if root.
     */
    public ribbonNode(ribbonNode parent) {
        super(parent);
    }
}
