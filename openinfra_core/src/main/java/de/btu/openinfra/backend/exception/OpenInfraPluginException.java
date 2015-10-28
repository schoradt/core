package de.btu.openinfra.backend.exception;

import javax.ws.rs.core.Response.Status;

public class OpenInfraPluginException extends OpenInfraWebException {

	private static final long serialVersionUID = 1L;

	public OpenInfraPluginException(OpenInfraExceptionTypes type) {
		super(Status.INTERNAL_SERVER_ERROR,
		        OpenInfraPluginException.class.getName(),
				type);
	}



}
