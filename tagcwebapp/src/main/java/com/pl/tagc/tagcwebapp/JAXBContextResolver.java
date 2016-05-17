package com.pl.tagc.tagcwebapp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import org.eclipse.persistence.jaxb.JAXBContextProperties;


/**
 * The Class JAXBContextResolver.
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	/** The context. */
	private JAXBContext context;
	private Class[] types = { NodeListObject.class, RestApi.class, 
			DimensionsObject.class};

	/**
	 * Instantiates a new JAXB context resolver.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public JAXBContextResolver() throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>(2);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream in = classLoader.getResourceAsStream(
				"bindings.json");
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, in);
		properties.put("eclipselink.media-type", "application/json");
		this.context = JAXBContext.newInstance(types, properties);
	}

	/**
	 * 
	 *
	 * @param objectType
	 *            the object type
	 * @return the JAXB context
	 * @see javax.ws.rs.ext.ContextResolver#getContext(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public JAXBContext getContext(Class<?> objectType) {
		for (Class type : types) {
			if (type == objectType) {
				return context;
			}
		}
		return null;
	}
}