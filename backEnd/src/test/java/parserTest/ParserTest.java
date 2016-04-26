package parserTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import parser.Parser;
import genome.Genome;

public class ParserTest {
	
	/**
	 * Reads the data and checks if the id's do match.
	 */
	@Test
	public void test() {
		Genome genome = Parser.parse("../data/TB10.gfa");
		assertEquals(genome.getNodes().get(0).getId(), 1);
		assertEquals(genome.getEdges().get(0).getStart(), 1);
		assertEquals(genome.getEdges().get(0).getEnd(), 2);
	}
	
	

}
