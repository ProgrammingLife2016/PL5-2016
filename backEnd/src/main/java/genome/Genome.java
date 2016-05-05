package genome;

import java.util.ArrayList;

/**
 * 
 * @author Jeffrey Helgers
 *
 */
public class Genome {
    private ArrayList<Node> nodes;

    /**
     * Constructor to create a new genome.
     */
    public Genome() {
        nodes = new ArrayList<>();
    }

    /**
     * Add a node to the genome.
     * @param node The added node.
     */
    public void addNode(Node node) {
        nodes.add(node);
    }

    /**
     * Get all the nodes from the genome.
     * @return The nodes the genome passes through.
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
