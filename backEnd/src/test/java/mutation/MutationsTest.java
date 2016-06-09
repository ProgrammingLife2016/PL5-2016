package mutation;

import controller.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import org.mockito.ArgumentCaptor;

/**
 * 
 * @author Jeffrey Helgers.
 * Test the Mutations class.
 */
public class MutationsTest {

	private Mutations mutations;
	private GenomeGraph graph;
	private HashMap<Integer, Strand> strands;
	private	Strand strand1;
	private Strand strand2;
	private Strand strand3;
	private Strand strand4;
	private ArgumentCaptor<AbstractMutation> captor;
	
	/**
	 * Set up the mutation object.
	 * @throws Exception if fail.
	 */
	@Before
	public void setUp() throws Exception {
		graph = mock(GenomeGraph.class);
		mutations = new Mutations(graph);
		strand1 = mock(Strand.class);
		strand2 = mock(Strand.class);
		strand3 = mock(Strand.class);
		strand4 = mock(Strand.class);
		strands = new HashMap<>();
		strands.put(0, strand1);
		strands.put(1, strand2);
		strands.put(2, strand3);
		strands.put(3, strand4);
		captor = new ArgumentCaptor<AbstractMutation>();
		when(graph.getStrandNodes()).thenReturn(strands);
		when(strand1.getId()).thenReturn(0);
		when(strand2.getId()).thenReturn(1);
		when(strand3.getId()).thenReturn(2);
		when(strand4.getId()).thenReturn(3);
		when(strand1.getSequence()).thenReturn("tagc");
	}

	/**
	 * Test a graph with no mutation.
	 */
	@Test
	public void testNoMutation() {
		when(strand1.getEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(0, 1), new StrandEdge(0, 2))));
		when(strand2.getEdges()).thenReturn(new ArrayList<>(Arrays.asList(new StrandEdge(1, 3))));
		when(strand3.getEdges()).thenReturn(new ArrayList<>());
		when(strand4.getEdges()).thenReturn(new ArrayList<>());
		when(strand2.getSequence()).thenReturn("aa");
		when(strand3.getSequence()).thenReturn("ac");
		when(strand4.getSequence()).thenReturn("ag");
		mutations.computeAllMutations();
		verify(strand1, never()).addMutation(any());
	}
	
	/**
	 * Test a graph with an indel.
	 */
	@Test
	public void testMutationIndel() {
		when(strand1.getEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(0, 1), new StrandEdge(0, 2))));
		when(strand2.getEdges()).thenReturn(new ArrayList<>(Arrays.asList(new StrandEdge(1, 2))));
		when(strand3.getEdges()).thenReturn(new ArrayList<>());
		when(strand2.getSequence()).thenReturn("aa");
		when(strand3.getSequence()).thenReturn("ac");
		mutations.computeAllMutations();
		verify(strand1).addMutation(captor.capture());
		assertEquals(captor.getValue().getMutationType(), MutationType.INDEL);
	}
	
	/**
	 * Test a graph with a SNP.
	 */
	@Test
	public void testMutationSNP() {
		when(strand1.getEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(0, 1), new StrandEdge(0, 2))));
		when(strand2.getEdges()).thenReturn(new ArrayList<>(Arrays.asList(new StrandEdge(1, 3))));
		when(strand3.getEdges()).thenReturn(new ArrayList<>(Arrays.asList(new StrandEdge(2, 3))));
		when(strand4.getEdges()).thenReturn(new ArrayList<>());
		when(strand2.getSequence()).thenReturn("a");
		when(strand3.getSequence()).thenReturn("c");
		when(strand4.getSequence()).thenReturn("aaa");
		mutations.computeAllMutations();
		verify(strand1).addMutation(captor.capture());
		assertEquals(captor.getValue().getMutationType(), MutationType.SNP);
	}
	
	/**
	 * Test a graph with a tandem duplication.
	 */
	@Test
	public void testMutationTandemDuplication() {
		when(strand1.getEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(0, 1), new StrandEdge(0, 2))));
		when(strand2.getEdges()).thenReturn(new ArrayList<>());
		when(strand3.getEdges()).thenReturn(new ArrayList<>());
		when(strand2.getSequence()).thenReturn("a");
		when(strand3.getSequence()).thenReturn("tagc");
		mutations.computeAllMutations();
		verify(strand1).addMutation(captor.capture());
		assertEquals(captor.getValue().getMutationType(), MutationType.TANDEMDUPLICATION);
	}
}
