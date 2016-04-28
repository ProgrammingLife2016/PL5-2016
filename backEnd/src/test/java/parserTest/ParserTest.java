package parserTest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import parser.Parser;
import genome.DataContainer;

public class ParserTest {
	
	/**
	 * Reads the data and checks if the id's do match.
	 */
	@Test
	public void test() {
		DataContainer dataContainer = Parser.parse("../data/TB10.gfa");
		assertEquals(dataContainer.getNodes().get(0).getId(), 1);
		assertEquals(dataContainer.getEdges().get(0).getStart(), 1);
		assertEquals(dataContainer.getEdges().get(0).getEnd(), 2);
	}
	
	

}
