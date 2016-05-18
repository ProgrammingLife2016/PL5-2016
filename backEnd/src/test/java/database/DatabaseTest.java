package database;

import controller.Controller;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.tooling.GlobalGraphOperations;
import parser.Parser;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by user on 11/05/16.
 */
public class DatabaseTest {

    private Database db;

    /**
     * Loads the data from the test file.
     * Creates database file
     */
    @Before
    public void setup() {
        Controller dc = Parser.parse("data/test2.gfa");
        db = new Database("test.db", dc);
        dc.createNodesCSV("data/nodes.csv");
        dc.createEdgesCSV("data/edges.csv");
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
     * Test if all nodes from the test file get inserted.
     */
    @Test
    public void testNodeInsertion() {
        Iterable<Node> allNodes;
        String[] strands = {"", "AAAAAAAA", "A", "C", "T", "G", "GG", "TA", "GG",
                "CGATGCAA", "CG", "TAG", "AT", "GAG", "CAG", "ATA"};

        int i = 1;
        try (Transaction tx = db.getGraphService().beginTx()) {
            //Fetch all nodes from the database
            allNodes = GlobalGraphOperations.at(db.getGraphService()).getAllNodes();
            tx.success();

            //Check if all nodes are inserted correctly in the database
            for (Node a : allNodes) {
                Assert.assertEquals(a.getProperty("id"), (long) i);
                Assert.assertEquals(a.getProperty("sequence"), strands[i]);
                i++;
            }
        }

        //Make sure no extra nodes are inserted
        Assert.assertEquals(16, i);
    }

    /**
     * Test if all nodes from the test file get inserted.
     */
    @Test
    public void testEdgeInsertion() {
        Iterable<Relationship> allRelas;
        Set<String> strands = new HashSet<>();
        strands.add("1|2 AA");
        strands.add("2|3 AA");
        strands.add("3|10 AA");
        strands.add("10|11 AA");
        strands.add("11|12 AA");
        strands.add("12|13 AA");
        strands.add("13|14 AA");
        strands.add("14|15 AA");
        strands.add("1|3 BB");
        strands.add("3|5 BB");
        strands.add("5|7 BB");
        strands.add("7|8 BB");
        strands.add("8|9 BB");
        strands.add("9|12 BB");
        strands.add("12|13 BB");
        strands.add("13|15 BB");
        strands.add("1|3 CC");
        strands.add("3|5 CC");
        strands.add("5|6 CC");
        strands.add("6|8 CC");
        strands.add("8|9 CC");
        strands.add("9|12 CC");
        strands.add("12|15 CC");
        strands.add("1|3 DD");
        strands.add("3|5 DD");
        strands.add("5|6 DD");
        strands.add("6|9 DD");
        strands.add("9|12 DD");
        strands.add("12|15 DD");
        strands.add("1|3 EE");
        strands.add("3|4 EE");
        strands.add("4|12 EE");
        strands.add("12|15 EE");

        int i = 1;

        try (Transaction tx = db.getGraphService().beginTx()) {
            //fetch all relationships from the graph
            allRelas = GlobalGraphOperations.at(db.getGraphService()).getAllRelationships();

            //check if all relationships/edges are indeed in the database
            for (Relationship a : allRelas) {
                String coordinates = a.getStartNode().getProperty("id") + "|"
                        + a.getEndNode().getProperty("id");
                Assert.assertTrue(strands.contains(coordinates + " " + a.getProperty("genome")));
                i++;
            }
            tx.success();
        }

        //Make sure no extra edges are inserted
        Assert.assertEquals(i, 34);
    }

    /**
     * Deletes the created database.
     */
    @After
    public void deleteDB() {
        db.deleteDatabase();
        File f = new File("test.db");
        Assert.assertTrue(!f.exists());
    }
}