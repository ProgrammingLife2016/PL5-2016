package mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controller.Controller;
import controller.GenomeGraph;

/**
 * 
 * @author Jeffrey Helgers.
 * This class computes all the mutations in the given dataset. 
 */
public class AllMutations {

	private GenomeGraph genomeGraph;
	private HashMap<String, ArrayList<MutationWillBeDeleted>> mutations;
		
	/**
	 * Constructor to create.
	 * @param dc The given data container.
	 */
	public AllMutations(GenomeGraph genomeGraph) {
		this.genomeGraph = genomeGraph;
		mutations = new HashMap<>();
		setKeys(genomeGraph.getGenomes().keySet());
		checkMutations();
	}
	
	private void setKeys(Set<String> keys) {
		for (String key : keys) {
			mutations.put(key, new ArrayList<MutationWillBeDeleted>());
		}
	}
	
	private void checkMutations() {
		Set<String> keys = genomeGraph.getGenomes().keySet();
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
	 * Add a mutation.
	 * @param key The key from the reference genome.
	 * @param mutation The mutation.
	 */
	public void addMutation(String key, MutationWillBeDeleted mutation) {
		mutations.get(key).add(mutation);
	}
	
	/**
	 * Get the mutations with a genome as compared to.
	 * @param genome The compared genome.
	 * @return The mutations.
	 */
	public ArrayList<MutationWillBeDeleted> getGenomeMutations(String genome) {
		return mutations.get(genome);
	}

}
