package mutation;

import genome.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
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
		graph = Mockito.mock(GenomeGraph.class);
		mutations = new Mutations(graph);
		strand1 = Mockito.mock(Strand.class);
		strand2 = Mockito.mock(Strand.class);
		strand3 = Mockito.mock(Strand.class);
		strand4 = Mockito.mock(Strand.class);
		strands = new HashMap<>();
		strands.put(0, strand1);
		strands.put(1, strand2);
		strands.put(2, strand3);
		strands.put(3, strand4);
		captor = new ArgumentCaptor<AbstractMutation>();
		Mockito.when(graph.getStrands()).thenReturn(strands);
		Mockito.when(strand1.getId()).thenReturn(0);
		Mockito.when(strand2.getId()).thenReturn(1);
		Mockito.when(strand3.getId()).thenReturn(2);
		Mockito.when(strand4.getId()).thenReturn(3);
		Mockito.when(strand1.getSequence()).thenReturn("tagc");
	}

	/**
	 * Test a graph with no mutation.
	 */
	@SuppressWarnings("CPD-START")
	@Test
	public void testNoMutation() {
		Mockito.when(strand1.getOutgoingEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(new Strand(0), new Strand(1)), 
						new StrandEdge(new Strand(0), new Strand(2)))));
		Mockito.when(strand2.getOutgoingEdges()).thenReturn(
				new ArrayList<>(Arrays.asList(new StrandEdge(new Strand(1), 
				new Strand(3)))));
		Mockito.when(strand3.getOutgoingEdges()).thenReturn(new ArrayList<>());
		Mockito.when(strand4.getOutgoingEdges()).thenReturn(new ArrayList<>());
		Mockito.when(strand2.getSequence()).thenReturn("aa");
		Mockito.when(strand3.getSequence()).thenReturn("ac");
		Mockito.when(strand4.getSequence()).thenReturn("ag");
		mutations.computeAllMutations();
		Mockito.verify(strand1, Mockito.never()).addMutation(Matchers.any());
	}
	
	/**
	 * Test a graph with an indel.
	 */
	@Test
	public void testMutationIndel() {
		Mockito.when(strand1.getOutgoingEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(new Strand(0), new Strand(1)), 
						new StrandEdge(new Strand(0), new Strand(2)))));
		Mockito.when(strand2.getOutgoingEdges()).thenReturn(
				new ArrayList<>(Arrays.asList(new StrandEdge(new Strand(0), 
						new Strand(2)))));
		Mockito.when(strand3.getOutgoingEdges()).thenReturn(new ArrayList<>());
		Mockito.when(strand2.getSequence()).thenReturn("aa");
		Mockito.when(strand3.getSequence()).thenReturn("ac");
		mutations.computeAllMutations();
		Mockito.verify(strand1).addMutation(captor.capture());
		assertEquals(captor.getValue().getMutationType(), MutationType.INDEL);
	}
	
	/**
	 * Test a graph with a SNP.
	 */
	@Test
	public void testMutationSNP() {
		Mockito.when(strand1.getOutgoingEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(new Strand(0), new Strand(1)), 
						new StrandEdge(new Strand(0), new Strand(2)))));
		Mockito.when(strand2.getOutgoingEdges()).thenReturn(
				new ArrayList<>(Arrays.asList(new StrandEdge(new Strand(1), new Strand(3)))));
		Mockito.when(strand3.getOutgoingEdges()).thenReturn(
				new ArrayList<>(Arrays.asList(new StrandEdge(new Strand(2), new Strand(3)))));
		Mockito.when(strand4.getOutgoingEdges()).thenReturn(new ArrayList<>());
		Mockito.when(strand2.getSequence()).thenReturn("a");
		Mockito.when(strand3.getSequence()).thenReturn("c");
		Mockito.when(strand4.getSequence()).thenReturn("aaa");
		mutations.computeAllMutations();
		Mockito.verify(strand1).addMutation(captor.capture());
		assertEquals(captor.getValue().getMutationType(), MutationType.SNP);
	}

	/**
	 * Test a graph with a tandem duplication.
	 */
	@SuppressWarnings("CPD-END")
	@Test
	public void testMutationTandemDuplication() {
		Mockito.when(strand1.getOutgoingEdges()).thenReturn(new ArrayList<>(
				Arrays.asList(new StrandEdge(new Strand(0), new Strand(1)), 
						new StrandEdge(new Strand(0), new Strand(2)))));
		Mockito.when(strand2.getOutgoingEdges()).thenReturn(new ArrayList<>());
		Mockito.when(strand3.getOutgoingEdges()).thenReturn(new ArrayList<>());
		Mockito.when(strand2.getSequence()).thenReturn("a");
		Mockito.when(strand3.getSequence()).thenReturn("tagc");
		mutations.computeAllMutations();
		Mockito.verify(strand1).addMutation(captor.capture());
		assertEquals(captor.getValue().getMutationType(), MutationType.TANDEMDUPLICATION);
	}

}
