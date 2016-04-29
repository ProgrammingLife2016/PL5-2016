package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import genome.Edge;
import genome.Node;

/**
 * @author Jeffrey Helgers.
 *     This class creates and uses the database.
 */
public class Database {

  private Connection connection;
  private Statement statement;

  private static final String username = "postgres";
  private static final String password = "TagC";

	/**
	 * Create a Database with its connection.
	 */
	public Database() {
		createDatabaseConnection();
	}
	
	/**
	 * Set up the database connection.
	 * If there isn't a database yet create it.
	 */
	public void createDatabaseConnection() {
		System.out.println("setting up connection");
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tagc", username, password);
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", username, password);
				statement = connection.createStatement();
				String sql = "CREATE DATABASE tagc";
				statement.execute(sql);
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tagc", username, password);
				statement = connection.createStatement();
				createTables();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Create the tables for the database.
	 */
	public void createTables() {
		System.out.println("creating tables");
		try {
			String edge = 	"CREATE TABLE edge " +
							"(startid INTEGER not NULL, " +
							"endid INTEGER not NULL)";
			statement.executeUpdate(edge);
			
			String node = 	"CREATE TABLE node " +
							"(id INTEGER not NULL, " +
							"sequence TEXT not NULL, " +
							"weight INTEGER not NULL, " +
							"referenceGenome TEXT not NULL, " +
							"referenceCoordinate INTEGER not NULL)";
			statement.executeUpdate(node);
							
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	
	
	/**
	 * Inserts the nodes into the database.
	 * @param nodes The nodes to be inserted.
	 */
	public void insertNodes(ArrayList<Node> nodes) {
		System.out.println("inserting nodes");
		for (Node node : nodes) {
			String sql = "INSERT INTO node(id, sequence, weight, referenceGenome, referenceCoordinate) VALUES(" + node.getId() + ", '" 
													+ node.getSequence() + "', "
													+ node.getWeight() + ", '"
													+ node.getReferenceGenome() + "', "
													+ node.getReferenceCoordinate() + ")";
			try {
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Inserts the edges into the database.
	 * @param edges The edges to be inserted.
	 */
	public void insertEdge(ArrayList<Edge> edges) {
		System.out.println("inserting edges");
		for (Edge edge : edges) {
			String sql = "INSERT INTO edge values(" + edge.getStart() + ", " + edge.getEnd() + ")";
			try {
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
