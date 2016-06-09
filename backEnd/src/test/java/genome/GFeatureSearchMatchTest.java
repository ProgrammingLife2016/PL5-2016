package genome;
import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class GFeatureSearchMatchTest.
 */
public class GFeatureSearchMatchTest {

	/**
	 * Tests the getters and setters.
	 */
	@Test
	public void test() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(GFeatureSearchMatch.class);
	}

}
