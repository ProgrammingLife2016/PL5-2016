package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import controller.Controller;
import genome.StrandEdge;
import genome.Strand;

/**
 * @author Jeffrey Helgers.
 *     This class creates and uses the database.
 */
public class Database {

  private Connection connection;
  private Statement statement;
  private String name;
  private Controller controller;

  private static String username = "postgres";
  private static String password = "TagC";

	/**
	 * Create a Database with its connection.
	 * @param databaseName Name of the database.
	 * @param data Input for the database.
	 */
	public Database(String databaseName, controller.Controller data) {
		name = databaseName.toLowerCase();
		controller = data;
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
				insertNodes(controller.getstrandNodes().values());
				insertEdges(controller.getEdges().values());
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
	 * Inserts the strands into the database.
	 * @param strands The strands to be inserted.
	 */
	private void insertNodes(Collection<Strand> strands) {
		System.out.println("inserting strains");
		String sql = "INSERT INTO node VALUES(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
			for (Strand strand : strands) {
				ps.setInt(1, strand.getId());
				ps.setString(2, strand.getSequence());
				ps.setInt(3, strand.getWeight());
				ps.setString(4, strand.getReferenceGenome());
				ps.setInt(5, strand.getReferenceCoordinate());
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
	 * Inserts the StrandEdges into the database.
	 * @param StrandEdges The StrandEdges to be inserted.
	 */
	private void insertEdges(Collection<StrandEdge> StrandEdges) {
		System.out.println("inserting StrandEdges");
		for (StrandEdge StrandEdge : StrandEdges) {
			String sql = "INSERT INTO edge values(" + StrandEdge.getStart() + ", " + StrandEdge.getEnd() + ")";
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
	public ArrayList<StrandEdge> getEdges(String sql) {
		ArrayList<StrandEdge> result = new ArrayList<StrandEdge>();
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				int start = rs.getInt("startid");
				int end = rs.getInt("endid");
				
				result.add(new StrandEdge(start, end));
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
	public ArrayList<Strand> getNodes(String sql) {
		ArrayList<Strand> result = new ArrayList<Strand>();
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
	
				result.add(new Strand(id, sequence, new String[weight],
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
