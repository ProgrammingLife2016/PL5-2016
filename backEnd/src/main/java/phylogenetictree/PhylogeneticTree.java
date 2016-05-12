package phylogenetictree;

import abstracttree.TreeStructure;
import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;


/**
 * Created by Matthijs on 4-5-2016.
 */
public class PhylogeneticTree extends TreeStructure<PhylogeneticNode> {



    /**
     * Initialize an empty tree.
     */
    public PhylogeneticTree() {
    }

    /**
     * Initialize a tree.
     *
     * @param root The root of the tree.
     */
    public PhylogeneticTree(final PhylogeneticNode root) {
        super(root);
    }

    /**
     * Method that parses a newick tree from a file and stores it in this tree.
     *
     * @param fileName the file to parse
     */
    public void parseTree(final String fileName) {
        Tree tree;
        BufferedReader reader;
        InputStream in = PhylogeneticTree.class.getClassLoader().getResourceAsStream(fileName);
        Reader r = new InputStreamReader(in, StandardCharsets.UTF_8);
        reader = new BufferedReader(r);
        TreeParser tp = new TreeParser(reader);
        tree = tp.tokenize("");
        this.setRoot(new PhylogeneticNode(tree.getRoot(), null, 0.,0));
    }

    /**
     * Get node with nameLabel name through depth first search.
     *
     * @param name The name of the node.
     * @return The node, null if it is not contained in this graph.
     */
    public PhylogeneticNode getNodeWithLabel(final String name) {

        if (this.getRoot().getNameLabel().equals(name)) {
            return getRoot();
        } else if (getRoot().getChildren().size() != 0) {
            for (PhylogeneticNode child : getRoot().getChildren()) {
                if (child.getNameLabel().equals(name)) {
                    return child;
                }
                PhylogeneticTree subTree = new PhylogeneticTree(child);
                PhylogeneticNode node = subTree.getNodeWithLabel(name);
                if (node != null) {
                    return node;
                }
            }
        }

        return null;
    }

    @Override
    public PhylogeneticNode getNode(int id) {
        return (PhylogeneticNode) super.getNode(id);
    }





}
