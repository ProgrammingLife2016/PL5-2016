package abstractdatastructure;


/**
 * Created by Matthijs on 4-5-2016.
 * Class that creates a tree starting with the root node.
 * @param <T> The treeNode subclass contained in the tree.
 */
public class TreeStructure<T extends AbstractTreeNode> {

    /**
     * The root Strand of the TreeStructure.
     */
    private T root;

    /**
     * Default contstructor, to create an empty tree.
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
     * Get the root of the tree.
     *
     * @return The root.
     */
    public T getRoot() {
        return root;
    }

    /**
     * Set the root.
     *
     * @param node The new root.
     */
    public void setRoot(T node) {
        root = node;
    }


}
