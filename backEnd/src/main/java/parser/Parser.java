package parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;


import genome.DataContainer;
import genome.Edge;
import genome.Node;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Jeffrey on 24-4-2016.
 */
public class Parser {

	/**
	 * Constructor to create an Parser.
	 * This throws an exception because it should not be possible.
	 */
	protected Parser() {
        throw new UnsupportedOperationException();
    }
	
	/**
	 * Reads the file as a graph in to an DataContainer.
	 * @param file The file that is read.
	 * @return The graph in the file.
	 */
	public static DataContainer parse(String file) {
		BufferedReader reader;
		String line;
		DataContainer result = new DataContainer();
		try {

			InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
			Reader r = new InputStreamReader(in, StandardCharsets.UTF_8);
			reader = new BufferedReader(r);

			reader.readLine();
			reader.readLine();
			line = reader.readLine();
			while (line != null) {
				String[] splittedLine = line.split("\t");
				String temp = splittedLine[0];
				if (temp.equals("S")) {
					Node node = createNode(splittedLine);
					result.addNode(node);
				} else if (temp.equals("L")) {
					result.addEdge(createEdge(splittedLine));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("No datafile has been found!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		result.calculateCoordinates();
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
