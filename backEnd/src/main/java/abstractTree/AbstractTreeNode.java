package abstractTree;


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
     * @param id The id of this node, root being 0. Incrementing breath first.
     */
    public AbstractTreeNode(int id, T parent) {

        children = new ArrayList<>();
        this.id = id;
        this.parent = parent;


    }

    /**
     * Add the children to this node.
     *
     * @param node The treeNode that contains the children of this node.
     */
    public abstract void addChildren(TreeNode node);


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
