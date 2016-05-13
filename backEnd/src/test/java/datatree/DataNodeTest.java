package datatree;

import genome.Strand;
import net.sourceforge.olduvai.treejuxtaposer.drawer.TreeNode;
import org.junit.Before;
import org.junit.Test;
import phylogenetictree.PhylogeneticNode;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Class testing datanode.
 * Created by Matthijs on 13-5-2016.
 */
public class DataNodeTest {

    private DataNode node; //the testnode.
    private DataNode child; //test child.

    /**
     * Setting up the test class.
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        node = new DataNode(null, 0);
        child = new DataNode(node, 0);
        ArrayList<String> childGenomes = new ArrayList<>();
        ArrayList<String> parentGenomes = new ArrayList<>();
        childGenomes.add("1");
        parentGenomes.add("1");
        parentGenomes.add("2");
        child.setGenomes(childGenomes);
        node.addChild(child);
        node.setGenomes(parentGenomes);

    }

    /**
     * Test the phylo adapter.
     */
    @Test
    public void testPhyloConstructor() {
        TreeNode treeNode = new TreeNode();
        PhylogeneticNode phylo = new PhylogeneticNode(treeNode, null, 0., 0);
        phylo.addGenome("1");
        phylo.addGenome("2");
        PhylogeneticNode child = new PhylogeneticNode(treeNode, phylo, 0., 0);
        child.addGenome("1");
        phylo.addChild(child);

        DataNode testNode = new DataNode(phylo, null, 0);
        assertEquals(testNode.getGenomes(), phylo.getGenomes());
        assertEquals(testNode.getChildWithGenome("1").getId(), child.getId());
    }

    /**
     * Test getting the child with specific genome.
     * @throws Exception if fail.
     */
    @Test
    public void testGetChildWithGenome() throws Exception {
        assertEquals(child, node.getChildWithGenome("1"));
    }

    /**
     * Test setting the strands.
     * @throws Exception if fail.
     */
    @Test
    public void testSetStrands() throws Exception {
        String[] strandGenomes = {"1"};
        Strand strand = new Strand(1, "tagc", strandGenomes, "1", 0);
        ArrayList<Strand> strands = new ArrayList<>();
        strands.add(strand);
        node.setStrands(strands);

        assertEquals(node.getStrands(), strands);
        assertEquals(node.getStrands().size(), 1);
    }

    /**
     * Test setting the level.
     * @throws Exception if fail.
     */
    @Test
    public void testSetLevel() throws Exception {
        assertEquals(node.getLevel(), 0);
        node.setLevel(5);
        assertEquals(node.getLevel(), 5);
    }

    /**
     * Test setting the genomes.
     * @throws Exception if fail.
     */
    @Test
    public void testSetGenomes() throws Exception {
        ArrayList<String> childGenomes = new ArrayList<>();
        ArrayList<String> testGenomes = new ArrayList<>();
        testGenomes.add("5");
        childGenomes.add("1");
        assertEquals(child.getGenomes(), childGenomes);
        child.setGenomes(testGenomes);
        assertEquals(child.getGenomes(), testGenomes);
    }
}