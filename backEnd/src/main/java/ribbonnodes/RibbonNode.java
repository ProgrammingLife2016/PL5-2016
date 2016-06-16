package ribbonnodes;

import genome.Strand;
import mutation.AbstractMutation;

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
     * Draw the node or not.
     */
    private boolean isVisible;
    
    /**
     * The mutation starting from this node.
     */
    private ArrayList<AbstractMutation> mutations;

    private boolean yFixed;

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
        this.isVisible = true;
        mutations = new ArrayList<>();
        yFixed=false;
    }

    /**
     * Get the label, with new lines.
     *
     * @return Label.
     */
    public String getLabel() {
        return this.genomes.toString() + " " + label;
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
            if (edge.getStart().getId() == idFrom && edge.getEnd().getId() == idTo) {
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
            if (edge.getStart().getId() == idFrom && edge.getEnd().getId() == idTo) {
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
        for (RibbonEdge edge : this.outEdges) {
            edge.setStart(this);
        }
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
        if (edge.getEnd() == this) {
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
        this.setLabel(label + strand.getSequence());
    }


    /**
     * Getter for is visible.
     *
     * @return whether node is visible.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Setter for visible.
     *
     * @param visible true if node should be drawn.
     */
    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    
    /**
     * Get the mutations starting from this node.
     * @return The mutations.
     */
    public ArrayList<AbstractMutation> getMutations() {
    	return mutations;
    }
    
    /**
     * Add a mutation.
     * @param mutation Added mutation.
     */
    public void addMutation(AbstractMutation mutation) {
    	mutations.add(mutation);
    }
    
    /**
     * Checks if there is a mutation on this node.
     * @return Boolean type.
     */
    public boolean hasMutation() {
    	return mutations.size() > 0;
    }



    public boolean isyFixed() {
        return yFixed;
    }

    public void setyFixed(boolean yFixed) {
        this.yFixed = yFixed;
    }
}
