package com.pl.tagc.tagcwebapp;

import genome.Strand;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.json.JSONJAXBContext;

/**
 * The Class JAXBContextResolver.
 */
@Provider
public class JAXBContextResolver implements ContextResolver<JAXBContext> {

	/** The context. */
	private JAXBContext context;
	private Class[] types = { Strand.class, NodeListObject.class, RestApi.class };

	/**
	 * Instantiates a new JAXB context resolver.
	 *
	 * @throws Exception the exception
	 */
	public JAXBContextResolver() throws Exception {
		this.context = new JSONJAXBContext(JSONConfiguration.natural().build(), types);
	}

	/**
	 *  
	 *
	 * @param objectType the object type
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