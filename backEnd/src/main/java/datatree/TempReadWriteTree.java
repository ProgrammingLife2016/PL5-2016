package datatree;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import genome.Strand;
import parser.Parser;

/**
 * 
 * @author Jeffrey Helgers.
 * Temporary class that reads and writes the datatree.
 */
public final class TempReadWriteTree {

	/**
	 * Constructor to create the tree.
	 */
	private TempReadWriteTree() {
		
	}
	/**
	 * Write the whole tree.
	 * @param root Root node.
	 */
	public static void writeTree(DataNode root) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				    new FileOutputStream("outfilename"), StandardCharsets.UTF_8));
			writeNode(root, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write a node.
	 * @param node Written node.
	 * @param writer The writer.
	 */
	private static void writeNode(DataNode node, BufferedWriter writer) {
		StringBuilder line = new StringBuilder();
		line.append(node.getId());
		line.append(" ");
		for (Strand strand : node.getStrands()) {
			line.append(strand.getId());
			line.append(";");
		}
		line.append("\n");
		try {
			writer.write(line.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (DataNode child : node.getChildren()) {
			writeNode(child, writer);
		}
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
				ArrayList<Strand> strandsInNode = new ArrayList<>();
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
