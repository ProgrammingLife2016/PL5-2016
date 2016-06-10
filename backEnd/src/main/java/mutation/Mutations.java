package mutation;

import genome.GenomeGraph;
import genome.Strand;
import genome.StrandEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
		HashMap<Integer, Strand> strands = genomeGraph.getStrands();
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
		for (int i = 0; i < start.getOutgoingEdges().size() - 1; i++) {
			Strand firstEdgeEnd = strands.get(start.getOutgoingEdges().get(i).getEnd().getId());
			if (firstEdgeEnd.getSequence().length() == 1) {
				for (int j = i + 1; j < start.getOutgoingEdges().size(); j++) {
					Strand secondEdgeEnd = strands.get(start.getOutgoingEdges().get(j).getEnd().getId());
					if (secondEdgeEnd.getSequence().length() == 1) {
						for (StrandEdge edge1 : firstEdgeEnd.getOutgoingEdges()) {
							for (StrandEdge edge2 : secondEdgeEnd.getOutgoingEdges()) {
								if (edge1.getEnd().getId() == edge2.getEnd().getId()) {
									start.addMutation(new MutationSNP(
														MutationType.SNP,
														firstEdgeEnd.getGenomes(),
														secondEdgeEnd.getGenomes(),
														start,
														strands.get(edge1.getEnd().getId()),
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
		for (StrandEdge edge : start.getOutgoingEdges()) {
			if (start.getSequence().equals(strands.get(edge.getEnd().getId()).getSequence())) {
				Strand mutated = strands.get(edge.getEnd().getId());
				HashSet<String> reference = new HashSet<String>(start.getGenomes());
				reference.removeAll(mutated.getGenomes());
				start.addMutation(new MutationOther(
										MutationType.TANDEMDUPLICATION,
										reference,
										strands.get(edge.getEnd().getId()).getGenomes(),
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
		for (StrandEdge edge1 : start.getOutgoingEdges()) {
			for (StrandEdge edge2 : start.getOutgoingEdges()) {
				if (!edge1.equals(edge2)) {
					Strand end = strands.get(edge1.getEnd().getId());
					Strand mutated = strands.get(edge2.getEnd().getId());
					HashSet<String> reference = new HashSet<String>(start.getGenomes());
					reference.retainAll(end.getGenomes());
					for (StrandEdge edge3 : strands.get(edge2.getEnd().getId()).getOutgoingEdges()) {
						if (edge3.getEnd().getId() == end.getId()) {
							HashSet<String> other = new HashSet<String>(mutated.getGenomes());
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
