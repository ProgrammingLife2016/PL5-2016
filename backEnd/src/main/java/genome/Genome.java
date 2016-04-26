package genome;
import java.util.ArrayList;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Datacontainer that stores the edges and nodes of a particular genome.
 */
//TODO test and commment better

public class Genome {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    public Genome() {
        nodes= new ArrayList<>();
        edges= new ArrayList<>();
    }

    public void addNode(Node node){
        nodes.add(node);
    }

    public void addEdge (Edge edge){
        edges.add(edge);
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
}
