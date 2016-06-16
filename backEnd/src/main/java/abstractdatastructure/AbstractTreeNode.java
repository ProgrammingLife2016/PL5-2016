package abstractdatastructure;


import java.util.ArrayList;

/**
 * Created by Matthijs on 4-5-2016.
 * An abstract class for a node in a tree.
 *
 * @param <T> The instantiated subclass of this node.
 */
public abstract class AbstractTreeNode<T extends AbstractTreeNode> {


    /**
     * The child nodes of this node.
     */
    private ArrayList<T> children;
    /**
     * This nodes' parent.
     */
    private T parent;
    /**
     * The Id of the node.
     */
    private int id;


    /**
     * Initialize this node from tree node, recursively.
     *
     * @param parent      The parent of this node, root having null.
     * @param childNumber Childnumber.
     */
    public AbstractTreeNode(T parent, int childNumber) {

        children = new ArrayList<>();
        this.parent = parent;
        this.id = generateId(parent, childNumber);


    }

    /**
     * Generates the id of this node breath first based on the id of the parent node, root being 0.
     *
     * @param parent      The parent of this node.
     * @param childNumber The childnumber.
     * @return The generated id, root being 0.
     */
    public int generateId(T parent, int childNumber) {
        if (parent == null) {
            return 0;
        }
        return childNumber + parent.getId() * 2 + 1;


    }

    /**
     * Get node with id through depth first search.
     *
     * @param id The id of the node.
     * @return The node, null if it is not contained in this graph.
     */
    public T getNode(final int id) {

        if (this.getId() == id) {
            return (T) this;
        } else if (this.getChildren().size() != 0) {
            for (T obj : this.getChildren()) {
                T child = obj;
                if (child.getId() == id) {
                    return child;
                }
                T node = (T) child.getNode(id);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }


    /**
     * Get the children of a node.
     *
     * @return The children as ArrayList.
     */
    public ArrayList<T> getChildren() {
        return children;
    }

    /**
     * Set the children of this node.
     *
     * @param children The children to set.
     */
    public void setChildren(ArrayList<T> children) {
        this.children = children;
    }

    /**
     * Adds child node.
     *
     * @param node the node
     */
    public void addChild(T node) {
        children.add(node);
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
     * Set the id.
     *
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the parent of this node.
     *
     * @return The parent, null if root.
     */
    public T getParent() {
        return parent;
    }

    /**
     * Set the parent.
     *
     * @param parent set the parent.
     */
    public void setParent(T parent) {
        this.parent = parent;
    }

    /**
     * Remove child from the child array.
     *
     * @param child The removed child.
     */
    public void removeChild(AbstractTreeNode<T> child) {
        children.remove(child);
    }
}
