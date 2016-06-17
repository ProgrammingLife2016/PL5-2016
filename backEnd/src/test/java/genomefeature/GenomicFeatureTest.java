package genomefeature;

import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class that tests GenomicFeature.
 */
public class GenomicFeatureTest {

    /**
     * Tests the getters and setters.
     */
    @Test
    public void test() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(GenomicFeature.class);
    }

}
