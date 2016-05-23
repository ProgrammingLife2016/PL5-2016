package parser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


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
		controller.Controller controller = Parser.parse("data/TB10.gfa");
		assertEquals(controller.getstrandNodes().get(1).getId(), 1);

	}
}
