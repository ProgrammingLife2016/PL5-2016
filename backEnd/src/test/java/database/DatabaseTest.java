package database;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import genome.Genome;
import genome.Strand;
import genome.StrandEdge;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 11/05/16.
 */
public class DatabaseTest {

    private static Database db;

    /**
     * Loads the data from the test file.
     * Creates database file
     */
    @BeforeClass
    public static void setup() {
        db = new Database("test.db", "data/test1.gfa", "data/testPhylo.nwk", false);
    }

    /**
     * The actual tests, currently checks if file indeed gets created.
     */
    @Test
    public void testDBCreation() {
        File f = new File("test.db");
        Assert.assertTrue(f.exists());
    }

    /**
     * Test if opening an existing database works.
     */
    @Test @SuppressFBWarnings("ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD")
    public void testDBOpening() {
        ResourceIterator<Node> allNodes;
        db.getGraphService().shutdown();
        db = new Database("test.db", "data/test1.gfa", "data/testPhylo.nwk", true);

        int i = 1;
        try (Transaction tx = db.getGraphService().beginTx()) {
            //Fetch all nodes from the database
            allNodes = db.getGraphService().findNodes(DynamicLabel.label("Strand"));
            tx.success();

            //Check if all nodes are inserted correctly in the database
            Node a;
            while (allNodes.hasNext()) {
                a = allNodes.next();
                Assert.assertEquals(a.getProperty("id"), (long) i);
                i++;
            }
        }
    }

    /**
     * Test if all nodes from the test file get inserted.
     */
    @Test
    public void testNodeInsertion() {
        ResourceIterator<Node> allNodes;
        String[] strands = {"", "AAAAAAAA", "A", "C"};

        int i = 1;
        try (Transaction tx = db.getGraphService().beginTx()) {
            //Fetch all Strand-nodes from the database
            allNodes = db.getGraphService().findNodes(DynamicLabel.label("Strand"));
            tx.success();

            //Check if all nodes are inserted correctly in the database
            Node a;
            while (allNodes.hasNext()) {
                a = allNodes.next();
                Assert.assertEquals(a.getProperty("id"), (long) i);
                Assert.assertEquals(a.getProperty("sequence"), strands[i]);
                Assert.assertEquals(a.getProperty("refGenome"), "AA");
                Assert.assertEquals(a.getProperty("refCoor"), (long) 371);
                i++;
            }
        }

        //Make sure no extra nodes are inserted
        Assert.assertEquals(4, i);
    }

    /**
     * Test if all edges from the test file get inserted.
     */
    @Test
    public void testEdgeInsertion() {
        Iterable<Relationship> allRelas;
        Set<String> strands = new HashSet<>();
        strands.add("1|2");
        strands.add("2|3");

        int i = 1;

        try (Transaction tx = db.getGraphService().beginTx()) {
            //fetch all relationships from the graph
            allRelas = GlobalGraphOperations.at(db.getGraphService()).getAllRelationships();

            //check if all relationships/edges are indeed in the database
            for (Relationship a : allRelas) {
                if (a.isType(DynamicRelationshipType.withName("GENOME"))) {
                    String coordinates = a.getStartNode().getProperty("id") + "|"
                            + a.getEndNode().getProperty("id");
                    Assert.assertTrue(strands.contains(coordinates));
                    i++;
                }
            }
            tx.success();
        }

        //Make sure no extra edges are inserted
        Assert.assertEquals(i, 3);
    }

    /**
     * Test if all phylogenetic nodes from the test file gets inserted.
     */
    @Test
    public void testPhyloNodeInsertion() {
        //Iterable<Node> allNodes;
        ResourceIterator<Node> allNodes;
        String[] genomes = {"0", "1", "AA", "2", "3", "BB", "CC", "DD"};
        String[] pc = {"parent", "parent", "child", "parent", "parent", "child", "child", "child"};

        int i = 0;
        try (Transaction tx = db.getGraphService().beginTx()) {
            //Fetch all nodes from the database
            allNodes = db.getGraphService().findNodes(DynamicLabel.label("Phylo"));
            tx.success();

            //Check if all nodes are inserted correctly in the database
            Node a;
            while (allNodes.hasNext()) {
                a = allNodes.next();
                Assert.assertEquals(genomes[i], a.getProperty("genome"));
                Assert.assertEquals(pc[i], a.getProperty("pc"));
                i++;
            }
        }

        //Make sure no extra nodes are inserted
        Assert.assertEquals(8, i);
    }

