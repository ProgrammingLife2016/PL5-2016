package com.pl.tagc.tagcwebapp;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Allows to see exceptions in console.
 */
@Provider
public class DebugExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		return Response.serverError().entity(exception.getMessage()).build();
	}
}