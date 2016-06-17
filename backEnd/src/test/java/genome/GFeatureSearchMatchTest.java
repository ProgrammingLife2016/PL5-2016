package genome;

import org.junit.Test;
import org.meanbean.test.BeanTester;

import genomefeature.GenomeFeatureSearchMatch;

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
        beanTester.testBean(GenomeFeatureSearchMatch.class);
    }

}
