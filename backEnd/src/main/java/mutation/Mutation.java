package mutation;

import genome.Genome;

public abstract class Mutation {

	private Genome reference;
	private Genome other;
	
	public Mutation(Genome reference, Genome other) {
		this.reference = reference;
	}
	
	public Genome getReference() {
		return reference;
	}
}
