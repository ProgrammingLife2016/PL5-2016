package parser;
import java.io.FileReader;
import java.io.IOException;

import genome.Edge;
import genome.Genome;
import genome.Node;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * Created by Jeffrey on 24-4-2016.
 */

public class Parser {

	/**
	 * Reads the file as a graph in to an Genome.
	 * @param file The file that is read.
	 * @return The graph in the file.
	 */
	public static Genome parse(String file) {
		BufferedReader reader;
		String line;
		Genome result = new Genome();
		
		try {
			reader = new BufferedReader(new FileReader(file));
			reader.readLine();
			reader.readLine();
			line = reader.readLine();
			
			while (line != null) {
				String[] splittedLine = line.split("\t");
				String temp = splittedLine[0];
				if (temp.equals("S")) {
					result.addNode(createNode(splittedLine));
				} else if (temp.equals("L")) {
					result.addEdge(createEdge(splittedLine));
				}
				line = reader.readLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/**
	 * Creates an Edge from input data.
	 * @param splittedLine A line that contains an edge read from the file.
	 * @return An Edge.
	 */
	private static Edge createEdge(String[] splittedLine) {
		int startId = Integer.parseInt(splittedLine[1]);
		int endId = Integer.parseInt(splittedLine[3]);
		return new Edge(startId, endId);
	}

	/**
	 * Creates a Node from the input data.
	 * @param splittedLine A line that contains a node read from the file.
	 * @return A Node.
	 */
	private static Node createNode(String[] splittedLine) {
		int nodeId = Integer.parseInt(splittedLine[1]);
		String sequence = splittedLine[2];
		splittedLine[4] = splittedLine[4].substring(6, splittedLine[4].length());
		String[] genomes = splittedLine[4].split(";");
		String referenceGenome = splittedLine[5].substring(6, splittedLine[5].length());
		String ref = splittedLine[8].substring(8, splittedLine[8].length());
		int referenceCoordinate = Integer.parseInt(ref);
		return new Node(nodeId, sequence, genomes, referenceGenome, referenceCoordinate);
	}
}
