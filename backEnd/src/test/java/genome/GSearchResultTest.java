package genome;
import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class GSearchResultTest.
 */
public class GSearchResultTest {

	/**
	 * Tests the getters and setters.
	 */
	@Test
	public void test() {
		BeanTester beanTester = new BeanTester();
		beanTester.testBean(GSearchResult.class);
	}

}
