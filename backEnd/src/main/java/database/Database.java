package database;

import controller.Controller;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.sql.Connection;
import java.util.Objects;

/**
 * @author user.
 *     This class creates and uses the database.
 */
public class Database {

    //private Connection connection;
    private String path;
    //private Controller controller;
    private GraphDatabaseService graphDb;

    private static String username = "postgres";
    private static String password = "TagC";

    /**
     * Create a Database with its connection.
     * @param databasePath Path of the databasefile.
     * @param data Input for the database.
     */
    public Database(String databasePath, Controller data) {
        path = databasePath.toLowerCase();

        //controller = data;
        createDatabaseConnection(databasePath);

        //Populate the database with constraints, indexes and actual data
        createIndexes();
        createConstraints();
        insertNodes();
        insertEdges();
    }

    /**
     * Set up the database connection.
     * If there isn't a database yet create it.
     */
    private void createDatabaseConnection(String databasePath) {
        System.out.println("setting up connection");
        GraphDatabaseBuilder dbBuilder = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(new File(databasePath));
        graphDb = dbBuilder.newGraphDatabase();
        System.out.println("Opened database successfully");
    }

    /**
     * Delete the database.
     */
    public void deleteDatabase() {
        //shut down the embedded database server
        graphDb.shutdown();
        deleteDirectory(new File(path));
    }

    /**
     * Delete a directory.
     *
     * @param   dir  the filepath to delete
     */
    private void deleteDirectory(File dir) {
        assert dir != null;

        if (dir.exists()) {
            File[] files = dir.listFiles();
            assert files != null;

            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    //call recursively if it's a directory
                    deleteDirectory(files[i]);
                }
                else {
                    //delete file otherwise
                    boolean deleted = files[i].delete();
                }
            }
        }

        //delete the current directory
        if (!dir.delete()) {
            System.out.println("Something went wrong deleting the directory " + dir);
        }
    }

    /**
     * Creates the constraints for the database we're using.
     */
    private void createConstraints() {
        try (Transaction tx = graphDb.beginTx()) {

            tx.success();
        }
    }

    /**
     * Creates the indexes for the database we're using.
     */
    private void createIndexes() {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("CREATE INDEX ON :Strand(id)");
            tx.success();
        }
    }

    /**
     * Inserts the nodes into the database.
     */
    private void insertNodes() {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + new File("data/nodes.csv").toURI()
                    + "\' AS csvLine\nCREATE (p:Strand { id: toInt(csvLine.id), sequence: "
                    + "csvLine.sequence })");
            tx.success();
        }
    }

    /**
     * Inserts the edges into the database.
     */
    private void insertEdges() {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + new File("data/edges.csv").toURI()
                    + "\' AS csvLine\nMATCH (start:Strand { id: toInt(csvLine.start)}),"
                    + "(end:Strand { id: toInt(csvLine.end)})\n"
                    + "CREATE (start)-[:GENOME { genome: csvLine.genome }]->(end)");
            tx.success();
        }
    }

    /**
     * GraphDatabaseService getter.
     * @return GraphDatabaseService.
     */
    public GraphDatabaseService getGraphService() {
        return graphDb;
    }
}