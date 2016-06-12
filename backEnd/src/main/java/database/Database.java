package database;

import genome.GenomeMetadata;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * @author user.
 *     This class creates and uses the database.
 */
public class Database {

	/**
	 * Path to the database.
	 */
    private String path;
    
    /**
     * Connection to the database.
     */
    private GraphDatabaseService graphDb;

    /**
     * Instantiates a new database.
     * @param databasePath Path of the database file.
     */
    public Database(String databasePath) {
    	path = databasePath.toLowerCase();
    	createDatabaseConnection(false);
    }
    
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
        new Parser("temp", dataPath, phyloPath);

        createDatabaseConnection(useExistingDB);

        //Populate the database with constraints, indexes and actual data
        if (!useExistingDB) {
            createIndexes();
            createConstraints();
            insertNodes("temp/nodes.csv");
            insertEdges("temp/edges.csv");
            insertPhyloTree("temp/phylo.csv");
        }
    }

    /**
     * Set up the database connection.
     * If there isn't a database yet create it.
     */
    private void createDatabaseConnection(boolean useExistingDB) {
        if (!useExistingDB) {
            deleteDirectory(new File(path));
        }
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

        boolean deleted = false;
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
                    deleted = f.delete();
                }
            }
        }

        //delete the current directory
        if (!dir.delete() || deleted) {
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
     * Inserts the phylogenetic tree into the database.
     * @param file the filePath of the csv
     */
    private void insertPhyloTree(String file) {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("CREATE (n:Phylo {genome: \"0\", pc: \"parent\"})");
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + new File(file).toURI()
                    + "\' AS csvLine\nMERGE (n:Phylo {genome:csvLine.child}) ON "
                    + "CREATE SET n += {genome: csvLine.child, pc: csvLine.pc}");
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + new File(file).toURI()
                    + "\' AS csvLine\nMATCH (parent:Phylo {genome:csvLine.parent})\n"
                    + "MATCH (child:Phylo {genome:csvLine.child})\n"
                    + "CREATE (parent)-[:PHYLOPARENT]->(child)");
            tx.success();
        }
    }

//    /**
//     * Returns the results from the query as nodes.
//     * @param query the Cypher-query to be done on the database
//     * @return the list of Strands returned by the query
//     */
//    public List<Strand> returnNodes(String query) {
//        List<Strand> result = new ArrayList<>();
//
//        try (Transaction ignored = graphDb.beginTx();
//              Result r = graphDb.execute(query)) {
//            while (r.hasNext()) {
//                Map<String, Object> row = r.next();
//               for (Object n : row.values()) {
//                   result.add(new Strand(n));
//                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Returns the results from the query as edges.
//     * @param query the Cypher-query to be done on the database
//     * @return the list of StrandEdges returned by the query
//     */
//    public List<StrandEdge> returnEdges(String query) {
//        List<StrandEdge> result = new ArrayList<>();
//
//        try (Transaction ignored = graphDb.beginTx();
//             Result r = graphDb.execute(query)) {
//            while (r.hasNext()) {
//                Map<String, Object> row = r.next();
////                for (Object n : row.values()) {
////                    //result.add(new StrandEdge(n));
////                }
//            }
//        }
//        return result;
//    }
//
//    /**
//     * Returns the results from the query as genomes.
//     * @param genomeName the id/name of the genome to be retrieved
//     * @return the requested genome
//     */
//    public Genome returnGenome(String genomeName) {
//        Genome genome = new Genome(genomeName);
//
//        try (Transaction ignored = graphDb.beginTx();
//             Result r = graphDb.execute("MATCH (n:Strand) WHERE \"" + genomeName
//                     + "\" IN n.genomes RETURN n")) {
//            while (r.hasNext()) {
//                Map<String, Object> row = r.next();
////                for (Object n : row.values()) {
////                    //genome.addStrand(new Strand(n));
////                }
//            }
//        }
//        return genome;
//    }

    /**
     * Returns all genomes that descent from the selected genome/parent-id.
     * @param id the id/name of the genome/parent who's descendants should be returned
     * @return list of all descending genomes
     */
    public Set<String> returnDescGenome(String id) {
        Set<String> result = new HashSet<>();

        try (Transaction ignored = graphDb.beginTx();
             Result r = graphDb.execute("MATCH (a:Phylo {genome: \"" + id + "\"})-[*]->(n:Phylo) "
                     + "WHERE n.pc = \"child\" RETURN n")) {
            while (r.hasNext()) {
                Map<String, Object> row = r.next();
                for (Object n : row.values()) {
                    result.add(((Node) n).getProperty("genome").toString());
                }
            }
        }
        return result;
    }

    /**
     * GraphDatabaseService getter.
     * @return GraphDatabaseService.
     */
    public GraphDatabaseService getGraphService() {
        return graphDb;
    }

	/**
	 * Gets the all genome metadata.
	 *
	 * @return the all genome metadata
	 */
	public HashMap<String, GenomeMetadata> getAllGenomeMetadata() {
		HashMap<String, GenomeMetadata> hmap = new HashMap<String, GenomeMetadata>(); 
		try (Transaction tx = graphDb.beginTx();
				ResourceIterator<Node> it = graphDb.findNodes(DynamicLabel.label("GenomeMeta"));) {

		while (it.hasNext()) {
			Node n = it.next();
			String genomeId = n.getProperty("id").toString();
			String lineage = n.getProperty("lineage").toString();
			hmap.put(genomeId, new GenomeMetadata(genomeId, lineage));
		}
		
		tx.success();
		return hmap;
		}
	}

	/**
	 * Load genome metadata from resources.
	 *
	 * @param filePath the file path
	 */
	public void loadGenomeMetadataFromResources(String filePath) {
		
		InputStream in = Parser.class.getClassLoader().getResourceAsStream(filePath);
		new File("temp").mkdir();
		File temp = new File("temp/metadata.csv");
		try {
			temp.createNewFile();
			Files.copy(in, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try (Transaction tx = graphDb.beginTx()) {
            graphDb.execute("LOAD CSV WITH HEADERS FROM \'" + temp.toURI()
                    + "\' AS line FIELDTERMINATOR ';' \n CREATE (:GenomeMeta "
                    + " {id: line.`Specimen ID`, lineage: line.Lineage})");
            tx.success();
        }
		
	}
}