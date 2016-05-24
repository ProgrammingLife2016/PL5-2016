package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Genome;
import genome.Strand;
import genome.StrandEdge;
import parser.Parser;
import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Controller returns the ribbon nodes based on a UI request.
 */
public class Controller implements FrontEndBackEndInterface {

    //Todo move strand graph and genomes to seperate class.
    private HashMap<Integer, Strand> strandNodes;
    private HashMap<String, Genome> genomes;
    private HashMap<String, Genome> temp;

    private String newickString;

    private ArrayList<String> activeGenomes; //The current genomes selected in the GUI.
    private double dataWidth; // The with of the Data.
    private PhylogeneticTree phylogeneticTree; //The phylogenetic tree parsed from the dataFile.
    private DataTree dataTree; //The dataTree containing the Strands.


    /**
     * Datacontainer Singleton, starts with empty hashmaps.
     */
    //public static final controller.Controller DC = Parser.parse("data/TB10.gfa");
    public static final Controller DC = Parser.parse("data/TB10.gfa");

    /**
     * Constructor.
     * @param dataFile where the datafile is stored.
     * @param phyloTree where the phylogenetic tree file is stored.
     */
    public Controller(String dataFile, String phyloTree) {
        strandNodes = new HashMap<>();
        activeGenomes = new ArrayList<>();
        genomes = new HashMap<>();
        temp = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        //phylogeneticTree.parseTree("testGenomeNwk");
        dataTree = new DataTree(new DataNode((PhylogeneticNode) phylogeneticTree.getRoot(), 
        		null, 0));
        newickString = loadRawFileData(phyloTree);

    }

    /**
     * Wrapper method that returns a list of filtered node for the particular query.
     * @param minX The minimal X of the nodes.
     * @param maxX The maximal X of the nodes.
     * @param zoomLevel The zoomlevel to filter to.
     * @return The list of ribbonNodes.
     */
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {
        return RibbonController.getRibbonNodes(minX, maxX, zoomLevel, 
        		dataTree, activeGenomes, temp);
    }

    /**
     * Add an outgoing edge to the correct Strand.
     * @param edge The edge to add.
     */
    public void addEdge(StrandEdge edge) {
        getStrandNodes().get(edge.getStart()).addEdge(edge);
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
                temp.put(genomeID, new Genome(genomeID));
                //HARDCODED ACTIVE GENOMES
                if (!genomeID.equals("MT_H37RV_BRD_V5.ref.fasta")) {
                    activeGenomes.add(genomeID);
                }
            } else {
                genomes.get(genomeID).addStrand(strand);
                temp.get(genomeID).addStrand(strand);
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
     * @param treeId the tree id
     * @return The tree.
     */
    public PhylogeneticTree loadPhylogeneticTree(int treeId) {
		if (treeId == 0) {
			phylogeneticTree = new PhylogeneticTree();
			phylogeneticTree.parseTree("testGenomeNwk");
			return phylogeneticTree;
		} else {
			return phylogeneticTree;
		}
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
     * @param fileName The file to be loaded.
     * @return String return the raw data file.
     */
	public String loadRawFileData(String fileName) {

		StringBuffer rawFileData = new StringBuffer();
		BufferedReader reader = null;
		
		try {
			InputStream in = PhylogeneticTree.class.getClassLoader().getResourceAsStream(fileName);
			Reader r = new InputStreamReader(in, StandardCharsets.UTF_8);
			reader = new BufferedReader(r);
			String line = reader.readLine();
			while (line != null) {
				rawFileData.append(line);
				line = reader.readLine();
			}
            reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return rawFileData.toString();
	}
}
