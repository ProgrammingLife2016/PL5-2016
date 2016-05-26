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
	public void getMutations() {
		HashMap<Integer, Strand> strands = genomeGraph.getStrandNodes();
		ArrayList<Strand> startingStrands = genomeGraph.getStrartStrands();
		for (Strand start : startingStrands) {
			mutationsOnStrand(start, strands, new ArrayList<Strand>());
		}
	}
	
	/**
	 * Compute mutations that can appear form a start Strand.
	 * @param start The start Strand.
	 * @param strands All the Strands in the graph.
	 * @param visited The Strands that are already visited, to prevent loops.
	 */
	public void mutationsOnStrand(Strand start, HashMap<Integer, 
			Strand> strands, ArrayList<Strand> visited) {
		visited.add(start);
		if (start.getEdges().size() > 1) {
			checkIndel(start, strands);
		}
		for (StrandEdge edge : start.getEdges()) {
			Strand newStart = strands.get(edge.getEnd());
			if (!visited.contains(newStart)) {
				mutationsOnStrand(newStart, strands, visited);
			}
		}
	}
	
	/**
	 * Check if a indel appears on the Strand.
	 * @param start The Strand.
	 * @param strands All the Strands.
	 */
	public void checkIndel(Strand start, HashMap<Integer, Strand> strands) {
		for (int i = 0; i < start.getEdges().size() - 1; i++) {
			Strand nextEdge1 = strands.get(start.getEdges().get(i).getEnd());
			for (int j = i + 1; j < start.getEdges().size(); j++) {
				Strand nextEdge2 = strands.get(start.getEdges().get(j).getEnd());
				for (StrandEdge edge : nextEdge1.getEdges()) {
					if (edge.getEnd() == nextEdge2.getId()) {
						MutationIndel indel = new MutationIndel(MutationType.DELETION, null,
								null, start, nextEdge2, new ArrayList<Strand>());
						mutation.add(indel);
					}
				}
				for (StrandEdge edge : nextEdge2.getEdges()) {
					if (edge.getEnd() == nextEdge1.getId()) {
						MutationIndel indel = new MutationIndel(MutationType.INSERTION, null,
								null, start, nextEdge1, new ArrayList<Strand>());
						mutation.add(indel);
					}
				}
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
