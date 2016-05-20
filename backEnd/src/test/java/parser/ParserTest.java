package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import junit.framework.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Jeffrey Helgers.
 * 
 */
public class ParserTest {
	
	/**
	 * Reads the data and checks if the id's do match.
	 */
	@Test
	public void test() {
		controller.Controller controller = Parser.parse("data/TB10.gfa");
		assertEquals(controller.getstrandNodes().get(1).getId(), 1);
		assertEquals(controller.getEdges().get("1|2").getStart(), 1);
		assertEquals(controller.getEdges().get("1|2").getEnd(), 2);
	}

	/**
	 * Checks if the created node-file is correct.
	 */
	@Test
	public void testNodesFile() {
		new Parser("temp", "data/test1.gfa", "data/testPhylo.nwk");

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("temp/nodes.csv"));
			Assert.assertEquals("id,sequence,genomes,refGenome,refCoor", reader.readLine());
			Assert.assertEquals("1,AAAAAAAA,AA,AA,371", reader.readLine());
			Assert.assertEquals("2,A,AA,AA,371", reader.readLine());
			Assert.assertEquals("3,C,AA;BB,AA,371", reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Checks if the created edge-file is correct.
	 */
	@Test
	public void testEdgesFile() {
		new Parser("temp", "data/test1.gfa", "data/testPhylo.nwk");

		BufferedReader reader;
		try {
			//InputStream in = Parser.class.getClassLoader().getResourceAsStream("temp/edges.csv");
			reader = new BufferedReader(new FileReader("temp/edges.csv"));
			Assert.assertEquals("start,end", reader.readLine());
			Assert.assertEquals("1,2", reader.readLine());
			Assert.assertEquals("2,3", reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * Checks if the created phylo-file is correct.
	 */
	@Test
	public void testPhyloFile() {
		new Parser("temp", "data/test1.gfa", "data/testPhylo.nwk");

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("temp/phylo.csv"));
			Assert.assertEquals("parent,child,dist,pc", reader.readLine());
			Assert.assertEquals("0,1,0,parent", reader.readLine());
			Assert.assertEquals("1,AA,0,child", reader.readLine());
			Assert.assertEquals("1,2,0,parent", reader.readLine());
			Assert.assertEquals("2,3,0,parent", reader.readLine());
			Assert.assertEquals("3,BB,0,child", reader.readLine());
			Assert.assertEquals("3,CC,0,child", reader.readLine());
			Assert.assertEquals("2,DD,0,child", reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
