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
        this.removeRedundantGenomes(getRoot(), currentGenomes);
        this.removeRedundantNodes(getRoot(), currentGenomes);
    }

	private void removeRedundantGenomes(PhylogeneticNode node, 
    		ArrayList<String> currentGenomes) {
    	PhylogeneticNode child1 = node.getChildren().get(0);
    	PhylogeneticNode child2 = node.getChildren().get(1);
    	if (child1.getNameLabel().equals("")) {
    		removeRedundantGenomes(child1, currentGenomes);    		
    	} else if (!currentGenomes.contains(child1.getNameLabel())) {
    		node.removeChild(child1);
    	}
    	if (child2.getNameLabel().equals("")) {
    		removeRedundantGenomes(child2, currentGenomes);    		
    	} else if (!currentGenomes.contains(child2.getNameLabel())) {
    		node.removeChild(child2);
    	}
    }

    private void removeRedundantNodes(PhylogeneticNode node, 
    		ArrayList<String> currentGenomes) {
    	for (String genome : currentGenomes) {
    		PhylogeneticNode leaf = node.getNodeWithLabel(genome);
    		PhylogeneticNode parent = leaf.getParent();
    		while (!leaf.equals(node) && parent.getChildren().size() < 2) {
        		parent = leaf.getParent();
    			parent.getParent().removeChild(parent);
    			parent.getParent().addChild(leaf);
    			leaf.setParent(parent.getParent());
    			leaf = leaf.getParent();
    		}
    	}
	}
}

