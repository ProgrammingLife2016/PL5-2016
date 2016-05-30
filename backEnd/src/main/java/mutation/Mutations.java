package mutation;

import java.util.ArrayList;
import java.util.HashMap;

import controller.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;

/**
 * The Class AllMutations.
 *
 * @author Jeffrey Helgers.
 * This class computes all the mutations in the given dataset.
 */
public class Mutations {

	/** The genome graph. */
	private GenomeGraph genomeGraph;
	
	/** The mutations. 
	 * For now all the mutations will be stored in a ArrayList.
	 */
	private ArrayList<AbstractMutation> mutation;
	
	/**
	 * Constructor to create.
	 *
	 * @param graph The genome graph.
	 */
	public Mutations(GenomeGraph graph) {
		mutation = new ArrayList<>();
		this.genomeGraph = graph;
	}
	
	/**
	 * Compute all the mutations in the graph.
	 * From all the start Strands.
	 */
	public void computeAllMutations() {
		HashMap<Integer, Strand> strands = genomeGraph.getStrandNodes();
		for (Strand strand : strands.values()) {
			mutationsOnStrand(strand, strands);
		}
	}
	
	/**
	 * Compute mutations that can appear form a start Strand.
	 * When there is only one edge, there can't be a mutation.
	 * @param start The start Strand.
	 * @param strands All the Strands in the graph.
	 */
	private void mutationsOnStrand(Strand start, HashMap<Integer, Strand> strands) {
		if (start.getEdges().size() > 1) {
			for (int i = 0; i < start.getEdges().size() - 1; i++) {
				Strand next1 = strands.get(start.getEdges().get(i).getEnd());
				for (int j = i + 1; j < start.getEdges().size(); j++) {
					Strand next2 = strands.get(start.getEdges().get(j).getEnd());
					checkMutation(start, next1, next2);
				}
			}
		}
	}
	
	/**
	 * Checks if there is a mutation from the start Strand using these two edges.
	 * @param start		The start Strand.
	 * @param next1		The Strand on the first edge.
	 * @param next2		The Strand on the second edge.
	 * @param strands	All the strands.
	 */
	private void checkMutation(Strand start, Strand next1, Strand next2) {
		for (StrandEdge edge1 : next1.getEdges()) {
			if (edge1.getEnd() == next2.getId()) {
				ArrayList<String> genomesInBothStrands = new ArrayList<>(start.getGenomes());
				genomesInBothStrands.retainAll(next2.getGenomes());
				ArrayList<String> genomesInOriginal = new ArrayList<>(next1.getGenomes());
				genomesInOriginal.retainAll(genomesInBothStrands);
				genomesInBothStrands.removeAll(genomesInOriginal);
				MutationIndel indel = new MutationIndel(MutationType.DELETION, genomesInOriginal,
						genomesInBothStrands, start, next2, new ArrayList<Strand>());
				mutation.add(indel);
				return;
			}
		}
		for (StrandEdge edge2 : next2.getEdges()) {
			if (edge2.getEnd() == next1.getId()) {
				ArrayList<String> genomesInBothStrands = new ArrayList<>(start.getGenomes());
				genomesInBothStrands.retainAll(next2.getGenomes());
				ArrayList<String> genomesInMutation = new ArrayList<>(next1.getGenomes());
				genomesInMutation.retainAll(genomesInBothStrands);
				genomesInBothStrands.removeAll(genomesInMutation);
				MutationIndel indel = new MutationIndel(MutationType.INSERTION, 
						genomesInBothStrands, genomesInMutation, 
						start, next1, new ArrayList<Strand>());
				mutation.add(indel);
				return;
			} 
		}
	}
	
	/**
	 * Get the mutations.
	 * @return Mutations.
	 */
	public ArrayList<AbstractMutation> getMutation() {
		return mutation;
	}
}
