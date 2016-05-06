package parserTest;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Test;

import parser.Parser;
import genome.DataContainer;

/**
 *
 * @author Jeffrey Helgers.
 * 
 */
public class ParserTest {
	
	/**
	 * Reads the data and checks if the id's do match.
	 */
	@Test
	public void test() {
		DataContainer dataContainer = Parser.parse("../data/TB10.gfa");
		assertEquals(dataContainer.getNodes().get(1).getId(), 1);
		assertEquals(dataContainer.getEdges().get("1|2").getStart(), 1);
		assertEquals(dataContainer.getEdges().get("1|2").getEnd(), 2);
	}
}
