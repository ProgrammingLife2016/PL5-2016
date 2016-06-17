package com.pl.tagc.tagcwebapp;

import static org.junit.Assert.assertEquals;
import genome.GSearchResult;
import genome.GraphSearcher.SearchType;
import java.util.ArrayList;
import java.util.Arrays;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import phylogenetictree.PhylogeneticNode;
import phylogenetictree.PhylogeneticTree;
import ribbonnodes.RibbonNode;
import controller.Controller;


/**
 * Allows to see exceptions in console.
 */
@Provider
class DebugExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		return Response.serverError().entity(exception.getMessage()).build();
	}
}

/**
 * The Class RestApiTest.
 */
public class RestApiTest extends JerseyTest {

	/** The controller. */
	private Controller controller = Mockito.mock(Controller.class);

	/** The tree. */
	private PhylogeneticTree tree = Mockito.mock(PhylogeneticTree.class);

	/** The node. */
	private PhylogeneticNode pNode = Mockito.mock(PhylogeneticNode.class);

	/** The node. */
	private RibbonNode rNode = Mockito.mock(RibbonNode.class);

	/** The node. */
	private GSearchResult searchResult = Mockito.mock(GSearchResult.class);

	/**
	 * Sets the up.
	 *
	 * @throws Exception             the exception
	 * @see org.glassfish.jersey.test.JerseyTest#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		Mockito.when(controller.loadPhylogeneticTree(Mockito.anyInt())).thenReturn(tree);
		Mockito.when(tree.getRoot()).thenReturn(pNode);
		Mockito.when(
				controller.getRibbonNodes(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(),
						Mockito.anyBoolean())).thenReturn(
				new ArrayList<RibbonNode>(Arrays.asList(rNode)));
		Mockito.when(controller.setActiveGenomes((Mockito.anyListOf(String.class)))).thenReturn(
				new ArrayList<String>());
		Mockito.when(controller.search(Mockito.anyString(), Mockito.any(SearchType.class)))
				.thenReturn(searchResult);
		BackEndAdapter.createInstance(controller);
		super.setUp();
	}

	/**
	 * Configure.
	 *
	 * @return the resource config
	 * @see org.glassfish.jersey.test.JerseyTest#configure()
	 */
	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		ResourceConfig rc = new ResourceConfig(RestApi.class);
		rc.register(DebugExceptionMapper.class);
		forceSet(TestProperties.CONTAINER_PORT, "0");
		return rc;
	}
	

	/**
	 * Tests that the getphylogenetictree REST endpoint returns the marshalled
	 * mocked tree.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetPhylogeneticTree() throws Exception {
		final String response = target().path("api/getphylogenetictree").request()
				.get(String.class);
		assertEquals("{\"name\":\"root\",\"children\":[]}", response);
	}

	/**
	 * Tests that the getnodes REST endpoint returns the marshalled mocked
	 * ribbonnode list.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetNodes() throws Exception {
		final String response = target().path("api/getnodes").request().get(String.class);
		assertEquals("{\"cList\":[{\"annotations\":[],\"edges\":[],\"genomes\":[],\"id\":0,\""
				+ "mutations\":[],\"visible\":false,\"x\":0,\"y\":0}]}", response);
	}

	/**
	 * Tests that the setactivegenomes REST endpoint returns the marshalled
	 * empty list.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSetActiveGenomes() throws Exception {
		Form form = new Form();
		form.param("x", "foo");
		form.param("y", "bar");
		Entity<Form> e = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE);
		final String response = target().path("api/setactivegenomes").request()
				.post(e, String.class);
		assertEquals("{\"list\":[]}", response);
	}

	/**
	 * Tests that the search REST endpoint returns the marshalled search result object.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testSearch() throws Exception {
		final String response = target().path("api/search")
				.queryParam("searchType", "GenomicFeatureSearch").request().get(String.class);
		assertEquals("{\"gFeatureSearchMatches\":[]}", response);
	}

}
