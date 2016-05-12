package ribbonnodes;

import java.util.ArrayList;

/**
 * Created by Matthijs on 12-5-2016.
 */
public class RibbonNode {
    protected int id;
    protected ArrayList<RibbonEdge> edges;
    protected ArrayList<String> genomes;
    protected String label;


    public RibbonNode(int id, ArrayList<String> genomes) {
        edges = new ArrayList<>();
        this.genomes = genomes;
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public RibbonEdge getEdge(int idFrom, int idTo) {
        for (RibbonEdge edge : edges) {
            if (edge.getStart() == idFrom && edge.getEnd() == idTo) {
                return edge;
            }
        }
        return null;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public ArrayList<RibbonEdge> getEdges() {
        return edges;
    }

    public void addEdge(RibbonEdge edge) {
        edges.add(edge);
    }

    public void setEdges(ArrayList<RibbonEdge> edges) {
        this.edges = edges;
    }

    public ArrayList<String> getGenomes() {
        return genomes;
    }


}
