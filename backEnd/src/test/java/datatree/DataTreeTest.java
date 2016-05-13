package datatree;

import genome.Genome;
import genome.Strand;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Class to test DataTree.
 * Created by Matthijs on 13-5-2016.
 */
public class DataTreeTest {

    private DataTree tree; //test tree.
    private DataNode root; //root of the tree;
    private DataNode child1; //child of the tree;
    private DataNode child2; //child of the tree;
    private ArrayList<Genome> genomes; //genomes contained in the tree;

    /**
     * Set up the tests.
     * @throws Exception if fail.
     */
    @SuppressWarnings("checkstyle:methodlength")
    @Before
    public void setUp() throws Exception {

        Genome genome1 = new Genome("1");
        Genome genome2 = new Genome("2");


        String[] strand1Genomes = {"1"};
        Strand strand1 = new Strand(1, "tagc", strand1Genomes, "1", 0);

        String[] strand2Genomes = {"2"};
        Strand strand2 = new Strand(10, "tagc", strand2Genomes, "2", 0);

        String[] strand12Genomes = {"1", "2"};
        Strand strand12 = new Strand(5, "tagc", strand12Genomes, "2", 0);

        genome1.addStrand(strand1);
        genome1.addStrand(strand12);
        genome2.addStrand(strand2);
        genome2.addStrand(strand12);


        genomes = new ArrayList<>();
        genomes.add(genome1);
        genomes.add(genome2);


        root = new DataNode(null, 0);
        child1 = new DataNode(root, 0);
        child2 = new DataNode(root, 1);
        ArrayList<String> child1Genomes = new ArrayList<>();
        ArrayList<String> child2Genomes = new ArrayList<>();
        ArrayList<String> parentGenomes = new ArrayList<>();
        child1Genomes.add("1");
        child2Genomes.add("2");
        parentGenomes.add("1");
        parentGenomes.add("2");
        child1.setGenomes(child1Genomes);
        child2.setGenomes(child2Genomes);
        root.addChild(child1);
        root.addChild(child2);
        root.setGenomes(parentGenomes);

        tree = new DataTree(root);
    }

    /**
     * Test getting the genome leaf.
     * @throws Exception if fail.
     */
    @Test
    public void testGetGenomeLeaf() throws Exception {
        assertEquals(tree.getRoot().getGenomeLeaf("1"), child1);
    }

    /**
     * Test adding a starnd.
     * @throws Exception if fail.
     */
    @Test
    public void testAddStrands() throws Exception {
        assertEquals(tree.getRoot().getStrands().size(), 0);
        tree.addStrands(genomes);
        assertEquals(root.getStrands().size(), 1);
        assertEquals(tree.getRoot().getGenomeLeaf("1").getStrands().size(), 1);
        assertEquals(tree.getRoot().getGenomeLeaf("2").getStrands().size(), 1);

    }

    /**
     * Test combining method and see if margin works.
     * @throws Exception if fail.
     */
    @Test
    public void testGetDataNodes() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("1");

        assertEquals(tree.getDataNodes(0, 10, genomes, 0).size(), 1);
        assertEquals(tree.getDataNodes(4, 4, genomes, 0).size(), 1);
        assertEquals(tree.getDataNodes(5, 10, genomes, 0).size(), 1);
        assertEquals(tree.getDataNodes(1, 11, genomes, 1).size(), 2);
    }

    /**
     * Test if a list of nodes is stripped from irrelevant information.
     * @throws Exception if fail.
     */
    @Test
    public void testFilterNodes() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("2");
        assertEquals(tree.getDataNodesForGenomes(genomes, 0).size(), 1);
        assertEquals(tree.getDataNodesForGenomes(genomes, 1).size(), 2);
        ArrayList<DataNode> testArray = tree.filterNodes(5, 5, 
        		tree.getDataNodesForGenomes(genomes, 1), genomes);
        assertEquals(testArray.size(), 2);
        assertEquals(testArray.get(0).getGenomes().size(), 1);
        assertEquals(testArray.get(1).getGenomes().size(), 1);

    }

    /**
     *Test if nodes are returned multiple genomes, and test if level works.
     * @throws Exception if fail.
     */
    @Test
    public void testGetDataNodesForGenomes() throws Exception {
        ArrayList<String> genomes = new ArrayList<>();
        genomes.add("1");
        genomes.add("2");
        assertEquals(tree.getDataNodesForGenomes(genomes, 0).size(), 1);
        assertEquals(tree.getDataNodesForGenomes(genomes, 1).size(), 3);
    }

    /**
     * Test if nodes are returned of a specific genome, and test if level works.
     * @throws Exception if fail.
     */
    @Test
    public void testGetDataNodesForGenome() throws Exception {
        assertEquals(tree.getDataNodesForGenome("1", 0).size(), 1);
        assertEquals(tree.getDataNodesForGenome("1", 1).size(), 2);
    }
}