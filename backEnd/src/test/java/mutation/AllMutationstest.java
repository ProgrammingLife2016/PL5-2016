package mutation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import genome.DataContainer;
import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the AllMutations and ComputeMutation classes.
 */
public class AllMutationstest {

	private DataContainer dc;
	private Genome g1;
	private Genome g2;
	private Node n1;
	private Node n2;
	private Node n3;
	private Node n4;
	private ArrayList<Mutation> result;

	/**
	 * Set up the nodes, gemomes and data container that are used.
	 * @throws Exception
	 */
	@Before
	public void setUp() {
		dc = new DataContainer();
		g1 = new Genome("ref1");
		g2 = new Genome("ref2");
		n1 = new Node(1, "A", new String[1], "ref1", 1);
		n2 = new Node(2, "C", new String[1], "ref2", 1);
		n3 = new Node(3, "T", new String[1], "ref3", 1);
		n4 = new Node(4, "G", new String[1], "ref4", 1);
		result = new ArrayList<>();
	}
	
	/**
	 * Tets two genomes with no mutations.
	 */
	@Test
	public void testNoMutation() {
		String[] common = { g1.getId(), g2.getId() };
		n1.setGenomes(common);
		n2.setGenomes(common);
		dc.addNode(n1);
		dc.addNode(n2);
		AllMutations mutations = new AllMutations(dc);
		result = mutations.getGenomeMutations(g1.getId());
		assertEquals(result.size(), 0);
	}
	
	/**
	 * Test the insertion mutation.
	 */
	@Test
	public void testInsertion() {
		String[] common = { g1.getId(), g2.getId() };
		String[] one = { g2.getId() };
		n1.setGenomes(common);
		n2.setGenomes(one);
		n3.setGenomes(one);
		n4.setGenomes(common);
		dc.addNode(n1);
		dc.addNode(n2);
		dc.addNode(n3);
		dc.addNode(n4);
		AllMutations mutations = new AllMutations(dc);
		result = mutations.getGenomeMutations(g1.getId());
		assertEquals(result.size(), 1);
		assertTrue(result.get(0) instanceof Insertion);
	}
	
	/**
	 * Test the deletion mutation.
	 */
	@Test
	public void testDeletion() {
		String[] common = { g1.getId(), g2.getId() };
		String[] one = { g2.getId() };
		n1.setGenomes(common);
		n2.setGenomes(one);
		n3.setGenomes(one);
		n4.setGenomes(common);
		dc.addNode(n1);
		dc.addNode(n2);
		dc.addNode(n3);
		dc.addNode(n4);
		AllMutations mutations = new AllMutations(dc);
		result = mutations.getGenomeMutations(g2.getId());
		assertEquals(result.size(), 1);
		assertTrue(result.get(0) instanceof Deletion);
	}
}
