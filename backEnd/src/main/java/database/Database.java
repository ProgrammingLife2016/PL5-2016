package database;

import controller.Controller;
import genome.Genome;
import genome.Strand;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;
import java.sql.Connection;

/**
 * @author Jeffrey Helgers.
 *     This class creates and uses the database.
 */
public class Database {

    private Connection connection;
    private String name;
    private String path;
    private Controller controller;
    private GraphDatabaseService graphDb;

    private static String username = "postgres";
    private static String password = "TagC";

    /**
     * Create a Database with its connection.
     * @param databasePath Path of the databasefile.
     * @param data Input for the database.
     */
    public Database(String databasePath, Controller data) {
        path = databasePath;
        deleteDatabase();

        name = databasePath.toLowerCase();
        controller = data;
        createDatabaseConnection(databasePath);

        insertPhylogeneticTree();
    }

    /**
     * Set up the database connection.
     * If there isn't a database yet create it.
     */
    private void createDatabaseConnection(String databasePath) {
        System.out.println("setting up connection");
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(databasePath));
        System.out.println("Opened database successfully");
    }

    /**
     * Delete the database.
     */
    public void deleteDatabase() {
        deleteDirectory(new File(path));
    }

    /**
     * Delete a directory.
     *
     * @param   dir  the filepath to delete
     */
    private void deleteDirectory(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
    }

    /**
     * Inserts the phylogenetic tree into the database.
     */
    public void insertPhylogeneticTree() {
        Node n;

        try (Transaction tx = graphDb.beginTx()) {
            for (Genome genome : controller.getGenomes().values()) {
                for (Strand strand : genome.getStrands()) {
                    n = graphDb.createNode();
                    n.setProperty("sequence", strand.getSequence());
                    n.setProperty("name", strand.getId());
                }
            }
            tx.success();
        }
    }
}