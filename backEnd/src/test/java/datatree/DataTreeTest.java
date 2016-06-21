package datatree;

import genome.Genome;
import strand.Strand;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Class to test DataTree.
 * Created by Matthijs on 13-5-2016.
 */
public class DataTreeTest {

	/**
	 * Tested tree.
	 */
    private DataTree tree;

    /**
     * The root of the tree.
     */
    private DataNode root;

    /**
     * Child of the root.
     */
    private DataNode child1;

    /**
     * Child of the root.
     */
    private DataNode child2;
    
    /**
     * Genomes contained in the tree.
     */
    private ArrayList<ArrayList<Genome>> genomeIDs;

    /**
     * A strand to test data return.
     */
    private Strand strand1;
    
    /**
     * A strand to test data return.
     */
    private Strand strand2;
    
    /**
     * A strand to test data return.
     */
    private Strand strand12;

    /**
     * Set up the tests.
     *
     * @throws Exception if fail.
     */
    @SuppressWarnings("checkstyle:methodlength")
    @Before
    public void setUp() throws Exception {
        Genome genome1 = new Genome("1");
        Genome genome2 = new Genome("2");

        String[] strand1Genomes = {"1"};
        HashSet<String> genomeSet = new HashSet<String>(Arrays.asList(strand1Genomes));
        strand1 = new Strand(1, "tagc", genomeSet, "1", 0);

        String[] strand2Genomes = {"2"};
        genomeSet = new HashSet<String>(Arrays.asList(strand2Genomes));
        strand2 = new Strand(10, "tagc", genomeSet, "2", 0);

        String[] strand12Genomes = {"1", "2"};
        genomeSet = new HashSet<String>(Arrays.asList(strand12Genomes));
        strand12 = new Strand(5, "tagc", genomeSet, "2", 0);

        genome1.addStrand(strand1);
        genome1.addStrand(strand12);
        genome2.addStrand(strand2);
        genome2.addStrand(strand12);

        ArrayList<Genome> genomes = new ArrayList<>();
        genomes.add(genome1);
        genomes.add(genome2);


        genomeIDs = new ArrayList<>();
        genomeIDs.add(new ArrayList<>(Arrays.asList(genome1)));
        genomeIDs.add(new ArrayList<>(Arrays.asList(genome2)));

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
        tree.addStrandsFromGenomes(genomes);
    }

    /**
     * Test getting the genome leaf.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetGenomeLeaf() throws Exception {
        assertEquals(tree.getRoot().getGenomeLeaf("1"), child1);
    }

    /**
     * Test adding a starnd.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddStrands() throws Exception {

        assertEquals(root.getStrands().size(), 1);
        assertEquals(tree.getRoot().getGenomeLeaf("1").getStrands().size(), 1);
        assertEquals(tree.getRoot().getGenomeLeaf("2").getStrands().size(), 1);

    }

    /**
     * Test combining method and see if margin works.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetDataNodes() throws Exception {

        assertEquals(tree.getStrands(-1, 10, genomeIDs, 21, false).size(), 3);
        assertEquals(tree.getStrands(4, 4, genomeIDs, 0, false).size(), 0);
        assertEquals(tree.getStrands(5, 10, genomeIDs, 0, false).size(), 0);
        assertEquals(tree.getStrands(5, 10, genomeIDs, 20, false).size(), 1);

    }

    /**
     * Test if a list of nodes is stripped from irrelevant information.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testFilterStrandsFromNodes() throws Exception {
        assertEquals(tree.getDataNodesForGenomes(genomeIDs, 0).size(), 1);
        assertEquals(tree.getDataNodesForGenomes(genomeIDs, 1).size(), 3);
        ArrayList<Strand> testArray = tree.filterStrandsFromNodes(0, 1,
                tree.getDataNodesForGenomes(genomeIDs, 1), genomeIDs, 20, false);

        assertEquals(testArray.size(), 3);
        assertTrue(testArray.contains(strand2));
        assertTrue(testArray.contains(strand12));


    }

    /**
     * Test if a list of nodes that is requested multiple times does not delete strands.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testFilterStrandsFromNodesMultiple() throws Exception {

        for (int i = 1; i < 5; i++) {
            ArrayList<Strand> testArray = tree.filterStrandsFromNodes(-10, 50,
                    tree.getDataNodesForGenomes(genomeIDs, i), genomeIDs, 40, false);

            assertEquals(testArray.size(), 3);
        }


    }

    /**
     * Test if nodes are returned multiple genomes, and test if level works.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetDataNodesForGenomes() throws Exception {
        assertEquals(tree.getDataNodesForGenomes(genomeIDs, 0).size(), 1);
        assertEquals(tree.getDataNodesForGenomes(genomeIDs, 1).size(), 3);

        for (int i = 1; i < 5; i++) {
            Set<DataNode> testArray = tree.getDataNodesForGenomes(genomeIDs, i);
            assertEquals(testArray.size(), 3);
            for (DataNode node : testArray) {
                assertEquals(node.getStrands().size(), 1);
            }
        }
    }

    /**
     * Test if nodes are returned of a specific genome, and test if level works.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetDataNodesForGenome() throws Exception {
        assertEquals(tree.getDataNodesForGenome(genomeIDs.get(0), 0).size(), 1);
        assertEquals(tree.getDataNodesForGenome(genomeIDs.get(0), 1).size(), 2);

    }
}