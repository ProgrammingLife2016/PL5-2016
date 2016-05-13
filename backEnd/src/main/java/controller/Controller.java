package controller;

import genome.Edge;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

import phylogenetictree.PhylogeneticTree;

/**
 * Created by Matthijs on 24-4-2016.
 */

/**
 * Datacontainer that stores the edges and Strands of a particular genome.
 */
public class Controller {


    private HashMap<Integer, Strand> strands;
    private HashMap<String, Edge> edges;
    private HashMap<String, Genome> genomes;
    private double dataWidth;
    private double dataHeight;
    private PhylogeneticTree phylogeneticTree;
    private String newickString;

    /**
     * Constructer for the datacontainer, starts with empty hashmaps.
     */
    public static final controller.Controller DC = Parser.parse("data/TB10.gfa");

    /**
     * Constructor.
     */
    public Controller() {
        strands = new HashMap<>();
        edges = new HashMap<>();
        genomes = new HashMap<>();
        phylogeneticTree = new PhylogeneticTree();
        phylogeneticTree.parseTree("data/340tree.rooted.TKK.nwk");
        newickString = loadRawFileData("data/340tree.rooted.TKK.nwk");
    }

    /**
     * Adding a strand to the data.
     *
     * @param strand The added strand.
     */
    public void addStrand(Strand strand) {
        strands.put(strand.getId(), strand);

        for (String genomeID : strand.getGenomes()) {
            if (!genomes.containsKey(genomeID)) {
                genomes.put(genomeID, new Genome(genomeID));
            }
            genomes.get(genomeID).addStrand(strand);
        }
    }

    /**
     * Adding an edge to the data.
     *
     * @param edge The added edge.
     */
    public void addEdge(Edge edge) {
        edges.put(edge.getStart() + "|" + edge.getEnd(), edge);
    }

    /**
     * Get all the Strand in the data.
     *
     * @return Strands.
     */
    public HashMap<Integer, Strand> getStrands() {
        return strands;
    }

    /**
     * Get all the edges in the data.
     *
     * @return Edges.
     */
    public HashMap<String, Edge> getEdges() {
        return edges;
    }

    /**
     * Compute and order all the Strands according to their x and y coordinate.
     *
     * @return The ordered set.
     */
    @SuppressWarnings("checkstyle:methodlength")
    public HashMap<Integer, HashSet<Strand>> calculateCoordinates() {
        double maxWidth = 0;
        double maxHeight = 0;

        // calculate the x-coordinates
        for (HashMap.Entry<String, Genome> entry : genomes.entrySet()) {
            ArrayList<Strand> currentGenomeStrands = entry.getValue().getStrands();

            currentGenomeStrands.get(0).updatexCoordinate(0);
            Strand prevStrand = currentGenomeStrands.get(0);
            for (int i = 1; i < currentGenomeStrands.size(); i++) {
                Strand currentStrand = currentGenomeStrands.get(i);
                currentGenomeStrands.get(i).updatexCoordinate(i); // update the
                // Strands
                // x-coordinate

                Edge currentEdge = edges.get(prevStrand.getId() + "|" + currentStrand.getId());
                currentEdge.setWeight(currentEdge.getWeight() + 1);
                prevStrand = currentStrand;
            }
        }

        HashMap<Integer, HashSet<Strand>> strandsByxCoordinate = new HashMap<>();
        for (HashMap.Entry<Integer, Strand> entry : strands.entrySet()) {
            if (!strandsByxCoordinate.containsKey((int) entry.getValue().getxCoordinate())) {
                strandsByxCoordinate.put((int) entry.getValue().getxCoordinate(), new HashSet<>());
            }
            strandsByxCoordinate.get((int) entry.getValue().getxCoordinate()).add(entry.getValue());
        }

        for (HashMap.Entry<Integer, HashSet<Strand>> c : strandsByxCoordinate.entrySet()) {
            int y = 0;
            for (Strand strand : c.getValue()) {
                strand.setyCoordinate(y);
                maxHeight = Math.max(maxHeight, y);
                maxWidth = Math.max(maxWidth, strand.getxCoordinate());
                y++;
            }
        }

        dataWidth = maxWidth;
        dataHeight = maxHeight;

        return strandsByxCoordinate;
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
     * Get the data height.
     *
     * @return The data height.
     */
    public double getDataHeight() {
        return this.dataHeight;
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
     * Set the data height.
     *
     * @param dataHeight New data height.
     */
    public void setDataHeight(double dataHeight) {
        this.dataHeight = dataHeight;
    }

    /**
     * Get the Strands in a certain area, on a certain zoomlevel based on that area.
     *
     * @param xleft  The leftmost coordinate.
     * @param ytop   The upper coordinate.
     * @param xright The rightmost coordinate.
     * @param ybtm   The lower coordinate.
     * @return A list of Strands that fall into this zoomlevel and area.
     */
    public CopyOnWriteArrayList<Strand> getStrands(double xleft,
                                                 double ytop,
                                                 double xright,
                                                 double ybtm) {

        CopyOnWriteArrayList<Strand> res = new CopyOnWriteArrayList<Strand>();
        ArrayList<Strand> correctStrands = new ArrayList<>();
        for (Strand n : strands.values()) {
            if (n.getxCoordinate() < xright
                    && n.getxCoordinate() > xleft
                    && n.getyCoordinate() > ytop
                    && n.getyCoordinate() < ybtm) {
                correctStrands.add(n);
            }
        }

        Collections.sort(correctStrands, (n1, n2) -> n2.getWeight() - n1.getWeight());

        int count = 0;
        for (Strand n : correctStrands) {

            res.add(n);
            count++;

            if (count == 20) {
                break;
            }

        }

        return res;
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
     * Get all the genomes in the data.
     *
     * @return Genomes.
     */
    public HashMap<String, Genome> getGenomes() {
        return genomes;
    }

	public String getNewickString() {
		return newickString;
	}

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
