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
			mutationsOnStrand(strand, strands);
		}
	}
	
	/**
	 * Check if there is a SNP mutation starting from the start strand.
	 * @param start The start strand.
	 * @param strands All the strands.
	 */
	private void findSNP(Strand start, ArrayList<Strand> strands) {
		for (int i = 0; i < start.getEdges().size() - 1; i++) {
			Strand firstEdgeEnd = strands.get(start.getEdges().get(i).getEnd());
			if (firstEdgeEnd.getSequence().length() == 1) {
				for (int j = i + 1; j < start.getEdges().size(); i++) {
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
	private void findTandemDuplication(Strand start, ArrayList<Strand> strands) {
		for (StrandEdge edge : start.getEdges()) {
			if(start.getSequence().equals(strands.get(edge.getEnd()).getSequence())) {
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
	private void findIndel(Strand start, ArrayList<Strand> strands) {
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
				MutationIndel indel = new MutationIndel(MutationType.INDEL, genomesInOriginal,
						genomesInBothStrands, start, next2, new ArrayList<Strand>());
				start.addMutation(indel);
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
				MutationIndel indel = new MutationIndel(MutationType.INDEL, 
						genomesInBothStrands, genomesInMutation, 
						start, next1, new ArrayList<Strand>());
				start.addMutation(indel);
				return;
			} 
		}
	}
}
