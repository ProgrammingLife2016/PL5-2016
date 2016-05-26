package mutation;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import controller.GenomeGraph;
import genome.Genome;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the Mutations class.
 */
public class MutationsTest {

	private static Mutations mutations;
	
	/**
	 * Set up the mutation object.
	 * @throws Exception if fail.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		GenomeGraph graph = mock(GenomeGraph.class);
		HashMap<String, Genome> genomes = new HashMap<>();
		genomes.put("genome1", mock(Genome.class));
		genomes.put("genome", mock(Genome.class));
		when(graph.getGenomes()).thenReturn(genomes);
		mutations = new Mutations(graph);
	}

	/**
	 * Yet unused test.
	 */
	@Test
	public void testGetCommonStrands() {
		assertEquals(1, 1);
	}

}
