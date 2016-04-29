package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
  private String name;

  private static final String username = "postgres";
  private static final String password = "TagC";

	/**
	 * Create a Database with its connection.
	 */
	public Database(String databaseName) {
		createDatabaseConnection(databaseName);
		name = databaseName;
	}
	
	/**
	 * Set up the database connection.
	 * If there isn't a database yet create it.
	 */
	private void createDatabaseConnection(String databaseName) {
		System.out.println("setting up connection");
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", username, password);
				statement = connection.createStatement();
				String sql = "CREATE DATABASE " + databaseName;
				statement.execute(sql);
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + databaseName, username, password);
				createTables();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try { statement.close(); } catch (SQLException e2) { } ;
			}
		}
		
	}
	
	/**
	 * Create the tables for the database.
	 */
	private void createTables() {
		System.out.println("creating tables");
		
		try {
			statement = connection.createStatement();
			String edge = 	"CREATE TABLE edge " +
							"(startid INTEGER not NULL, " +
							"endid INTEGER not NULL)";
			statement.executeUpdate(edge);
			
			String node = 	"CREATE TABLE node " +
							"(id INTEGER not NULL, " +
							"sequence TEXT not NULL, " +
							"weight INTEGER not NULL, " +
							"referenceGenome TEXT not NULL, " +
							"referenceCoordinate INTEGER not NULL, " +
							"PRIMARY KEY ( id ))";
			statement.executeUpdate(node);
							
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e2) { } ;
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
													+ node.getRefrenceGenome() + "', "
													+ node.getRefrenceCoordinate() + ")";
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try { statement.close(); } catch (SQLException e2) { } ;
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
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try { statement.close(); } catch (SQLException e2) { } ;
			}
		}
	}
	
	/**
	 * Retrieving data from the edge table.
	 * @param sql The executed query.
	 * @return The edges that satisfy the query.
	 */
	public ArrayList<Edge> getEdges(String sql) {
		ArrayList<Edge> result = new ArrayList<Edge>();
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				int start = rs.getInt("startid");
				int end = rs.getInt("endid");
				
				result.add(new Edge(start, end));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e2) { } ;
			try { rs.close(); } catch (SQLException e3) { } ;
		}
		
		return result;
	}
	
	/**
	 * Retrieving data from the node table.
	 * @param sql The executed query.
	 * @return The nodes that satisfy the query.
	 */
	public ArrayList<Node> getNodes(String sql) {
		ArrayList<Node> result = new ArrayList<Node>();
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String sequence = rs.getString("sequence");
				int weight = rs.getInt("weight");
				String referenceGenome = rs.getString("referenceGenome");
				int referenceCoordinate = rs.getInt("referenceCoordinat");
	
				result.add(new Node(id, sequence, new String[weight], referenceGenome, referenceCoordinate));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e2) { } ;
			try { rs.close(); } catch (SQLException e3) { } ;
		}
		
		return result;
	}
	
	/**
	 * Ends the connection with the database.
	 */
	public void closeConnection() {
		try {
			connection.close();
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + name, username, password);
			statement = connection.createStatement();
			String sql = "DROP DATABASE " + name;
			statement.executeUpdate(sql);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { statement.close(); } catch (SQLException e2) { } ;
			try { connection.close(); } catch (SQLException e3) { } ;
		}
		
	}	
}
