package abstracttree;


/**
 * Created by Matthijs on 4-5-2016.
 */
public class TreeStructure<T extends AbstractTreeNode> {

    /**
     * The root Strand of the TreeStructure.
     */
    protected T root;

    /**
     * Default contstructor.
     */
    public TreeStructure() {
    }


    /**
     * Initialize a tree.
     *
     * @param root The root of the tree.
     */
    public TreeStructure(T root) {
        this.root = root;
    }

    /**
     * Get node with id through depth first search.
     *
     * @param id The id of the node.
     * @return The node, null if it is not contained in this graph.
     */
    public AbstractTreeNode getNode(final int id) {

        if (root.getId() == id) {
            return root;
        } else if (root.getChildren().size() != 0) {
            for (Object obj : root.getChildren()) {
                AbstractTreeNode child = (AbstractTreeNode) obj;
                if (child.getId() == id) {
                    return child;
                }
                TreeStructure subTree = new TreeStructure(child);
                AbstractTreeNode node = subTree.getNode(id);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Set the root.
     *
     * @param node The new root.
     */
    public void setRoot(T node) {
        root = node;
    }

    /**
     * Get the root of the tree.
     *
     * @return The root.
     */
    public AbstractTreeNode getRoot() {
        return root;
    }


}
