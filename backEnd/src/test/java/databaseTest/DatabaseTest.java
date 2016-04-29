package databaseTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import database.Database;
import genome.Edge;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 *
 */
public class DatabaseTest {

	private static Database database;
	
	/**
	 * Setting up the Database.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		database = new Database("testing");
	}

//	/**
//	 * Closing the database.
//	 */
//	@AfterClass
//	public static void tearDownClass() {
//		database.closeConnection();
//	}
	
	/**
	 * Tests adding and getting edges from the database.
	 */
	@Test
	public void testEdges() {
		Edge edge1 = new Edge(1, 2);
		Edge edge2 = new Edge(1, 3);
		ArrayList<Edge> edges = new ArrayList<Edge>();
		edges.add(edge1);
		edges.add(edge2);
		database.insertEdge(edges);
		String sql = "SELECT startid, endid FROM edge";
		ArrayList<Edge> retrievedEdges = database.getEdges(sql);
		assertEquals(retrievedEdges.get(0).getStart(), edge1.getStart());
		assertEquals(retrievedEdges.get(0).getEnd(), edge1.getEnd());
		assertEquals(retrievedEdges.get(1).getStart(), edge2.getStart());
		assertEquals(retrievedEdges.get(1).getEnd(), edge2.getEnd());
	}

}
