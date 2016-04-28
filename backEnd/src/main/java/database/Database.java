package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	
	private Connection connection;
	private Statement statement;

	public Database() {
		CreateDatabaseConnection();
	}
	
	public void CreateDatabaseConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tagc", "postgres", "TagC");
			System.out.println("hier");
			statement = connection.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", "postgres", "TagC");
				System.out.println("ja");
				statement = connection.createStatement();
				String sql = "CREATE DATABASE tagc";
				statement.execute(sql);
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tagc", "postgres", "TagC");
				statement = connection.createStatement();
				CreateTables();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	public void CreateTables() {
		try {
			String edge = 	"CREATE TABLE edge " +
							"(startid INTEGER not NULL, " +
							"endid INTEGER not NULL)";
			statement.executeUpdate(edge);
			System.out.println("table created");
			String sql = "INSERT INTO edge values(1, 2)";
			statement.execute(sql);
			
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
	
}
