package genome;

/**
 * Created by Matthijs on 24-4-2016.
 */

/***
 * data container class that stores the start and end of an edge, as well as its weight.
 */

//TODO test and commment better
public class Edge {
    private int startId;
    private int endId;
    private int weight;

    
    public Edge(int startId, int endId) {
    	this.startId = startId;
    	this.endId = endId;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getStart() {
        return startId;
    }

    public int getEnd() {
        return endId;
    }

    public int getWeight() {
        return weight;
    }
}
