package mutation;

import controller.GenomeGraph;
import parser.Parser;

public class test {

	public static void main(String[] args) {
		GenomeGraph graph = Parser.parse("data/TB10.gfa");
		Mutations mutations = new Mutations(graph);
		mutations.getMutations();
		System.out.println(mutations.getMutation().size());
	}

}
