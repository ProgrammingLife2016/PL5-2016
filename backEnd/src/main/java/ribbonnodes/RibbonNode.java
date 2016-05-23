package ribbonnodes;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonNode {
    private int id; // The id of this ribbon.
    private ArrayList<RibbonEdge> edges; //The edges attached to this ribbonNode.
    private ArrayList<String> genomes; // The genomes that have this node.
    private String label; //The label of this node.
    private Color color; //The color of this node.

    /**
     * Constructor for the RibbonNode.
     * @param id The id.
     * @param genomes The genomes.
     */
    public RibbonNode(int id, ArrayList<String> genomes) {
        edges = new ArrayList<>();
        this.genomes = genomes;
        this.id = id;
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
}