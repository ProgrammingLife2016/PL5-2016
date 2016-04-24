/**
 * Created by Matthijs on 24-4-2016.
 */

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */

//TODO test and commment better
public class Edge {
    private Node start;
    private Node end;
    private int startId;
    private int endId;
    private int weight;


    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
        weight=0;
    }
    
    public Edge(int startId, int endId) {
    	this.startId = startId;
    	this.endId = endId;
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
