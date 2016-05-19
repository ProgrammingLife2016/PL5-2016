package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Strand;
import genome.StrandEdge;
import genome.Genome;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Matthijs on 18-5-2016.
 */
public final class RibbonController {


    /**
     * Get the ribbon nodes with edges for a certain view in the GUI.
     *
     * @param minX          the minx of the view.
     * @param maxX          the maxx of the view.
     * @param zoomLevel     the zoomlevel of the view.
     * @param dataTree      the filled and processed tree of the data.
     * @param activeGenomes the genomes to filter for.
     * @return The list of ribbonNodes.
     */
    public static ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel, DataTree dataTree, 
    		ArrayList<String> activeGenomes, HashMap<String, Genome> genomes, HashMap<Integer, Strand> strands) {
        ArrayList<RibbonNode> result = new ArrayList<>();
        HashMap<Integer, RibbonNode> tempResult = new HashMap<>();
        ArrayList<DataNode> filteredNodes =
                dataTree.getDataNodes(minX, maxX, activeGenomes, 100);
        
        String reference = "MT_H37RV_BRD_V5.ref.fasta";
        Genome referenceGenome = genomes.get(reference);
        int x =  0;
        for (Strand strand : referenceGenome.getStrands()) {
        	RibbonNode ribbon = new RibbonNode(strand.getId(), strand.getGenomes());
        	ribbon.setX(x);
        	ribbon.setY(10);
        	for (StrandEdge edge : strand.getEdges()) {
        		ribbon.addEdge(new RibbonEdge(edge.getStart(), edge.getEnd()));
        	}
        	tempResult.put(ribbon.getId(), ribbon);
        	result.add(ribbon);
        	x += 10;
        }
        
        String reference2 = "TKK_02_0001.fasta";
        Genome genome = genomes.get(reference2);
        System.out.println("hier");
        System.out.println(genome.getStrands().size());
        x = 0;
        for (int i = 0; i < genome.getStrands().size(); i++) {
        	System.out.println(i);
        	Strand strand = genome.getStrands().get(i);
        	if (!tempResult.containsKey(strand.getId())) {
        		x += 10;
        		while (!tempResult.containsKey(strand.getId())) {
        			if (i + 1 == genome.getStrands().size()) {
        				break;
        			}
        			Strand temp = genome.getStrands().get(i + 1);     
        			RibbonNode ribbon = new RibbonNode(strand.getId(), strand.getGenomes());
        			ribbon.setX(x);
        			ribbon.setY(50);
        			ribbon.addEdge(new RibbonEdge(ribbon.getId(), temp.getId()));
        			i++;
        			tempResult.put(ribbon.getId(), ribbon);
        			result.add(ribbon);
        			strand = genome.getStrands().get(i);
        		}
        		
        	} else {
        		x = tempResult.get(strand.getId()).getX();
        	}
        }
        
//        for (DataNode node : filteredNodes) {
//            for (Strand strand : node.getStrands()) {
//                //Here the nodes are placed in order
//                //(notice node.getgenomes and not ribbon.getgenomes).
//                RibbonNode ribbonNode = new RibbonNode(strand.getId(), strand.getGenomes());
//                ribbonNode.addStrand(strand);
//                ribbonNode.setX(strand.getId());
//                ribbonNode.setY(strand.getWeight() * 10); //TODO find way to structure more clearly
//                result.add(ribbonNode);
//            }
//        }

        //addRibbonEdges(result, activeGenomes);
        //result = collapseBubbles(result);
        //addRibbonEdges(result, activeGenomes);


        return result;

    }

    /**
     * Calculate and add the edges between the ribbon nodes.
     *
     * @param nodes   The ribbonnodes to connect.
     * @param genomes The active Genomes.
     */

    public static void addRibbonEdges(ArrayList<RibbonNode> nodes, ArrayList<String> genomes) {
        //Sort on Id since toplevel have a low id.
        nodes.sort(new Comparator<RibbonNode>() {
            @Override
            public int compare(RibbonNode o1, RibbonNode o2) {
                if (o1.getId() > o2.getId()) {
                    return 1;
                } else if (o1.getId() < o2.getId()) {
                    return -1;
                }
                return 0;
            }
        });
        for (String genome : genomes) {
            System.out.println(genome);
            for (int i = 0; i < nodes.size(); i++) {
                RibbonNode startNode = nodes.get(i);

                if (startNode.getGenomes().contains(genome) && i + 1 < nodes.size()) {
                    int j = i + 1;
                    RibbonNode endNode = nodes.get(j);
                    while (!checkEdge(startNode, endNode, genome) && j < nodes.size()) {
                        endNode = nodes.get(j);
                        j++;

                    }
                    i = j - 1;
                }
            }
        }
    }


    /**
     * Check if an edge should exist between two ribbon nodes, and add it if it should.
     *
     * @param startNode The start node.
     * @param endNode   The end node.
     * @param genomeID  The Genome of this path.
     * @return True if edge was found.
     */

    public static boolean checkEdge(RibbonNode startNode, RibbonNode endNode, String genomeID) {

        if (endNode.getGenomes().contains(genomeID)) {
            if (startNode.getOutEdge(startNode.getId(), endNode.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(startNode.getId(), endNode.getId());
                startNode.addEdge(edge);
                endNode.addEdge(edge);

            } else {
                startNode.getOutEdge(startNode.getId(), endNode.getId()).incrementWeight();
            }
            return true;

        }
        return false;
    }

    public static RibbonNode getNodeWithId(int id, ArrayList<RibbonNode> nodes) {
        for (RibbonNode node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }
        return null;
    }

    public static RibbonNode collapseRibbonNodes(RibbonNode node, RibbonNode other) {
        RibbonNode bubble = new RibbonNode(node.getId(), node.getGenomes());
        bubble.addStrands(node.getStrands());
        bubble.addStrands(other.getStrands());
        bubble.setX((node.getX() + other.getX()) / 2);
        bubble.setY((node.getY() + other.getY()) / 2);
        bubble.setInEdges(node.getInEdges());
        for (RibbonEdge edge : other.getEdges()) {
            RibbonEdge newOut = edge;
            newOut.setStartId(bubble.getId());
            bubble.addEdge(newOut);
        }


        return bubble;
    }

    public static ArrayList<RibbonNode> collapseBubbles(ArrayList<RibbonNode> nodes) {
        ArrayList<RibbonNode> collapsed = new ArrayList<>();
        ArrayList<RibbonNode> result = new ArrayList<>();

        do {
            collapsed = new ArrayList<>();
            result = new ArrayList<>();
            for (RibbonNode node : nodes) {
                if (node.getEdges().size() == 1 && !collapsed.contains(node)) {
                    if (getNodeWithId(node.getEdges().get(0).getEnd(), nodes) != null) {
                        RibbonNode other = getNodeWithId(node.getEdges().get(0).getEnd(), nodes);
                        if (other.getInEdges().size() == 1 && !collapsed.contains(other)) {
                            collapsed.add(node);
                            collapsed.add(other);
                            result.add(collapseRibbonNodes(node, other));
                        }


                    } else {
                        result.add(node);
                    }
                }


            }
            nodes = result;

        } while (collapsed.size() != 0);

        for (RibbonNode node : result) {
            node.setInEdges(new ArrayList<>());
            node.setOutEdges(new ArrayList<>());
        }

        return result;

    }
}
