package phylogenetictree;

import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the PhylogeneticNode class.
 * Created by Matthijs on 17-5-2016.
 */
public class PhylogeneticNodeTest {
    private PhylogeneticNode node; //The node to test.
    private PhylogeneticNode child1; //The first child of node;
    private PhylogeneticNode child2; //The second child of node;


    /**
     * Set up the tests.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        node = new PhylogeneticNode(null, 0);
        child1 = new PhylogeneticNode(node, 0);
        child1.setNameLabel("child1");
        child2 = new PhylogeneticNode(node, 1);
        child2.setNameLabel("child2");
        node.addChild(child1);
        node.addChild(child2);

    }

    /**
     * Test if adaptChild adapts a treeNode correctly to a PhylogeneticNode.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAdaptChild() throws Exception {
        TreeNode treeRoot = new TreeNode();
        TreeNode tree1 = new TreeNode();
        tree1.setName("child1");
        TreeNode tree2 = new TreeNode();
        tree2.setName("child2");
        treeRoot.addChild(tree1);
        treeRoot.addChild(tree2);

        PhylogeneticNode testNode = new PhylogeneticNode(treeRoot, null, 1., 0);
        assertEquals(testNode.getNameLabel(), "");
        assertEquals(testNode.getGenomes().size(), 2);
        for (int i = 0; i < 2; i++) {
            PhylogeneticNode child = testNode.getChildren().get(i);
            assertEquals(child.getNameLabel(), "child" + (i + 1));
        }


    }

    /**
     * Test if get a node with a certain label behaves correcly.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetNodeWithLabel() throws Exception {
        assertEquals(node.getNodeWithLabel("child1"), child1);
        assertEquals(node.getNodeWithLabel("child2"), child2);
    }


    /**
     * Test setting the namelabel.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetNameLabel() throws Exception {
        assertEquals("", node.getNameLabel());
        node.setNameLabel("asdf");
        assertEquals("asdf", node.getNameLabel());
    }

    /**
     * Test the getter of Namelabel.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetNameLabel() throws Exception {
        assertEquals(child1.getNameLabel(), "child1");
        assertEquals(child2.getNameLabel(), "child2");

    }

    /**
     * Test the getter of distance.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetDistance() throws Exception {
        assertEquals(node.getDistance(), 0., 0.00001);
    }

    /**
     * Test the getter for genomes.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetGenomes() throws Exception {
        assertEquals(node.getGenomes().get(0), "child1");
        assertEquals(node.getNodeWithLabel("child1").getGenomes().get(0), "child1");
    }

    /**
     * Test the addition of a genome to a node.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddGenome() throws Exception {
        String genome = "genome2";
        assertEquals(node.getGenomes().size(), 2);
        child1.addGenome(genome);
        assertEquals(node.getGenomes().size(), 3);
        assertEquals(node.getGenomes().get(2), "genome2");

    }
}