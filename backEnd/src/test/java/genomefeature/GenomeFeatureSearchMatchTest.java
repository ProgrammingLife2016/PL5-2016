package genomefeature;

import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class that tests the GenomeFeatureSearchMatch.
 */
public class GenomeFeatureSearchMatchTest {

    /**
     * Tests the getters and setters.
     */
    @Test
    public void test() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(GenomeFeatureSearchMatch.class);
    }

}
