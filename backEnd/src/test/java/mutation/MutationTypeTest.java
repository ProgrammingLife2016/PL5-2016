package mutation;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * 
 * @author Jeffrey Helgers.
 * Class that tests the mutation types.
 */
public class MutationTypeTest {

	/**
	 * Test the getBetween on all the mutation types.
	 */
	@Test
	public void testMutationTypes() {
		for (MutationType type : MutationType.values()) {
			if (type.equals(MutationType.INDEL) || type.equals(MutationType.SNP)) {
				assertTrue(type.getBetween());
			} else {
				assertFalse(type.getBetween());
			}
		}
	}

}
