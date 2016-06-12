package ribbonnodes;

import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;

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
        HashSet<String> genome = new HashSet<>(Arrays.asList("1"));
        RibbonNode node1 = new RibbonNode(0, genome);
        RibbonNode node2 = new RibbonNode(1, genome);
        edge = new RibbonEdge(node1, node2);

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

    /**
     * Test the color getter and setter.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testColor() throws Exception {
        assertEquals(Color.black, edge.getColor());
        edge.setColor(Color.red);
        assertEquals(Color.red, edge.getColor());
    }

    /**
     * Test the updateColor funtion.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testUpdateColor() throws Exception {
        edge.setColor(Color.red);
        edge.updateColor(Color.yellow);
        assertEquals(new Color(255, 127, 0), edge.getColor());
    }

    /**
     * Test if when a genome is added to an edge the weight and color are updated.
     *
     * @throws Exception if fail.
     */
    @Test
    public void testAddGenome() throws Exception {
        edge.setColor(Color.red);
        assertEquals(edge.getColor(), Color.red);
        assertEquals(edge.getWeight(), 1);
        edge.addGenomeToEdge(Color.yellow);
        assertEquals(new Color(255, 127, 0), edge.getColor());
        assertEquals(edge.getWeight(), 2);


    }
}