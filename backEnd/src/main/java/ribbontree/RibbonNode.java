package ribbontree;

import abstracttree.AbstractTreeNode;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonNode extends AbstractTreeNode {

    /**
     * creates a ribbon Node.
     * @param parent The parent of this node, null if root.
     */
    public RibbonNode(RibbonNode parent) {
        super(parent, 0);
    }
}
