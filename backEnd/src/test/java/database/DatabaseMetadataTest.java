package database;

import genome.GenomeMetadata;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;

/**
 * Created by user on 11/05/16.
 */
public class DatabaseMetadataTest {

    private static Database db;

    /**
     * Loads the data from the test file.
     * Creates database file
     */
    @BeforeClass
    public static void setup() {
        db = new Database("test.db");
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

    /**
     * Tests if the metadata gets retrieved correctly.
     */
    @Test
    public void testLoadGenomeMetadataFromCSV() {
        db.loadGenomeMetadataFromResources("data/metadata.csv");
        HashMap<String, GenomeMetadata> metadata = db.getAllGenomeMetadata();
        Assert.assertTrue(metadata.get("TKK-01-0055") != null);
        Assert.assertEquals(metadata.get("TKK-01-0055").getLineage(), "LIN 2");
    }
}