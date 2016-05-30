package parser;

import controller.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jeffrey on 24-4-2016.
 */
public class Parser {

	private PrintWriter nodes;
	private PrintWriter edges;
    private PrintWriter phylo;
    private int parentID = 0;

	/**
	 * Constructor to create an Parser.
     * @param destPath the destination path
     * @param currentPath the path of the files
     * @param phyloTree path of the phylogenetic tree file
	 */
    @SuppressWarnings("checkstyle:methodlength")
	public Parser(String destPath, String currentPath, String phyloTree) {
        boolean problem = new File(destPath).mkdir();
        File nodeFile = new File(destPath + "/nodes.csv");
		File edgeFile = new File(destPath + "/edges.csv");
        File phyloFile = new File(destPath + "/phylo.csv");
		// creates the files
		try {
			problem = nodeFile.createNewFile();
			problem = edgeFile.createNewFile();
            problem = phyloFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			edges = new PrintWriter(destPath + "/edges.csv", "UTF-8");
            edges.println("start,end");
            nodes = new PrintWriter(destPath + "/nodes.csv", "UTF-8");
            nodes.println("id,sequence,genomes,refGenome,refCoor");
            phylo = new PrintWriter(destPath + "/phylo.csv", "UTF-8");
            phylo.println("parent,child,dist,pc");
		} catch (Exception e) {
			e.printStackTrace();
		}

        if (problem) {
            System.out.println("Something went wrong creating /temp");
        }

		parseToCSV(currentPath);

        getPhyloFile(phyloTree);
		edges.close();
		nodes.close();
        phylo.close();
    }
	
	/**
	 * Reads the file as a graph in to an Controller.
	 * @param file The file that is read.
	 * @return The graph in the file.
	 */
	 @SuppressWarnings("checkstyle:methodlength")
	 public static GenomeGraph parse(String file) {
		BufferedReader reader;
		String line;
		GenomeGraph result = new GenomeGraph();
		try {
			InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
			reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			reader.readLine();
			reader.readLine();
			line = reader.readLine();
			while (line != null) {
				String[] splittedLine = line.split("\t");
				String temp = splittedLine[0];
				if (temp.equals("S")) {
					Strand strand = createNode(splittedLine);
					result.addStrand(strand);
				} else if (temp.equals("L")) {
					StrandEdge edge = createEdge(splittedLine);
					result.getStrandNodes().get(edge.getStart()).addEdge(edge);
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Creates an StrandEdge from input data.
	 * @param splittedLine A line that contains an edge read from the file.
	 * @return An StrandEdge.
	 */
	private static StrandEdge createEdge(String[] splittedLine) {
		int startId = Integer.parseInt(splittedLine[1]);
		int endId = Integer.parseInt(splittedLine[3]);
		return new StrandEdge(startId, endId);
	}

	/**
	 * Creates a Strand from the input data.
	 * @param splittedLine A line that contains a node read from the file.
	 * @return A Strand.
	 */
	private static Strand createNode(String[] splittedLine) {
		int nodeId = Integer.parseInt(splittedLine[1]);
		String sequence = splittedLine[2];
		splittedLine[4] = splittedLine[4].substring(6, splittedLine[4].length());
		String[] genomes = splittedLine[4].split(";");
		String referenceGenome = splittedLine[5].substring(6, splittedLine[5].length());
		String ref = splittedLine[8].substring(8, splittedLine[8].length());
		int referenceCoordinate = Integer.parseInt(ref);

		return new Strand(nodeId, sequence, genomes, referenceGenome, referenceCoordinate);
	}

    /**
     * Reads the file as a graph in to an Controller.
     * @param file The file that is read.
     * @return The graph in the file.
     */
    private void parseToCSV(String file) {
        BufferedReader reader;
        String line;
        try {
            InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            reader.readLine();
            reader.readLine();
            line = reader.readLine();
            while (line != null) {
                String[] splittedLine = line.split("\t");
                String temp = splittedLine[0];
                if (temp.equals("S")) {
                    writeNode(splittedLine);
                } else if (temp.equals("L")) {
                    writeEdge(splittedLine);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes an edge to the CSV-file.
     * @param splittedLine the line from the GFA-file
     */
	private void writeEdge(String[] splittedLine) {
		edges.println(splittedLine[1] + "," + splittedLine[3]);
	}

    /**
     * Writes a node to the CSV-file.
     * @param splittedLine the line from the GFA-file
     */
	private void writeNode(String[] splittedLine) {
        String id = splittedLine[1];
        String sequence = splittedLine[2];
        String genomes = splittedLine[4].substring(6, splittedLine[4].length());
        String referenceGenome = splittedLine[5].substring(6, splittedLine[5].length());
        String refCoor = splittedLine[8].substring(8, splittedLine[8].length());

        nodes.println(String.format("%s,%s,%s,%s,%s", id, sequence, genomes,
                referenceGenome, refCoor));
	}

    /**
     * Get all the genomes that are in the file.
     * @param file The file.
     * @return The genomes.
     */
    public static ArrayList<String> getPresentGenomes(String file) {
        BufferedReader reader;
        String line = null;
        try {
            InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            reader.readLine();
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (line != null) {
            String[] splitted = line.split("\t");
            String[] genomes = splitted[1].split(";");

            genomes = Arrays.copyOfRange(genomes, 1, genomes.length);
            return new ArrayList<String>(Arrays.asList(genomes));
        }
        return null;
    }

    /**
     * Get the content from the phylogenetic tree.
     * @param file the path of the file
     */
    public void getPhyloFile(String file) {
        BufferedReader reader;
        String line = "";
        try {
            InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
            reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert line != null;

        parsePhyloTree(line.substring(0, line.length() - 1) + ":0", 0);
    }

    /**
     * Recursively parses the inputted Newick tree (assumed binary).
     * @param tree the path of the file
     * @param parent the id of the parent for recursion (root is 0)
     */
    private void parsePhyloTree(String tree, int parent) {
        int parCount = 0;
        boolean isParent = false;

        for (int i = 0; i < tree.length(); i++) {
            if (tree.charAt(i) == '(') {
                parCount++;
            } else if (tree.charAt(i) == ')') {
                parCount--;
            } else if (tree.charAt(i) == ',' && parCount == 1) {
                parentID++; //global id counter
                int temp = parentID; //keep current value so it doesn't change in recursive calls.
                String[] splitted = tree.split(":");
                phylo.println(parent + "," + temp + "," + splitted[splitted.length - 1]
                        + ",parent");
                parsePhyloTree(tree.substring(1, i), temp);
                parsePhyloTree(tree.substring(i + 1, tree.lastIndexOf(')')), temp);
                isParent = true;
            }
        }

        if (!isParent) {
            phylo.println(parent + "," + tree.split(":")[0] + "," + tree.split(":")[1] + ",child");
        }
    }
}
