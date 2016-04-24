/**
 * Created by Matthijs on 24-4-2016.
 */

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */
public class Edge {
    private Node start;
    private Node end;
    private int weight;


    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
        weight=0;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }
}
