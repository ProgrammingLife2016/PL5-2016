package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.StrandEdge;
import genome.Genome;
import genome.Strand;
import parser.Parser;

import java.util.Comparator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonEdge;
import ribbonnodes.RibbonNode;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Controller returns the ribbon nodes based on a UI request.
 */
public class Controller {

    //Todo move strand graph and genomes to seperate class.
    private HashMap<Integer, Strand> strandNodes;
    private HashMap<String, StrandEdge> strandEdges;
    private HashMap<String, Genome> genomes;

    private String newickString;

    private ArrayList<String> activeGenomes; //The current genomes selected in the GUI.
    private double dataWidth; // The with of the Data.
    private PhylogeneticTree phylogeneticTree; //The phylogenetic tree parsed from the dataFile.
    private DataTree dataTree; //The dataTree containing the Strands.


    /**
     * Datacontainer Singleton, starts with empty hashmaps.
     */
    public static final controller.Controller DC = Parser.parse("data/TB10.gfa");

    /**
     * Constructor.
     */
    public Controller() {
        strandNodes = new HashMap<>();
        strandEdges = new HashMap<>();
        activeGenomes = new ArrayList<>();
        genomes = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        dataTree = new DataTree(new DataNode((PhylogeneticNode) phylogeneticTree.getRoot(), 
        		null, 0));
        newickString = loadRawFileData("data/340tree.rooted.TKK.nwk");

    }

    /**
     * Get the ribbon nodes with edges for a certain view in the GUI.
     *
     * @param minX      the minx of the view.
     * @param maxX      the maxx of the view.
     * @param zoomLevel the zoomlevel of the view.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {
        ArrayList<RibbonNode> result = new ArrayList<>();
        ArrayList<DataNode> filteredNodes = 
        		dataTree.getDataNodes(minX, maxX, activeGenomes, zoomLevel);

        for (DataNode node : filteredNodes) {
            for (Strand strand : node.getStrands()) {
                //Here the nodes are placed in order 
            	//(notice node.getgenomes and not ribbon.getgenomes).
                RibbonNode ribbonNode = new RibbonNode(strand.getId(), node.getGenomes());
                result.add(ribbonNode);
            }
        }

        addRibbonEdges(result, activeGenomes);

        return result;

    }

    /**
     * Calculate and add the edges between the ribbon nodes.
     *
     * @param nodes   The ribbonnodes to connect.
     * @param genomes The active Genomes.
     */

    public void addRibbonEdges(ArrayList<RibbonNode> nodes, ArrayList<String> genomes) {
        nodes.sort(new Comparator<RibbonNode>() {
            @Override
            public int compare(RibbonNode o1, RibbonNode o2) {
                if (o1.getId() > o2.getId()) {
                    return 1;
                }
                else if (o1.getId() < o2.getId()) {
                    return -1;
                }
                return 0;
            }
        });
        for (String genome : genomes) {
            for (int i = 0; i < nodes.size(); i++) {
                RibbonNode startNode = nodes.get(i);

                if (startNode.getGenomes().contains(genome)) {
                    i++;
                    RibbonNode endNode = nodes.get(i);
                    while (!checkEdge(startNode, endNode, genome) && i < nodes.size()) {
                        i++;
                        endNode = nodes.get(i);
                    }
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

    public boolean checkEdge(RibbonNode startNode, RibbonNode endNode, String genomeID) {
        if (endNode.getGenomes().contains(genomeID)) {
            if (startNode.getEdge(startNode.getId(), endNode.getId()) == null) {
                RibbonEdge edge = new RibbonEdge(startNode.getId(), endNode.getId());
                startNode.addEdge(edge);
                endNode.addEdge(edge);

            } else {
                startNode.getEdge(startNode.getId(), endNode.getId()).incrementWeight();
            }
            return true;

        }
        return false;
    }

    /**
     * Adding a strand to the data.
     *
     * @param strand The added strand.
     */
    public void addStrand(Strand strand) {
        strandNodes.put(strand.getId(), strand);


        for (String genomeID : strand.getGenomes()) {
            if (!genomes.containsKey(genomeID)) {
                genomes.put(genomeID, new Genome(genomeID));

                //HARDCODED ACTIVE GENOMES
                if (!(activeGenomes.size() > 2)) {
                    activeGenomes.add(genomeID);
                }
            }
            genomes.get(genomeID).addStrand(strand);
        }
    }

    /**
     * Adding an StrandEdge to the data.
     *
     * @param strandEdge The added StrandEdge.
     */
    public void addEdge(StrandEdge strandEdge) {
        strandEdges.put(strandEdge.getStart() + "|" + strandEdge.getEnd(), strandEdge);
    }

    /**
     * Get all the Strand in the data.
     *
     * @return strandNodes.
     */
    public HashMap<Integer, Strand> getstrandNodes() {
        return strandNodes;

    }

    /**
     * Get all the edges in the data.
     *
     * @return Edges.
     */
    public HashMap<String, StrandEdge> getEdges() {
        return strandEdges;
    }


    /**
     * Get the data width.
     *
     * @return The data width.
     */
    public double getDataWidth() {
        return this.dataWidth;
    }


    /**
     * Set the data width.
     *
     * @param dataWidth New data width.
     */
    public void setDataWidth(double dataWidth) {
        this.dataWidth = dataWidth;
    }


    /**
     * Getter for the phylogenicTree.
     *
     * @return The tree.
     */
    public PhylogeneticTree getPhylogeneticTree() {
        return phylogeneticTree;
    }

    /**
     * Setter for the phylogenicTree.
     *
     * @param phylogeneticTree The tree.
     */
    public void setPhylogeneticTree(PhylogeneticTree phylogeneticTree) {
        this.phylogeneticTree = phylogeneticTree;
    }


    /**
     * Getter for the dataTree.
     *
     * @return the datatree.
     */
    public DataTree getDataTree() {
        return dataTree;
    }

    /**
     * Setter for the datatree.
     *
     * @param dataTree the datatree.
     */
    public void setDataTree(DataTree dataTree) {
        this.dataTree = dataTree;
    }

    /**
     * Getter for the genomes.
     *
     * @return the genomes.
     */
    public HashMap<String, Genome> getGenomes() {
        return genomes;
    }

    /**
     * Setter for the genomes.
     *
     * @param genomes The genomes.
     */
    public void setGenomes(HashMap<String, Genome> genomes) {
        this.genomes = genomes;
    }

    /**
     * The active genomes in the Gui.
     *
     * @return the active genomeIDS.
     */
    public ArrayList<String> getActiveGenomes() {
        return activeGenomes;
    }


    /** 
     * Setter for the activeGenomes.
     * @param activeGenomes The genomeIDS.
     */
    public void setActiveGenomes(ArrayList<String> activeGenomes) {
        this.activeGenomes = activeGenomes;
    }
    
    /**
     * Get the newick string.
     * @return String.
     */
    public String getNewickString() {
		return newickString;
	}
    

    /**
     * Load rar file data.
     * @param fileName The file.
     * @return String.
     */
	public String loadRawFileData(String fileName) {

		String rawFileData = "";

		try {
			BufferedReader reader;
			InputStream in = PhylogeneticTree.class.getClassLoader().getResourceAsStream(fileName);
			Reader r = new InputStreamReader(in, StandardCharsets.UTF_8);
			reader = new BufferedReader(r);
			String line = reader.readLine();
			while (line != null) {
				rawFileData = rawFileData + line;
				line = reader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return rawFileData;
	}
    
}
