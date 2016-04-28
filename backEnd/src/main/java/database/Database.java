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
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
}
