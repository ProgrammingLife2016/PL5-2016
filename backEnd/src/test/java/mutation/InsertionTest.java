package mutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 * Tets the insertion branch.
 */
public class InsertionTest {

	private Insertion insertion;
	private Node start;
	private Node end;
	private Genome reference;
	private Genome other;
	
	/**
	 * Setup the insertion class.
	 */
	@Before
	public void setUp() {
		reference = new Genome("base");
		other = new Genome("other");
		start = new Node(1, "A", new String[1], reference.getId(), 1);
		end = new Node(2, "CT", new String[1], other.getId(), 1);
		insertion = new Insertion(reference, other, start, end);
	}

	/**
	 * Test get start node.
	 */
	@Test
	public void testGetStart() {
		assertEquals(insertion.getStart(), start);
	}
	
	/**
	 * Test get end node.
	 */
	@Test
	public void testGetEnd() {
		assertEquals(insertion.getEnd(), end);
	}
	
	/**
	 * Test get reference genome.
	 */
	@Test
	public void testGetReference() {
		assertEquals(insertion.getReference(), reference);
	}
	
	/**
	 * Test get other genome.
	 */
	@Test
	public void testGetOther() {
		assertEquals(insertion.getOther(), other);
	}

}
