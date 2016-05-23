package mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import controller.Controller;

/**
 * 
 * @author Jeffrey Helgers.
 * This class computes all the mutations in the given dataset. 
 */
public class AllMutations {

	private Controller dc;
	private HashMap<String, ArrayList<MutationWillBeDeleted>> mutations;
		
	/**
	 * Constructor to create.
	 * @param dc The given data container.
	 */
	public AllMutations(Controller dc) {
		this.dc = dc;
		mutations = new HashMap<>();
		setKeys(dc.getGenomes().keySet());
		checkMutations();
	}
	
	private void setKeys(Set<String> keys) {
		for (String key : keys) {
			mutations.put(key, new ArrayList<MutationWillBeDeleted>());
		}
	}
	
	private void checkMutations() {
		Set<String> keys = dc.getGenomes().keySet();
		for (String base : keys) {
			for (String other : keys) {
				if (!base.equals(other)) {
					ComputeMutation.compute(dc.getGenomes().get(base), 
							dc.getGenomes().get(other), this);
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
