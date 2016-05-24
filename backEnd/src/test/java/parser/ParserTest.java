package parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import junit.framework.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

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
		assertEquals(controller.getStrandNodes().get(1).getId(), 1);
	}

	/**
	 * Checks if the created node-file is correct.
	 */
	@Test
	public void testNodesFile() {
		new Parser("temp", "data/test1.gfa", "data/testPhylo.nwk");

		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("temp/nodes.csv"), "UTF8"));
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
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("temp/edges.csv"), "UTF8"));
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
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("temp/phylo.csv"), "UTF8"));
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
