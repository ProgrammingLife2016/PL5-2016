package ribbonnodes;

import genome.Strand;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonNode {
    private int id; // The id of this ribbon.
    private int x; //The x value of this ribbon Node.
    private int y; //The y value of this ribbon Node.
    private ArrayList<RibbonEdge> edges; //The edges attached to this ribbonNode.
    private ArrayList<String> genomes; // The genomes that have this node.
    private ArrayList<Strand> strands; //The strands contained in this RibbonNode.
    private String label; //The label of this node.
    private Color color; //The color of this node.

    /**
     * Constructor for the RibbonNode.
     * @param id The id.
     * @param genomes The genomes.
     */
    public RibbonNode(int id, String[] genomes) {
        edges = new ArrayList<>();
        this.genomes = new ArrayList<>(Arrays.asList(genomes));
        this.strands = new ArrayList<>();
        this.id = id;
        this.x=0;
        this.y=0;
        this.color= Color.black;

    }

    /**
     * Get the label.
     * @return Label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get specific edge.
     * @param idFrom Edge start id.
     * @param idTo Edge end id.
     * @return Edge.
     */
    public RibbonEdge getEdge(int idFrom, int idTo) {
        for (RibbonEdge edge : edges) {
            if (edge.getStart() == idFrom && edge.getEnd() == idTo) {
                return edge;
            }
        }
        return null;
    }

    /**
     * Set the label.
     * @param label Label.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the id.
     * @return Id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the edges.
     * @return Edges.
     */
    public ArrayList<RibbonEdge> getEdges() {
        return edges;
    }

    /**
     * Add an edge.
     * @param edge Edge.
     */
    public void addEdge(RibbonEdge edge) {
        edges.add(edge);
    }

    /**
     * Set the edges.
     * @param edges Edges.
     */
    public void setEdges(ArrayList<RibbonEdge> edges) {
        this.edges = edges;
    }

    /**
     * Get the genomes.
     * @return Genomes.
     */
    public ArrayList<String> getGenomes() {
        return genomes;
    }

    /**
     * The color this ribbon has.
     * @return the color of this ribbon.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Set the color of this ribbon.
     * @param color the color.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * The getter of the x of this node.
     * @return the x value .
     */
    public int getX() {
        return x;
    }

    /**
     * The setter of the x of this node.
     * @param x the x value.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * The getter of the y value of this node.
     * @return The y value.
     */
    public int getY() {
        return y;
    }

    /**
     * The setter of the y value of this node.
     * @param y the y value.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * The getter of strands.
     * @return The strands contained in this node.
     */
    public ArrayList<Strand> getStrands(){return strands;}

    /**
     * Add a strand to this RibbonNode.
     * @param strand the strands contained in this node.
     */
    public void addStrand(Strand strand){strands.add(strand);}
}