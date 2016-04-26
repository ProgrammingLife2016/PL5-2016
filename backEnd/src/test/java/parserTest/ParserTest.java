package parserTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import parser.Parser;
import genome.Genome;

public class ParserTest {
	
	@Test
	public void test() {
		Genome genome = Parser.parse("../data/TB10.gfa");
		assertEquals(genome.getNodes().get(0).getId(), 1);
		assertEquals(genome.getEdges().get(0).getStart(), 1);
		assertEquals(genome.getEdges().get(0).getEnd(), 2);
	}
	
	

}
