package abstracttree;


import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;

import java.util.ArrayList;

/**
 * Created by Matthijs on 4-5-2016.
 * An abstract class for a node in a tree.
 */
public abstract class AbstractTreeNode<T extends AbstractTreeNode> {

    /**
     * The child nodes of this node.
     */
    protected ArrayList<T> children;
    /**
     * This nodes' parent.
     */
    protected T parent;
    /**
     * The Id of the node.
     */
    protected int id;


    /**
     * Initialize this node from tree node, recursively.
     *
     * @param parent The parent of this node, root having null.
     */
    public AbstractTreeNode(T parent, int childNumber) {

        children = new ArrayList<>();
        this.parent = parent;
        this.id = generateId(parent, childNumber);


    }

    /**
     * Generates the id of this node breath first based on the id of the parent node, root being 0.
     *
     * @param parent The parent of this node.
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
            return (T)this;
        } else if (this.getChildren().size() != 0) {
            for (T obj : this.getChildren()) {
                T child =  obj;
                if (child.getId() == id) {
                    return child;
                }
                T node = (T)child.getNode(id);
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
     * Get the parent of this node.
     *
     * @return The parent, null if root.
     */
    public T getParent() {
        return parent;
    }


}
