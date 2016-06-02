package phylogenetictree;

import abstractdatastructure.TreeStructure;
import net.sourceforge.olduvai.treejuxtaposer.TreeParser;
import net.sourceforge.olduvai.treejuxtaposer.drawer.Tree;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


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
        this.setRoot(new PhylogeneticNode(tree.getRoot(), null, 0., 0));

        
    }
	
    /**
     * Removes the leaf.
     *
     * @param node the node
     */
    public void removeLeaf(PhylogeneticNode node) {
    	removeSubtree(node);
    }

	/**
	 * Removes the subtree.
	 *
	 * @param node the node
	 */
	private void removeSubtree(PhylogeneticNode node) {
		assert (node.getParent().getChildren().size() == 2);
		
		for (String genome : node.getGenomes()) {
			node.getParent().removeGenome(genome);
		}
		
		PhylogeneticNode parent = node.getParent();
		int childNumber = parent.getChildren().indexOf(node);
		int siblingChildNumber = (1 + childNumber) % 2;
		PhylogeneticNode sibling = parent.getChildren().get(siblingChildNumber);
		parent.getParent().addChild(sibling);
		parent.getParent().removeChild(parent);
		sibling.setParent(parent.getParent());
		
	}

	/**
	 * Recursive algorithm to get all the leaves in a tree.
	 * @param node The current node.
	 * @param result The result.
	 * @return All the leaves.
	 */
	private ArrayList<PhylogeneticNode> getLeaves(PhylogeneticNode node, 
			ArrayList<PhylogeneticNode> result) {
		if (node.getChildren().size() == 0) {
			result.add(node);
		} else {
			result = getLeaves(node.getChildren().get(0), result);
			if (node.getChildren().size() > 1) {
				result = getLeaves(node.getChildren().get(1), result);
			}
		}
		return result;
	}
	

}

