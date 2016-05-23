package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
		assertEquals(controller.getStrandNodes().get(1).getId(), 1);
	}

	/**
	 * Test that the default constructor fails.
	 * @throws Exception if fail.
     */
	@Test
	public void testEmptyParserConstructor() throws Exception {
		Exception exception = null;
		try {
			new Parser();
		}
		catch  (UnsupportedOperationException e) {
			exception = e;
		}
		assertNotNull(exception);
	}
}
