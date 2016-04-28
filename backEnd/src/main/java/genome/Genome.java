package genome;

import java.util.ArrayList;

public class Genome {
    private ArrayList<Node> nodes;

    public Genome(){
        nodes = new ArrayList<>();
    }

    public void addNode(Node node){
        nodes.add(node);
    }

    public ArrayList<Node> getNodes(){
        return nodes;
    }
}
