package genomeTest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import genome.Edge;
import genome.Genome;
import genome.Node;
import genome.DataContainer;

/**
 * Created by Matthijs on 24-4-2016.
 */
public class DataContainerTest {

	private DataContainer data;
	
	/**
	 * Setting up the DataContainer.
	 */
    @Before
    public void setUp(){
    	data = new DataContainer();
    }
}