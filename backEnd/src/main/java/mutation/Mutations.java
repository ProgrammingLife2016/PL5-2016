package mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controller.GenomeGraph;
import genome.Genome;
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
	
	/** The mutations. */
	private HashMap<String, HashMap<String, ArrayList<AbstractMutation>>> mutations;
		
	private ArrayList<AbstractMutation> mutation;
	
	/**
	 * Constructor to create.
	 *
	 * @param genomeGraph the genome graph
	 */
	public Mutations(GenomeGraph graph) {
		mutations = new HashMap<>();
		mutation = new ArrayList<>();
		this.genomeGraph = graph;
		setKeys(genomeGraph.getGenomes().keySet());
	}
	
	/**
	 * Sets the keys.
	 *
	 * @param keys the new keys
	 */
	private void setKeys(Set<String> keys) {
		for (String key1 : keys) {
			for (String key2 : keys) {
				mutations.put(key1, new HashMap<>());
				if (!key1.equals(key2)) {
					mutations.get(key1).put(key2, new ArrayList<>());
				}
			}
		}
	}
	
	/**
	 * Add a mutation between two genomes.
	 * @param reference The key from the reference genome.
	 * @param other The key from the other geneme.
	 * @param mutation The mutation.
	 */
	public void addMutation(String reference, String other, AbstractMutation mutation) {
		mutations.get(reference).get(other).add(mutation);
	}
	
	/**
	 * Get the mutations with a genome as compared to.
	 * @param genome The compared genome.
	 * @return The mutations.
	 */
	public HashMap<String, ArrayList<AbstractMutation>> getGenomeMutations(String genome) {
		return mutations.get(genome);
	}
	
	/**
	 * Get the mutations between to genomes.
	 * @param reference The reference genome.
	 * @param other The other genome.
	 * @return The mutations.
	 */
	public ArrayList<AbstractMutation> getMutationBetweenGenomes(String reference, String other) {
		return mutations.get(reference).get(other);
	}
	
	public void getMutations() {
		HashMap<Integer, Strand> strands = genomeGraph.getStrandNodes();
		System.out.println(strands.size());
		ArrayList<Strand> startingStrands = genomeGraph.getStrartStrands();
		for (Strand start : startingStrands) {
			System.out.println(strands.size());
			test(start, strands, new ArrayList<Strand>());
		}
	}
	
	public void test(Strand start, HashMap<Integer, Strand> strands, ArrayList<Strand> visited) {
		visited.add(start);
		System.out.println(strands.size());
		if (start.getEdges().size() == 2) {
			checkIndel(start, strands);
		}
		for (StrandEdge edge : start.getEdges()) {
			Strand newStart = strands.get(edge.getEnd());
			if (!visited.contains(newStart)) {
				test(newStart, strands, visited);
			}
		}
	}
	
	public void checkIndel(Strand start, HashMap<Integer, Strand> strands) {
		Strand nextEdge1 = strands.get(start.getEdges().get(0).getEnd());
		Strand nextEdge2 = strands.get(start.getEdges().get(0).getEnd());
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
	
	public ArrayList<AbstractMutation> getMutation() {
		return mutation;
	}
}
