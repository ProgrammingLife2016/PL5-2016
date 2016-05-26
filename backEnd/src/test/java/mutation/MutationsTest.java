package mutation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.*;
import controller.GenomeGraph;
import genome.Genome;

public class MutationsTest {

	private static Mutations mutations;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		GenomeGraph graph = mock(GenomeGraph.class);
		HashMap<String, Genome> genomes = new HashMap<>();
		genomes.put("genome1", mock(Genome.class));
		genomes.put("genome", mock(Genome.class));
		when(graph.getGenomes()).thenReturn(genomes);
		mutations = new Mutations(graph);
	}

	@Test
	public void testGetCommonStrands() {
		
	}

}
