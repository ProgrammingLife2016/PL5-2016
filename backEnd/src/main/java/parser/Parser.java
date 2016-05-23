package parser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import genome.StrandEdge;

import genome.Strand;

import java.util.ArrayList;
import java.util.Arrays;

import controller.Controller;

/**
 * Created by Jeffrey on 24-4-2016.
 */
public class Parser {

	private PrintWriter nodes;
	private PrintWriter edges;
    private PrintWriter phylo;

	/**
	 * Constructor to create an Parser.
     * @param destPath the destination path
     * @param currentPath the path of the files
	 */
	public Parser(String destPath, String currentPath) {
        new File(destPath).mkdir();
        File nodeFile = new File(destPath + "/nodes.csv");
		File edgeFile = new File(destPath + "/edges.csv");
        File phyloFile = new File(destPath + "/phylo.csv");
		// creates the files
		try {
            //dir.createNewFile();
			nodeFile.createNewFile();
			edgeFile.createNewFile();
            phyloFile.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			edges = new PrintWriter(destPath + "/edges.csv", "UTF-8");
            edges.println("start,end");
            nodes = new PrintWriter(destPath + "/nodes.csv", "UTF-8");
            nodes.println("id,sequence,genomes,refGenome,refCoor");
            phylo = new PrintWriter(destPath + "/phylo.csv", "UTF-8");
            phylo.println("parent,child,dist");
		} catch (Exception e) {
			e.printStackTrace();
		}

		parseToCSV(currentPath);
		edges.close();
		nodes.close();
        phylo.close();
    }
	
	/**
	 * Reads the file as a graph in to an Controller.
	 * @param file The file that is read.
	 * @return The graph in the file.
	 */
	public static Controller parse(String file) {

		BufferedReader reader;
		String line;
		controller.Controller result = new controller.Controller("data/TB10.gfa",
                "data/340tree.rooted.TKK.nwk");
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
		result.getDataTree().addStrands(new ArrayList<>(result.getGenomes().values()));
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
}
