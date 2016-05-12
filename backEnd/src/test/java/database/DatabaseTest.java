package database;

import controller.Controller;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parser.Parser;

import java.io.File;

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
    }

    /**
     * The actual tests, currently checks if file indeed gets created.
     */
    @Test
    public void test() {
        File f = new File("test.db");
        Assert.assertTrue(f.exists());
    }

    /**
     * Deletes the created database.
     */
    @After
    public void deleteDB() {
        db.deleteDatabase();
    }
}