package newickTest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import phylogeneticTree.PhylogeneticTree;;


/**
 * Created by Matthijs on 4-5-2016.
 * Test tree: https://en.wikipedia.org/wiki/File:NewickExample.svg
 */
public class TreeParserTest {

	/**
	 * The tested tree.
	 */
    private PhylogeneticTree tree;

    /**
     * Setting up the testing.
     */
    @Before
    public void setUp() {
        tree = new PhylogeneticTree();
        tree.parseTree("src/testFile");
    }

    /**
     * Test the number of children.
     */
    @Test
    public void testRootSize() {
        assertEquals(tree.getRoot().getChildren().size(), 3);
    }

    /**
     * Test the distance between the root and a node.
     */
    @Test
    public void testPathEnd() {
        assertEquals(tree.getNode("D").getDistance(), 0.4, 0.001);
    }

    /**
     * Test the distance to a middle node.
     */
    @Test
    public void testPathMiddle() {
        assertEquals(tree.getNode("").getDistance(), 0.0, 0.001);
    }
}
