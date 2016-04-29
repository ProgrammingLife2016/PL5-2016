package genome;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import parser.Parser;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Datacontainer that stores the edges and nodes of a particular genome.
 */
//TODO test and commment better

public class DataContainer {
    private HashMap<Integer, Node> nodes;
    private HashMap<String, Edge> edges;
    private HashMap<String, Genome> genomes;
	public static DataContainer DC = Parser.parse("../data/TB10.gfa");
	
    public DataContainer() {
        nodes= new HashMap<>();
        edges= new HashMap<>();
        genomes = new HashMap<>();
    }

    public void addNode(int id, Node node){
        nodes.put(id, node);

        for(String genomeID : node.getGenomes()){
            if(!genomes.containsKey(genomeID)){
                genomes.put(genomeID, new Genome());
            }
            genomes.get(genomeID).addNode(node);
        }
    }

    public void addEdge (Edge edge){
        edges.put(edge.getStart() + "|" + edge.getEnd(), edge);
    }

    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashMap<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    public HashMap<String, Edge> getEdges() {
        return edges;
    }

    public void setEdges(HashMap<String, Edge> edges) {
        this.edges = edges;
    }

    public HashMap<String, Genome> getGenomes() {
        return genomes;
    }

    public HashMap<Integer, HashSet<Node>> calculateCoordinates(){
        //calculate the x-coordinates
        for(HashMap.Entry<String, Genome> entry : genomes.entrySet()){
            ArrayList<Node> currentGenomeNodes = entry.getValue().getNodes();

            currentGenomeNodes.get(0).updatex(0);
            Node prevNode = currentGenomeNodes.get(0);
            for(int i = 1; i < currentGenomeNodes.size(); i++){
                Node currentNode = currentGenomeNodes.get(i);
                currentGenomeNodes.get(i).updatex(i); //update the nodes x-coordinate

                Edge currentEdge = edges.get(prevNode.getId() + "|" + currentNode.getId());
                currentEdge.setWeight(currentEdge.getWeight()+1);
                prevNode = currentNode;
            }
        }

        HashMap<Integer, HashSet<Node>> nodesByx = new HashMap<>();
        for(HashMap.Entry<Integer, Node> entry : nodes.entrySet()){
            if(!nodesByx.containsKey((int) entry.getValue().getx())){
                nodesByx.put((int) entry.getValue().getx(), new HashSet<>());
            }
            nodesByx.get((int) entry.getValue().getx()).add(entry.getValue());
        }

        for(HashMap.Entry<Integer, HashSet<Node>> c : nodesByx.entrySet()){
            int y = 0;
            for(Node node : c.getValue()){
                node.sety(y);
                y++;
            }
        }

        return nodesByx;
    }

}
