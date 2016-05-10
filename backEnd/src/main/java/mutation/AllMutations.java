package mutation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import genome.DataContainer;

public class AllMutations {

	private DataContainer dc;
	private HashMap<String, ArrayList<Mutation>> mutations;
		
	public AllMutations(DataContainer dc) {
		this.dc = dc;
		mutations = new HashMap<>();
		setKeys(dc.getGenomes().keySet());
		checkMutations();
	}
	
	private void setKeys(Set<String> keys) {
		for (String key : keys) {
			mutations.put(key, new ArrayList<Mutation>());
		}
	}
	
	private void checkMutations() {
		Set<String> keys = dc.getGenomes().keySet();
		for (String base : keys) {
			for (String other : keys) {
				if (!base.equals(other)) {
					ComputeMutation cm = new ComputeMutation(dc.getGenomes().get(base), 
							dc.getGenomes().get(other), this);
					cm.allMutations();
				}
			}
		}
	}
	
	public void addMutation(String key, Mutation mutation) {
		mutations.get(key).add(mutation);
	}
	
	public HashMap<String, ArrayList<Mutation>> getMutations() {
		return mutations;
	}
	
	public ArrayList<Mutation> getGenomeMutations(String genome) {
		return mutations.get(genome);
	}

}
