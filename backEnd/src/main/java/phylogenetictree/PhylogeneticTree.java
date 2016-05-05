package phylogenetictree;

import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Matthijs on 4-5-2016.
 */
public class PhylogeneticTree {
	
    private PhylogeneticNode root;

    /**
     * Initialize an empty tree.
     */
    public PhylogeneticTree() {
    }

    /**
     * Initialize a tree.
     * @param root The root of the tree.
     */
    public PhylogeneticTree(PhylogeneticNode root) {
        this.root = root;
    }

    /**
     * Method that parses a newick tree from a file and stores it in this tree.
     * @param fileName the file to parse
     */

    public void parseTree(String fileName) {
        Tree tree = new Tree();
        try {
            BufferedReader r = new BufferedReader(new FileReader("src/testFile"));
            TreeParser tp = new TreeParser(r);
            tree = tp.tokenize("");
            this.setRoot(new PhylogeneticNode(tree.getRoot(), 0.));


        }
        catch (FileNotFoundException e) {
            System.out.println("tree file not found");
        }
    }

    /**
     * Get node with nameLabel name throught -first search.
     * @param name name of node
     * @return The node, null if it is not contained in this graph
     */
    public PhylogeneticNode getNode(String name) {

        if (root.getNameLabel().equals(name)) {
            return root;
        }
        else if (root.getChildren().size() != 0) {
            for (PhylogeneticNode child:root.getChildren()) {
                if (child.getNameLabel().equals(name)) {
                    return child;
                }
                PhylogeneticTree subTree = new PhylogeneticTree(child);
                PhylogeneticNode node = subTree.getNode(name);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }

    /**
     * Set the root.
     * @param node The new root.
     */
    public void setRoot(PhylogeneticNode node) {
        root = node;
    }

    /**
     * Get the root of the tree.
     * @return The root.
     */
    public PhylogeneticNode getRoot() {
        return root;
    }

}
