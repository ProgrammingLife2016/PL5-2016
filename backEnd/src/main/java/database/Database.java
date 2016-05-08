package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import genome.DataContainer;
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
  private DataContainer dataContainer;

  private static String username = "postgres";
  private static String password = "TagC";

	/**
	 * Create a Database with its connection.
	 * @param databaseName Name of the database.
	 * @param data Input for the database.
	 */
	public Database(String databaseName, DataContainer data) {
		name = databaseName.toLowerCase();
		dataContainer = data;
		createDatabaseConnection(databaseName);
	}
	
	/**
	 * Set up the database connection.
	 * If there isn't a database yet create it.
	 */
	private void createDatabaseConnection(String databaseName) {
		System.out.println("setting up connection");
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"
					+ databaseName, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				System.out.println("new connection");
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/",
						username, password);
				statement = connection.createStatement();
				String sql = "CREATE DATABASE " + databaseName;
				statement.execute(sql);
				statement.close();
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"
						+ databaseName, username, password);
				createTables();
				insertNodes(dataContainer.getNodes().values());
				insertEdges(dataContainer.getEdges().values());
			} catch (SQLException e1) {
				e1.printStackTrace();
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
			String edge = 	"CREATE TABLE edge "
							+ "(startid INTEGER not NULL, "
							+ "endid INTEGER not NULL)";
			statement.executeUpdate(edge);
			
			String node = 	"CREATE TABLE node "
							+ "(id INTEGER not NULL, "
							+ "sequence TEXT not NULL, "
							+ "weight INTEGER not NULL, "
							+ "referenceGenome TEXT not NULL, "
							+ "referenceCoordinate INTEGER not NULL, "
							+ "PRIMARY KEY ( id ))";
			statement.executeUpdate(node);
							
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}	
	
	/**
	 * Inserts the nodes into the database.
	 * @param nodes The nodes to be inserted.
	 */
	private void insertNodes(Collection<Node> nodes) {
		System.out.println("inserting nodes");
		String sql = "INSERT INTO node VALUES(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			for (Node node : nodes) {
				ps.setInt(1, node.getId());
				ps.setString(2, node.getSequence());
				ps.setInt(3, node.getWeight());
				ps.setString(4, node.getReferenceGenome());
				ps.setInt(5, node.getReferenceCoordinate());
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	
	/**
	 * Inserts the edges into the database.
	 * @param edges The edges to be inserted.
	 */
	private void insertEdges(Collection<Edge> edges) {
		System.out.println("inserting edges");
		for (Edge edge : edges) {
			String sql = "INSERT INTO edge values(" + edge.getStart() + ", " + edge.getEnd() + ")";
			try {
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
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
			
			while (rs.next()) {
				int start = rs.getInt("startid");
				int end = rs.getInt("endid");
				
				result.add(new Edge(start, end));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				statement.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
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
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String sequence = rs.getString("sequence");
				int weight = rs.getInt("weight");
				String referenceGenome = rs.getString("referenceGenome");
				int referenceCoordinate = rs.getInt("referenceCoordinate");
	
				result.add(new Node(id, sequence, new String[weight], 
						referenceGenome, referenceCoordinate));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				statement.close();
			} catch (SQLException e2) {
				e2.printStackTrace();				
			}
		}
		return result;
	}
	
	/**
	 * Ends the connection with the database.
	 */
	public void closeConnection() {
		try {
			statement = connection.createStatement();
			String sql = "DROP DATABASE " + name;
			statement.executeUpdate(sql);
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		
	}	
}
