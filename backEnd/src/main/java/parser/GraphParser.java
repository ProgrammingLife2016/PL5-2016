package parser;

import com.opencsv.CSVReader;

import genome.LightGraph;
import genome.GenomeMetadata;
import genome.GenomicFeature;
import genome.Strand;
import genome.StrandEdge;
import genome.GenomeGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jeffrey on 24-4-2016.
 */
public class GraphParser {

	/**
	 * Reads the file as a graph in to an Controller.
	 *
	 * @param file
	 *            The file that is read.
	 * @return The graph in the file.
	 */
	@SuppressWarnings("checkstyle:methodlength")
	public static LightGraph parse(String file) {
		LightGraph genomeGraph = new LightGraph();

		InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
		CSVReader reader = new CSVReader(new InputStreamReader(in), '\t');
		String[] nextLine;
		try {
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine[0].equals("S")) {
					genomeGraph.addNode(Integer.parseInt(nextLine[1]));
				} else if (nextLine[0].equals("L")) {
					int start = Integer.parseInt(nextLine[1]);
					int end = Integer.parseInt(nextLine[3]);
					genomeGraph.addEdge(start, end);
				}
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return genomeGraph;
	}
}