package abstractDataStructure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class to test Edge.
 * Created by Matthijs on 13-5-2016.
 */
public class EdgeTest {

    private Edge edge; //testEdge

    /**
     * Set up the Edge.
     *
     * @throws Exception if fail.
     */
    @Before
    public void setUp() throws Exception {
        edge = new Edge(1, 2);
    }

    /**
     * Test set weight.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetWeight() throws Exception {
        assertEquals(edge.getWeight(), 1);
        edge.setWeight(4);
        assertEquals(edge.getWeight(), 4);
    }

    /**
     * Test get start.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetStart() throws Exception {
        assertEquals(1, edge.getStart());

    }

    /**
     * Test get end.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetEnd() throws Exception {
        assertEquals(2, edge.getEnd());
    }

    /**
     * Test get weight.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testGetWeight() throws Exception {
        assertEquals(1, edge.getWeight());
    }


    /**
     * Test the setStartID
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetStartId() throws Exception {
        assertEquals(1, edge.getStart());
        edge.setStartId(4);
        assertEquals(4, edge.getStart());
    }


    /**
     * Test the setStartID
     *
     * @throws Exception if fail.
     */
    @Test
    public void testSetEndId() throws Exception {
        assertEquals(2, edge.getEnd());
        edge.setEndId(4);
        assertEquals(4, edge.getEnd());
    }
}