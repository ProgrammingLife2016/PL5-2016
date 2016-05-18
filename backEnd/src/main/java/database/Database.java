package database;

import controller.Controller;
import genome.Genome;
import genome.Strand;
import genome.StrandEdge;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import parser.Parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author user.
 *     This class creates and uses the database.
 */
public class Database {

    //private Connection connection;
    private String path;
    //private Controller controller;
    private GraphDatabaseService graphDb;

    /**
     * Create a Database with its connection.
     * @param databasePath Path of the databasefile.
     * @param dataPath Input for the database.
     * @param phyloPath Where the phylogenetic tree file is stored.
     * @param useExistingDB if false, a new database will be created from the data.
     */
    public Database(String databasePath, String dataPath, String phyloPath, boolean useExistingDB) {
        path = databasePath.toLowerCase();

        //Parse the data to csv-files
        new Parser("temp", dataPath);

        createDatabaseConnection(useExistingDB);

        //Populate the database with constraints, indexes and actual data
        if (!useExistingDB) {
            createIndexes();
            createConstraints();
            insertNodes("temp/nodes.csv");
            insertEdges("temp/edges.csv");
        }
    }

    /**
     * Create a controller class from the database.
     * @return the controller to be used.
     */
    public Controller createController() {
        Controller c = new Controller("data/TB10.gfa", "data/340tree.rooted.TKK.nwk");
        for (Strand s : returnNodes("SELECT s RETURN s")) {
            c.addStrand(s);
        }
        for (StrandEdge se : returnEdges("MATCH (a)-[b:GENOME]->(c) RETURN b")) {
            c.addEdge(se);
        }
        return c;
    }

    /**
     * Set up the database connection.
     * If there isn't a database yet create it.
     */
    private void createDatabaseConnection(boolean useExistingDB) {
        if (!useExistingDB) {
            deleteDirectory(new File(path));
        }

        System.out.println("setting up connection");
        GraphDatabaseBuilder dbBuilder = new GraphDatabaseFactory().
                newEmbeddedDatabaseBuilder(new File(path));
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

            for (File f : files) {
                if (f.isDirectory()) {
                    //call recursively if it's a directory
                    deleteDirectory(f);
                }
                else {
                    //delete file otherwise
                    boolean deleted = f.delete();
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
     * @param file the filePath of the csv
     */
    private void insertNodes(String file) {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + new File(file).toURI()
                    + "\' AS csvLine\nCREATE (p:Strand { id: toInt(csvLine.id), "
                    + "sequence: csvLine.sequence, genomes: split(csvLine.genomes,\";\"), "
                    + "refGenome: csvLine.refGenome, refCoor: TOINT(csvLine.refCoor) })");
            tx.success();
        }
    }

    /**
     * Inserts the edges into the database.
     * @param file the filePath of the csv
     */
    private void insertEdges(String file) {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + new File(file).toURI()
                    + "\' AS csvLine\nMATCH (start:Strand { id: toInt(csvLine.start)}),"
                    + "(end:Strand { id: toInt(csvLine.end)})\n"
                    + "CREATE (start)-[:GENOME]->(end)");
            tx.success();
        }
    }

    /**
     * Returns the results from the query as nodes.
     * @param query the Cypher-query to be done on the database
     * @return the list of Strands returned by the query
     */
    public List<Strand> returnNodes(String query) {
        List<Strand> result = new ArrayList<>();

        try (Transaction ignored = graphDb.beginTx();
              Result r = graphDb.execute(query)) {
            while (r.hasNext()) {
                Map<String, Object> row = r.next();
                for (Object n : row.values()) {
                    result.add(new Strand(n));
                }
            }
        }
        return result;
    }

    /**
     * Returns the results from the query as edges.
     * @param query the Cypher-query to be done on the database
     * @return the list of StrandEdges returned by the query
     */
    public List<StrandEdge> returnEdges(String query) {
        List<StrandEdge> result = new ArrayList<>();

        try (Transaction ignored = graphDb.beginTx();
             Result r = graphDb.execute(query)) {
            while (r.hasNext()) {
                Map<String, Object> row = r.next();
                for (Object n : row.values()) {
                    result.add(new StrandEdge(n));
                }
            }
        }
        return result;
    }

    /**
     * Returns the results from the query as genomes.
     * @param genomeName the id/name of the genome to be retrieved
     * @return the requested genome
     */
    public Genome returnGenome(String genomeName) {
        Genome genome = new Genome(genomeName);

        try (Transaction ignored = graphDb.beginTx();
             Result r = graphDb.execute("MATCH (n:Strand) WHERE \"" + genomeName
                     + "\" IN n.genomes RETURN n")) {
            while (r.hasNext()) {
                Map<String, Object> row = r.next();
                for (Object n : row.values()) {
                    genome.addStrand(new Strand(n));
                }
            }
        }
        return genome;
    }

    /**
     * GraphDatabaseService getter.
     * @return GraphDatabaseService.
     */
    public GraphDatabaseService getGraphService() {
        return graphDb;
    }
}