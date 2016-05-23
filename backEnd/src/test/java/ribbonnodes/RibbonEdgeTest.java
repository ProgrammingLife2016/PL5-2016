package ribbonnodes;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matthijs on 17-5-2016.
 * Test the Ribbon Edges.
 */
public class RibbonEdgeTest {

    private RibbonEdge edge; //the edge to test.

    /**
     * Set up the tests.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        edge = new RibbonEdge(0, 1);

    }

    /**
     * Test the incrementation of an edge.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testIncrementWeight() throws Exception {
        assertEquals(1, edge.getWeight());
        edge.incrementWeight();
        assertEquals(2, edge.getWeight());
    }
}