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
    private HashMap<String, Genome> genomes;
    private HashMap<String, Genome> test;
    
    public HashMap<String, Genome> getTest() { return test; }

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
        activeGenomes = new ArrayList<>();
        genomes = new HashMap<>();
        test = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        dataTree = new DataTree(new DataNode((PhylogeneticNode) phylogeneticTree.getRoot(), 
        		null, 0));
        newickString = loadRawFileData("data/340tree.rooted.TKK.nwk");

    }

    /**
     * Wrapper method that returns a list of filtered node for the particular query.
     * @param minX The minimal X of the nodes.
     * @param maxX The maximal X of the nodes.
     * @param zoomLevel The zoomlevel to filter to.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel){
        return RibbonController.getRibbonNodes(minX,maxX,zoomLevel,dataTree,activeGenomes, test, strandNodes);
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
                test.put(genomeID, new Genome(genomeID));
                //HARDCODED ACTIVE GENOMES
                if (!genomeID.equals("MT_H37RV_BRD_V5.ref.fasta")) {
                    activeGenomes.add(genomeID);
                }
            } else {
                genomes.get(genomeID).addStrand(strand);
                test.get(genomeID).addStrand(strand);
            }
        }
    }

    /**
     * Get all the Strand in the data.
     *
     * @return strandNodes.
     */
    public HashMap<Integer, Strand> getStrandNodes() {
        return strandNodes;

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
