package parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import genome.StrandEdge;

import genome.Strand;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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
	 * Reads the file as a graph in to an Controller.
	 * @param file The file that is read.
	 * @return The graph in the file.
	 */
	public static controller.Controller parse(String file) {
		BufferedReader reader;
		String line;
		controller.Controller result = new controller.Controller();
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
}
