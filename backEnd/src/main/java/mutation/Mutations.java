package mutation;

import controller.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * The Class AllMutations.
 *
 * @author Jeffrey Helgers.
 * This class computes all the mutations in the given dataset.
 */
public class Mutations {

	/** 
	 * The genome graph. 
	 */
	private GenomeGraph genomeGraph;
	
	/**
	 * Constructor to create.
	 *
	 * @param graph The genome graph.
	 */
	public Mutations(GenomeGraph graph) {
		this.genomeGraph = graph;
	}
	
	/**
	 * Compute all the mutations in the graph.
	 * From all the start Strands.
	 */
	public void computeAllMutations() {
		HashMap<Integer, Strand> strands = genomeGraph.getStrandNodes();
		for (Strand strand : strands.values()) {
			findIndel(strand, strands);
			findSNP(strand, strands);
			findTandemDuplication(strand, strands);
		}
	}
	
	/**
	 * Check if there is a SNP mutation starting from the start strand.
	 * @param start The start strand.
	 * @param strands All the strands.
	 */
	private void findSNP(Strand start, HashMap<Integer, Strand> strands) {
		for (int i = 0; i < start.getEdges().size() - 1; i++) {
			Strand firstEdgeEnd = strands.get(start.getEdges().get(i).getEnd());
			if (firstEdgeEnd.getSequence().length() == 1) {
				for (int j = i + 1; j < start.getEdges().size(); j++) {
					Strand secondEdgeEnd = strands.get(start.getEdges().get(j).getEnd());
					if (secondEdgeEnd.getSequence().length() == 1) {
						for (StrandEdge edge1 : firstEdgeEnd.getEdges()) {
							for (StrandEdge edge2 : secondEdgeEnd.getEdges()) {
								if (edge1.getEnd() == edge2.getEnd()) {
									start.addMutation(new MutationSNP(
														MutationType.SNP,
														firstEdgeEnd.getGenomes(),
														secondEdgeEnd.getGenomes(),
														start,
														strands.get(edge1.getEnd()),
														firstEdgeEnd,
														secondEdgeEnd));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Check if there is a tandem duplication mutation starting from the start strand.
	 * @param start The start strand.
	 * @param strands All the strands.
	 */
	private void findTandemDuplication(Strand start, HashMap<Integer, Strand> strands) {
		for (StrandEdge edge : start.getEdges()) {
			if (start.getSequence().equals(strands.get(edge.getEnd()).getSequence())) {
				Strand mutated = strands.get(edge.getEnd());
				ArrayList<String> reference = new ArrayList<>(start.getGenomes());
				reference.removeAll(mutated.getGenomes());
				start.addMutation(new MutationOther(
										MutationType.TANDEMDUPLICATION,
										reference,
										strands.get(edge.getEnd()).getGenomes(),
										start,
										new ArrayList<>(Arrays.asList(mutated))));
			}
		}
	}
	
	/**
	 * Check if there is an indel mutation starting from the start strand.
	 * @param start The start strand.
	 * @param strands All the strands.
	 */
	private void findIndel(Strand start, HashMap<Integer, Strand> strands) {
		for (StrandEdge edge1 : start.getEdges()) {
			for (StrandEdge edge2 : start.getEdges()) {
				if (!edge1.equals(edge2)) {
					Strand end = strands.get(edge1.getEnd());
					Strand mutated = strands.get(edge2.getEnd());
					ArrayList<String> reference = new ArrayList<>(start.getGenomes());
					reference.retainAll(end.getGenomes());
					for (StrandEdge edge3 : strands.get(edge2.getEnd()).getEdges()) {
						if (edge3.getEnd() == end.getId()) {
							ArrayList<String> other = new ArrayList<>(mutated.getGenomes());
							other.removeAll(reference);
							reference.removeAll(other);
							start.addMutation(new MutationIndel(
													MutationType.INDEL,
													reference,
													other,
													start,
													end,
													new ArrayList<>(Arrays.asList(mutated))));	
						}
					}
				}
			}
		}
	}
}
