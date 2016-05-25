package mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controller.GenomeGraph;

// TODO: Auto-generated Javadoc
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
		
	/**
	 * Constructor to create.
	 *
	 * @param genomeGraph the genome graph
	 */
	public Mutations(GenomeGraph genomeGraph) {
		this.genomeGraph = genomeGraph;
		mutations = new HashMap<>();
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
				if (key1 != key2) {
					mutations.get(key1).put(key2, new ArrayList<>());
				}
			}
		}
	}
	
	/**
	 * Check mutations.
	 */
	private void checkMutations() {
		Set<String> keys = mutations.keySet();
		for (String base : keys) {
			for (String other : keys) {
				if (!base.equals(other)) {
					ComputeMutation.compute(genomeGraph.getGenomes().get(base), 
							genomeGraph.getGenomes().get(other), this);
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
	public HashMap<String,ArrayList<AbstractMutation>> getGenomeMutations(String genome) {
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

}
