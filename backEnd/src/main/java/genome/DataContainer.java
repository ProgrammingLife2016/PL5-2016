package genome;

import parser.Parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import phylogenetictree.PhylogeneticTree;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Datacontainer that stores the edges and nodes of a particular genome.
 */
public class DataContainer {

    private HashMap<Integer, Node> nodes;
    private HashMap<String, Edge> edges;
    private HashMap<String, Genome> genomes;
    private double dataWidth;
    private double dataHeight;
    private PhylogeneticTree phylogeneticTree;


    /**
     * Constructer for the datacontainer, starts with empty hashmaps.
     */
    public static final DataContainer DC = Parser.parse("data/TB10.gfa");

    /**
     * Constructor.
     */
    public DataContainer() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
        genomes = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
    }

    /**
     * Adding a node to the data.
     *
     * @param node The added node.
     */
    public void addNode(Node node) {
        nodes.put(node.getId(), node);

        for (String genomeID : node.getGenomes()) {
            if (!genomes.containsKey(genomeID)) {
                genomes.put(genomeID, new Genome(genomeID));
            }
            genomes.get(genomeID).addNode(node);
        }
    }

    /**
     * Adding an edge to the data.
     *
     * @param edge The added edge.
     */
    public void addEdge(Edge edge) {
        edges.put(edge.getStart() + "|" + edge.getEnd(), edge);
    }

    /**
     * Get all the node in the data.
     *
     * @return Nodes.
     */
    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    /**
     * Get all the edges in the data.
     *
     * @return Edges.
     */
    public HashMap<String, Edge> getEdges() {
        return edges;
    }
    
    /**
     * Compute and order all the nodes according to their x and y coordinate.
     * @return The ordered set.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public HashMap<Integer, HashSet<Node>> calculateCoordinates() {
        double maxWidth = 0;
        double maxHeight = 0;

        // calculate the x-coordinates
        for (HashMap.Entry<String, Genome> entry : genomes.entrySet()) {
            ArrayList<Node> currentGenomeNodes = entry.getValue().getNodes();

            currentGenomeNodes.get(0).updatexCoordinate(0);
            Node prevNode = currentGenomeNodes.get(0);
            for (int i = 1; i < currentGenomeNodes.size(); i++) {
                Node currentNode = currentGenomeNodes.get(i);
                currentGenomeNodes.get(i).updatexCoordinate(i); // update the
                // nodes
                // x-coordinate

                Edge currentEdge = edges.get(prevNode.getId() + "|" + currentNode.getId());
                currentEdge.setWeight(currentEdge.getWeight() + 1);
                prevNode = currentNode;
            }
        }

        HashMap<Integer, HashSet<Node>> nodesByxCoordinate = new HashMap<>();
        for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) {
            if (!nodesByxCoordinate.containsKey((int) entry.getValue().getxCoordinate())) {
                nodesByxCoordinate.put((int) entry.getValue().getxCoordinate(), new HashSet<>());
            }
            nodesByxCoordinate.get((int) entry.getValue().getxCoordinate()).add(entry.getValue());
        }

        for (HashMap.Entry<Integer, HashSet<Node>> c : nodesByxCoordinate.entrySet()) {
            int y = 0;
            for (Node node : c.getValue()) {
                node.setyCoordinate(y);
                maxHeight = Math.max(maxHeight, y);
                maxWidth = Math.max(maxWidth, node.getxCoordinate());
                y++;
            }
        }

        dataWidth = maxWidth;
        dataHeight = maxHeight;

        return nodesByxCoordinate;
    }

    /**
     * Get the data width.
     *
     * @return The data width.
     */
    public double getDataWidth() {
        return this.dataWidth;
    }

    /**
     * Get the data height.
     *
     * @return The data height.
     */
    public double getDataHeight() {
        return this.dataHeight;
    }

    /**
     * Set the data width.
     *
     * @param dataWidth New data width.
     */
    public void setDataWidth(double dataWidth) {
        this.dataWidth = dataWidth;
    }

    /**
     * Set the data height.
     *
     * @param dataHeight New data height.
     */
    public void setDataHeight(double dataHeight) {
        this.dataHeight = dataHeight;
    }

    /**
     * Get the nodes in a certain area, on a certain zoomlevel based on that area.
     *
     * @param xleft  The leftmost coordinate.
     * @param ytop   The upper coordinate.
     * @param xright The rightmost coordinate.
     * @param ybtm   The lower coordinate.
     * @return A list of nodes that fall into this zoomlevel and area.
     */
    public CopyOnWriteArrayList<Node> getNodes(double xleft,
                                               double ytop,
                                               double xright,
                                               double ybtm) {

        CopyOnWriteArrayList<Node> res = new CopyOnWriteArrayList<Node>();
        ArrayList<Node> correctNodes = new ArrayList<>();
        for (Node n : nodes.values()) {
            if (n.getxCoordinate() < xright
                    && n.getxCoordinate() > xleft
                    && n.getyCoordinate() > ytop
                    && n.getyCoordinate() < ybtm) {
                correctNodes.add(n);
            }
        }

        Collections.sort(correctNodes, (n1, n2) -> n2.getWeight() - n1.getWeight());

        int count = 0;
        for (Node n : correctNodes) {

            res.add(n);
            count++;

            if (count == 20) {
                break;
            }

        }

        return res;
    }

    /**
     * Getter for the phylogenicTree.
     *
     * @return The tree.
     */
    public PhylogeneticTree getPhylogeneticTree() {
        return phylogeneticTree;
    }

    /**
     * Setter for the phylogenicTree.
     * @param phylogeneticTree The tree.
     */
    public void setPhylogeneticTree(PhylogeneticTree phylogeneticTree) {
        this.phylogeneticTree = phylogeneticTree;
    }
    
    /**
	 * Get all the genomes in the data.
	 * @return Genomes.
	 */
	public HashMap<String, Genome> getGenomes() {
		return genomes;
	}

}
