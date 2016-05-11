package mutation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import genome.Genome;
import genome.Node;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the deletion class.
 */
public class DeletionTest {

	private Deletion deletion;
	private ArrayList<Node> missing;
	
	/**
	 * Set up the deletion object.
	 */
	@Before
	public void setUp() {
		Genome reference = new Genome("base");
		Genome other = new Genome("other");
		missing = new ArrayList<>();
		missing.add(new Node(1, "A", new String[1], reference.getId(), 1));
		missing.add(new Node(2, "CT", new String[1], other.getId(), 1));
		deletion = new Deletion(reference, other, missing);
	}
	
	/**
	 * Test getting missed nodes.
	 */
	@Test
	public void testGetMissingNodes() {
		ArrayList<Node> getMissing = deletion.getMissingNodes();
		assertEquals(missing.size(), getMissing.size());
		assertEquals(missing.get(0), getMissing.get(0));
	}

}
