package mutation;

import genome.Genome;
import genome.Node;

public class Insertion extends Mutation {

	private Node start;
	private Node end;
	
	public Insertion(Genome reference, Genome other, Node start, Node end) {
		super(reference, other);
		this.start = start;
		this.end = end;
	}

}
