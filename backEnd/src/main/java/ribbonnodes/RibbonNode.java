package ribbonnodes;

import java.util.ArrayList;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonNode {
    private int id;
    private ArrayList<RibbonEdge> edges;
    private ArrayList<String> genomes;
    private String label;

    /**
     * Constructor for the RibbonNode.
     * @param id The id.
     * @param genomes The genomes.
     */
    public RibbonNode(int id, ArrayList<String> genomes) {
        edges = new ArrayList<>();
        this.genomes = genomes;
        this.id = id;
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


}