package main;

import genome.DataContainer;
import genome.Edge;
import genome.Node;

import java.util.ArrayList;

import database.Database;
import parser.Parser;

/**
 * 
 * @author Jeffrey Helgers.
 *
 */
public class Main {

	/**
	 * The main reads the data from the file and stores this in the database.
	 * Followed by a sql example.
	 * @param args Main.
	 */
	public static void main(String[] args) {
		DataContainer dataContainer = Parser.parse("../data/TB10.gfa");
		Database database = new Database("tagc", dataContainer);
		
		// Example.
		ArrayList<Edge> edges = database.getEdges("SELECT * FROM edge");
		ArrayList<Node> nodes = database.getNodes("SELECT * FROM node");
		System.out.println(edges.size());
		System.out.println(nodes.size());
	}

}
