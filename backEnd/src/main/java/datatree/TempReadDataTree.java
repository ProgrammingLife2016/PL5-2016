package datatree;

import genome.Strand;
import parser.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Jeffrey Helgers.
 * Temporary class that reads and writes the datatree.
 */
public final class TempReadDataTree {

	/**
	 * Constructor to create the tree.
	 */
	private TempReadDataTree() {
		
	}
	
	/**
	 * Read the data tree.
	 * @param tree The data tree where the strands are added.
	 * @param strands All the strands.
	 * @param file The file.
	 */
	public static void readFile(DataTree tree, HashMap<Integer, Strand> strands, String file) {
		BufferedReader reader;
		try {
			InputStream in = Parser.class.getClassLoader().getResourceAsStream(file);
			reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
			String line = reader.readLine();
			while (line != null) {
				String[] splitted = line.split(" ");
				int nodeId = Integer.parseInt(splitted[0]);
				DataNode current = tree.getRoot().getNode(nodeId);
				String[] strandIDs = splitted[1].split(";");
				ArrayList<Strand> strandsInNode = new ArrayList<Strand>();
				for (String strandID : strandIDs) {
					strandsInNode.add(strands.get(Integer.parseInt(strandID)));
				}
				current.setStrands(strandsInNode);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
