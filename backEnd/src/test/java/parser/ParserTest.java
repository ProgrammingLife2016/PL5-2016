package parser;

import org.junit.Test;

import genome.GenomeGraph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


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
		GenomeGraph genomeGraph = Parser.parse("data/TB10.gfa");
		assertEquals(genomeGraph.getStrandNodes().get(1).getId(), 1);
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
			assertEquals("id,sequence,genomes,refGenome,refCoor", reader.readLine());
			assertEquals("1,AAAAAAAA,AA,AA,371", reader.readLine());
			assertEquals("2,A,AA,AA,371", reader.readLine());
			assertEquals("3,C,AA;BB,AA,371", reader.readLine());
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
			assertEquals("start,end", reader.readLine());
			assertEquals("1,2", reader.readLine());
			assertEquals("2,3", reader.readLine());
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
		//new Parser("temp", "data/test1.gfa", "data/testPhylo.nwk");
		new Parser("temp", "data/test1.gfa", "data/testPhylo.nwk");

		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							new FileInputStream("temp/phylo.csv"), "UTF8"));
			assertEquals("parent,child,dist,pc", reader.readLine());
			assertEquals("0,1,0,parent", reader.readLine());
			assertEquals("1,AA,1,child", reader.readLine());
			assertEquals("1,2,5,parent", reader.readLine());
			assertEquals("2,3,2,parent", reader.readLine());
			assertEquals("3,BB,2,child", reader.readLine());
			assertEquals("3,CC,3,child", reader.readLine());
			assertEquals("2,DD,0,child", reader.readLine());
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
}
