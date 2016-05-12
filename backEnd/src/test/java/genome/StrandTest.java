package genome;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class StrandTest {

	private Strand strand;
	
	/**
	 * Sets up a strand with whom we test.
	 */
    @Before
    public void setUp() {
    	String[] genomes = {"ref1", "ref2"};
    	strand = new Strand(1, "AA", genomes, "ref1", 0);
    }
    
    /**
     * Tests whether we can change the x coordinate.
     */
    @Test
    public void testGetXCoordinate() {
    	strand.setxCoordinate(10.0);
    	assertEquals(strand.getxCoordinate(), 10.0, 0.001);
    }

    /**
     * Tests whether we can change the x coordinate.
     */
    @Test
    public void testGetYCoordinate() {
    	strand.setyCoordinate(10.0);
    	assertEquals(strand.getyCoordinate(), 10.0, 0.001);
    }
    
    /**
     * Tests getting the id of the strand.
     */
    @Test
    public void testGetId() {
    	assertEquals(strand.getId(), 1);
    	strand.setId(2);
    	assertEquals(strand.getId(), 2);
    }
    
    /**
     * Tests getting the sequence of the strand.
     */
    @Test
    public void testGetSequence() {
    	assertEquals(strand.getSequence(), "AA");
    	strand.setSequence("AC");
    	assertEquals(strand.getSequence(), "AC");
    }
    
    /**
     * Tests getting the genomes of the strand.
     */
    @Test
    public void testGetGenomes() {
    	assertEquals(strand.getGenomes()[0], "ref1");
    	assertEquals(strand.getGenomes()[1], "ref2");
    	String[] temp = {"ref3", "ref4" };
    	strand.setGenomes(temp);
    	assertEquals(strand.getGenomes()[0], "ref3");
    	assertEquals(strand.getGenomes()[1], "ref4");
    }
    
    /**
     * Tests getting the reference genome of the strand.
     */
    @Test
    public void testGetReferenceGenome() {
    	assertEquals(strand.getReferenceGenome(), "ref1");
    	strand.setReferenceGenome("ref2");
    	assertEquals(strand.getReferenceGenome(), "ref2");
    }
    
    /**
     * Tests getting the reference coordinate of the strand.
     */
    @Test
    public void testGetReferenceCoordinate() {
    	assertEquals(strand.getReferenceCoordinate(), 0);
    	strand.setReferenceCoordinate(10);
    	assertEquals(strand.getReferenceCoordinate(), 10);
    }
    
    /**
     * Tests getting the weight of the strand.
     */
    @Test
    public void testGetWeight() {
    	assertEquals(strand.getWeight(), 2);
    	strand.setWeight(4);
    	assertEquals(strand.getWeight(), 4);
    }
    
    /**
     * Tests updating the x coordinate.
     */
    @Test
    public void testUpdateXCoordinate() {
    	String[] genomes = {"ref1", "ref2"};
    	strand = new Strand(1, "AA", genomes, "ref1", 0);
    	strand.updatexCoordinate(2);
    	assertEquals(strand.getxCoordinate(), 2, 0.001);
    }
    
}