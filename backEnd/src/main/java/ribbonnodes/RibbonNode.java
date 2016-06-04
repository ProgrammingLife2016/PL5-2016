package ribbonnodes;

import genome.Strand;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonNode {
    
	/**
	 * The id of the Node.
	 */
	private int id;
	
	/**
	 * The x coordinate of the Node.
	 */
    private int x;
    
    /**
     * The y coordinate of the Node.
     */
    private int y;
    
    /**
     * The edges going in to the node.
     */
    private ArrayList<RibbonEdge> inEdges;
    
    /**
     * The edges going out of the node.
     */
    private ArrayList<RibbonEdge> outEdges;
    
    /**
     * The genomes that go through this node.
     */
    private HashSet<String> genomes;
    
    /**
     * The strands contained in this node.
     */
    private ArrayList<Strand> strands;
    
    /**
     * The entire strand contained in this node.
     */
    private String label;

    /**
     * Constructor for the RibbonNode.
     *
     * @param id      The id.
     * @param genomes The genomes.
     */
    public RibbonNode(int id, HashSet<String> genomes) {
        inEdges = new ArrayList<>();
        outEdges = new ArrayList<>();
        strands = new ArrayList<>();
        this.label = "";
        this.genomes = genomes;
        this.id = id;
        this.x = 0; //for now
        this.y = 0;
    }

    /**
     * Get the label.
     *
     * @return Label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get specific Inedge.
     *
     * @param idFrom Edge start id.
     * @param idTo   Edge end id.
     * @return Edge.
     */
    public RibbonEdge getInEdge(int idFrom, int idTo) {
        for (RibbonEdge edge : inEdges) {
            if (edge.getStart() == idFrom && edge.getEnd() == idTo) {
                return edge;
            }
        }
        return null;
    }


    /**
     * Get specific Outedge.
     *
     * @param idFrom Edge start id.
     * @param idTo   Edge end id.
     * @return Edge.
     */
    public RibbonEdge getOutEdge(int idFrom, int idTo) {
        for (RibbonEdge edge : outEdges) {
            if (edge.getStart() == idFrom && edge.getEnd() == idTo) {
                return edge;
            }
        }
        return null;
    }


    /**
     * Set the label.
     *
     * @param label Label.
     */
    public void setLabel(String label) {
        this.label = label;
        String newLabel = "";
        for (int i = 0; i < label.length(); i += 40) {
            String sub = label.substring(i, Math.min(i + 40, label.length()));
            newLabel += sub + "\n";
        }
        this.label = newLabel;

    }

    /**
     * Get the id.
     *
     * @return Id.
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id.
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the out edges.
     *
     * @return Edges.
     */
    public ArrayList<RibbonEdge> getOutEdges() {
        return outEdges;
    }

    /**
     * Get the incoming edges.
     *
     * @return InEdges.
     */
    public ArrayList<RibbonEdge> getInEdges() {
        return inEdges;
    }

    /**
     * Set the outgoing edges.
     *
     * @param outEdges New outgoing edges.
     */
    public void setOutEdges(ArrayList<RibbonEdge> outEdges) {
        this.outEdges = outEdges;
    }

    /**
     * Set the incoming edges.
     *
     * @param inEdges The new incoming edges.
     */
    public void setInEdges(ArrayList<RibbonEdge> inEdges) {
        this.inEdges = inEdges;
    }

    /**
     * Add an edge.
     *
     * @param edge Edge.
     */
    public void addEdge(RibbonEdge edge) {
        if (edge.getEnd() == this.getId()) {
            inEdges.add(edge);
        } else {
            outEdges.add(edge);
        }
    }


    /**
     * Get the genomes.
     *
     * @return Genomes.
     */
    public HashSet<String> getGenomes() {
        return genomes;
    }

    /**
     * Get x coordinate.
     *
     * @return X.
     */
    public int getX() {
        return x;
    }

    /**
     * Set x coordinate.
     *
     * @param x New x.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get y coordinate.
     *
     * @return Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the y coordinate.
     *
     * @param y New y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the strands.
     *
     * @return Strands.
     */
    public ArrayList<Strand> getStrands() {
        return strands;
    }

    /**
     * Add a multiple strands.
     *
     * @param strands Added strands.
     */
    public void addStrands(ArrayList<Strand> strands) {
        for (Strand strand : strands) {
            addStrand(strand);
        }
    }

    /**
     * Add a strand.
     *
     * @param strand Added strand.
     */
    public void addStrand(Strand strand) {
        this.strands.add(strand);
        this.setLabel(label + strand.getSequence() + "\n");
    }
}