    /**
     * Test if the phylogenetic tree gets inserted correctly in the database.
     */
    @Test
    @SuppressWarnings("checkstyle:methodlength")
    public void testPhyloStructure() {
        Iterable<Relationship> allRelas;
        Set<String> strands = new HashSet<>();
        strands.add("0|1");
        strands.add("1|AA");
        strands.add("1|2");
        strands.add("2|3");
        strands.add("3|BB");
        strands.add("3|CC");
        strands.add("2|DD");

        int i = 1;

        try (Transaction tx = db.getGraphService().beginTx()) {
            //fetch all relationships from the graph
            allRelas = GlobalGraphOperations.at(db.getGraphService()).getAllRelationships();

            //check if all relationships/edges are indeed in the database
            for (Relationship a : allRelas) {
                if (a.isType(DynamicRelationshipType.withName("PHYLOPARENT"))) {
                    String coordinates = a.getStartNode().getProperty("genome") + "|"
                            + a.getEndNode().getProperty("genome");
                    Assert.assertTrue(strands.contains(coordinates));
                    i++;
                }
            }
            tx.success();
        }

        //Make sure no extra edges are inserted
        Assert.assertEquals(i, 8);
    }

    /**
     * Test if nodes get retrieved correctly.
     */
    @Test
    public void testNodeRetrieval() {
        List<Strand> strands = db.returnNodes("MATCH (n:Strand) RETURN n");
        Assert.assertEquals(1, strands.get(0).getId());
        Assert.assertEquals(2, strands.get(1).getId());
        Assert.assertEquals(3, strands.get(2).getId());
        Assert.assertEquals(3, strands.size());

        strands = db.returnNodes("MATCH (n {id: 1}) RETURN n");
        Assert.assertEquals(1, strands.get(0).getId());
        Assert.assertEquals(1, strands.size());
    }

    /**
     * Test if all relationships get retrieved correctly.
     */
    @Test
    public void testRelaRetrieval() {
        List<StrandEdge> strandEdges = db.returnEdges("MATCH (a)-[b:GENOME]->(c) RETURN b");
        Assert.assertEquals(1, strandEdges.get(0).getStart());
        Assert.assertEquals(2, strandEdges.get(0).getEnd());
        Assert.assertEquals(2, strandEdges.get(1).getStart());
        Assert.assertEquals(3, strandEdges.get(1).getEnd());
        Assert.assertEquals(2, strandEdges.size());
    }

    /**
     * Test if all relationships get retrieved correctly.
     */
    @Test
    public void testGenomeRetrieval() {
        Genome genome = db.returnGenome("AA");
        Assert.assertEquals("AA", genome.getId());
        Assert.assertEquals(1, genome.getStrands().get(0).getId());
        Assert.assertEquals(2, genome.getStrands().get(1).getId());
        Assert.assertEquals(3, genome.getStrands().get(2).getId());

        genome = db.returnGenome("BB");
        Assert.assertEquals("BB", genome.getId());
        Assert.assertEquals(3, genome.getStrands().get(0).getId());
    }

    /**
     * Test if all relationships get retrieved correctly.
     */
    @Test
    public void testPhyloRetrieval() {
        Set<String> wanted = new HashSet<String>();
        wanted.add("BB");
        wanted.add("CC");
        Assert.assertEquals(db.returnDescGenome("3"), wanted);
        wanted.add("DD");
        Assert.assertEquals(db.returnDescGenome("2"), wanted);
    }
    
    /**
     * Tests if the metadata gets retrieved correctly.
     */
    @Test
    public void testLoadGenomeMetadataFromCSV() {
        db.loadGenomeMetadataFromResources("data/metadata.csv");
        HashMap<String,GenomeMetadata> metadata = db.getAllGenomeMetadata();
        Assert.assertEquals(metadata.get("TKK-01-0055").getLineage(),"LIN 2");        
    }

    /**
     * Deletes the created database.
     */
    @AfterClass
    public static void deleteDB() {
        db.deleteDatabase();
        File f = new File("test.db");
        Assert.assertTrue(!f.exists());
    }
}