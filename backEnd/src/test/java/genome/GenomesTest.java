package genome;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;


/**
 * @author Jeffrey Helgers.
 */
public class GenomesTest {

	private Genome genome;
	
	/**
	 * Setting up the genome to test with.
	 */
	@Before
	public void setUp() {
		genome = new Genome("test");
	}
	
	/**
	 * Tests adding nodes to the genome.
	 */
	@Test
	public void testAddNode() {
		ArrayList<Node> res = new ArrayList<Node>();
		assertEquals(genome.getNodes(), res);
		String[] genomes = {"ref1", "ref2"};
    	Node node = new Node(1, "AA", genomes, "ref1", 0);
    	res.add(node);
    	genome.addNode(node);
    	assertEquals(genome.getNodes(), res);
	}
}
