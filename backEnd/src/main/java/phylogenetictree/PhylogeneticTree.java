package phylogenetictree;

import abstracttree.TreeStructure;
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
     * @param currentGenomes The genomes.
     */
    public void parseTree(final String fileName, ArrayList<String> currentGenomes) {
        Tree tree;
        BufferedReader reader;
        InputStream in = PhylogeneticTree.class.getClassLoader().getResourceAsStream(fileName);
        Reader r = new InputStreamReader(in, StandardCharsets.UTF_8);
        reader = new BufferedReader(r);
        TreeParser tp = new TreeParser(reader);
        tree = tp.tokenize("");  
        this.setRoot(new PhylogeneticNode(tree.getRoot(), null, 0., 0));
        removeEmptyLeaves(currentGenomes);
        ArrayList<PhylogeneticNode> r1 = getLeaves(getRoot(), new ArrayList<PhylogeneticNode>());
        removeRedundantNodes(currentGenomes);
        generateId(getRoot());
        
    }
	
    /**
     * Remove the leaves that does not matter from the tree.
     * @param currentGenomes The genomes that has to be in the tree.
     */
	private void removeEmptyLeaves(ArrayList<String> currentGenomes) {
		ArrayList<PhylogeneticNode> leaves = getLeaves(getRoot(), new ArrayList<PhylogeneticNode>());
		while (leaves.size() != currentGenomes.size()) {
			for (PhylogeneticNode leaf : leaves) {
				if (!currentGenomes.contains(leaf.getNameLabel())) {
					leaf.getParent().removeChild(leaf);
				}
			}
			leaves = getLeaves(getRoot(), new ArrayList<PhylogeneticNode>());
		}	
	}
	
	/**
	 * Recursive algorithm to get all the leaves in a tree.
	 * @param node The current node.
	 * @param result The result.
	 * @return All the leaves.
	 */
	private ArrayList<PhylogeneticNode> getLeaves(PhylogeneticNode node, ArrayList<PhylogeneticNode> result) {
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
	
	/**
	 * Remove nodes with one child.
	 * @param currentGenomes The genomes that are present in the tree.
	 */
    private void removeRedundantNodes(ArrayList<String> currentGenomes) {
    	PhylogeneticNode rootNode = getRoot();
    	for (String genome : currentGenomes) {
    		PhylogeneticNode leaf = rootNode.getNodeWithLabel(genome);
    		PhylogeneticNode parent = leaf.getParent();
    		while (!parent.equals(rootNode)) {
    			if (parent.getChildren().size() == 2) {
    				leaf = parent;
    				parent = parent.getParent();
    			} else {
    				parent.getParent().removeChild(parent);
    				parent.getParent().addChild(leaf);
    				leaf.setParent(parent.getParent());
    				parent = leaf.getParent();
    			}
    		}
    	}
    	if (rootNode.getChildren().size() == 1) {
    		rootNode.getChildren().get(0).setParent(null);
    		setRoot(rootNode.getChildren().get(0));
    		getRoot().setId(0);
    	}
	}
    
    /**
     * Generate the id's for the tree.
     * @param node Start node.
     */
    private void generateId(PhylogeneticNode node) {
    	ArrayList<PhylogeneticNode> children = node.getChildren();
    	for (PhylogeneticNode child : children) {
    		child.setId(child.generateId(node, children.indexOf(child)));
    		generateId(child);
    	}
    }
}

