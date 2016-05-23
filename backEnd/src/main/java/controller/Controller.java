package controller;

import datatree.DataNode;
import datatree.DataTree;
import genome.Genome;
import genome.Strand;
import parser.Parser;

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
import ribbonnodes.RibbonNode;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Controller returns the ribbon nodes based on a UI request.
 */
public class Controller implements FrontEndBackEndInterface {
    
    private GenomeGraph genomeGraph;
    private String newickString;
    private double dataWidth; // The with of the Data.
    private PhylogeneticTree phylogeneticTree; //The phylogenetic tree parsed from the dataFile.
    private DataTree dataTree; //The dataTree containing the Strands.


    /**
     * Datacontainer Singleton, starts with empty hashmaps.
     */
    public static final controller.Controller DC = new Controller();
    		

    /**
     * Constructor.
     */
    public Controller() {
    	genomeGraph = Parser.parse("data/TB10.gfa");
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        //phylogeneticTree.parseTree("testGenomeNwk");
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
    public ArrayList<RibbonNode> getRibbonNodes(int minX, int maxX, int zoomLevel) {
        return RibbonController.getRibbonNodes(minX, maxX, zoomLevel, 
        		dataTree, activeGenomes, temp);
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
    public void setDataWifdth(double dataWidth) {
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
     * Setter for the activeGenomes.
     * @param activeGenomes The genomeIDS.
     */
    public void setActiveGenomes(ArrayList<String> activeGenomes) {
        genomeGraph.setActiveGenomes(activeGenomes);
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
