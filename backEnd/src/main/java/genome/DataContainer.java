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
public class DataContainer {
    private HashMap<Integer, Node> nodes;
    private HashMap<String, Edge> edges;
    private HashMap<String, Genome> genomes;
<<<<<<< HEAD
    private double dataWidth;
    private double dataHeight;
	public static DataContainer DC = Parser.parse("../data/TB10.gfa");
	
=======

    /**
     * Constructer for the datacontainer, starts with empty hashmaps.
     */
>>>>>>> master
    public DataContainer() {
        nodes= new HashMap<>();
        edges= new HashMap<>();
        genomes = new HashMap<>();
    }

    /**
     * Adding a node to the data.
     * @param node The added node.
     */
    public void addNode(Node node){
        nodes.put(node.getId(), node);
        
        for(String genomeID : node.getGenomes()){
            if(!genomes.containsKey(genomeID)){
                genomes.put(genomeID, new Genome());
            }
            genomes.get(genomeID).addNode(node);
        }
    }

    /**
     * Adding an edge to the data.
     * @param edge The added edge.
     */
    public void addEdge (Edge edge){
        edges.put(edge.getStart() + "|" + edge.getEnd(), edge);
    }

    /**
     * Get all the node in the data.
     * @return Nodes.
     */
    public HashMap<Integer, Node> getNodes() {
        return nodes;
    }

    /**
     * Get all the edges in the data.
     * @return Edges.
     */
    public HashMap<String, Edge> getEdges() {
        return edges;
    }

    /**
     * Compute and order all the nodes according to their x and y coordinate.
     * @return The ordered set.
     */
    public HashMap<Integer, HashSet<Node>> calculateCoordinates(){
        //calculate the x-coordinates
        for(HashMap.Entry<String, Genome> entry : genomes.entrySet()){
            ArrayList<Node> currentGenomeNodes = entry.getValue().getNodes();

            currentGenomeNodes.get(0).updateX(0);
            Node prevNode = currentGenomeNodes.get(0);
            for(int i = 1; i < currentGenomeNodes.size(); i++){
                Node currentNode = currentGenomeNodes.get(i);
                currentGenomeNodes.get(i).updateX(i); //update the nodes x-coordinate

                Edge currentEdge = edges.get(prevNode.getId() + "|" + currentNode.getId());
                currentEdge.setWeight(currentEdge.getWeight()+1);
                prevNode = currentNode;
            }
        }

        HashMap<Integer, HashSet<Node>> nodesByxCoordinate = new HashMap<>();
        for(HashMap.Entry<Integer, Node> entry : nodes.entrySet()){
            if(!nodesByxCoordinate.containsKey((int) entry.getValue().getX())){
                nodesByxCoordinate.put((int) entry.getValue().getX(), new HashSet<>());
            }
            nodesByxCoordinate.get((int) entry.getValue().getX()).add(entry.getValue());
        }

        for(HashMap.Entry<Integer, HashSet<Node>> c : nodesByxCoordinate.entrySet()){
            int y = 0;
            for(Node node : c.getValue()){
                node.setY(y);
                y++;
            }
        }

        return nodesByxCoordinate;
    }
    
    public void computeCoordinates(){
    	double maxYvalue = 0.0;
    	double maxXvalue = 0.0;
        for(HashMap.Entry<String, Genome> entry : genomes.entrySet()){
            ArrayList<Node> currentGenomeNodes = entry.getValue().getNodes();

            currentGenomeNodes.get(0).updateX(0);
            Node prevNode = currentGenomeNodes.get(0);
            for(int i = 1; i < currentGenomeNodes.size(); i++){
                Node currentNode = currentGenomeNodes.get(i);
                currentGenomeNodes.get(i).updateX(i); //update the nodes x-coordinate
                maxXvalue = Math.max(maxXvalue, currentGenomeNodes.get(i).getX());
                Edge currentEdge = edges.get(prevNode.getId() + "|" + currentNode.getId());
                currentEdge.setWeight(currentEdge.getWeight()+1);
                prevNode = currentNode;
            }
        }
        int y = 1;
        for(HashMap.Entry<String, Genome> entry : genomes.entrySet()){
        	maxYvalue = Math.max(maxYvalue, y);
        	ArrayList<Node> currentGenomeNodes = entry.getValue().getNodes();
            for(int i = 0; i < currentGenomeNodes.size(); i++){
                currentGenomeNodes.get(i).setY(y); //update the nodes x-coordinate
            }
            y++;
        }
        setDataHeight(maxYvalue);
        setDataWidth(maxXvalue);
    }

	public double getDataWidth() {
		return this.dataWidth;
	}

	public double getDataHeight() {
		return this.dataHeight;
	}

	public void setDataWidth(double dataWidth) {
		this.dataWidth = dataWidth;
	}

	public void setDataHeight(double dataHeight) {
		this.dataHeight = dataHeight;
	}
    

}
