package genomefeature;

import org.junit.Test;
import org.meanbean.test.BeanTester;

import genomefeature.GenomeSearchResult;

/**
 * The Class GSearchResultTest.
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
