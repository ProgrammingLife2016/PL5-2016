package mutation;

import java.util.ArrayList;

import genome.Genome;
import genome.Node;

public class Deletion extends Mutation {

	private ArrayList<Node> missingNodes;
	
	public Deletion(Genome reference, Genome other, ArrayList<Node> missingNodes) {
		super(reference, other);
		this.missingNodes = missingNodes;
	}

}
