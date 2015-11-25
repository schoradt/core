package de.btu.openinfra.plugins.solr.exception;

import javax.ws.rs.core.Response.Status;

import de.btu.openinfra.backend.exception.OpenInfraExceptionTypes;
import de.btu.openinfra.backend.exception.OpenInfraWebException;

public class OpenInfraSolrException extends OpenInfraWebException {

	private static final long serialVersionUID = 1L;

	public OpenInfraSolrException(OpenInfraExceptionTypes type) {
		super(Status.INTERNAL_SERVER_ERROR,
		        OpenInfraSolrException.class.getName(),
		        type);
	}



}
