package genomefeature;

import org.junit.Test;
import org.meanbean.test.BeanTester;

/**
 * The Class that tests GenomeSearchResult.
 */
public class GenomeSearchResultTest {

    /**
     * Tests the getters and setters.
     */
    @Test
    public void test() {
        BeanTester beanTester = new BeanTester();
        beanTester.testBean(GenomeSearchResult.class);
    }

}
