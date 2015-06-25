package de.btu.openinfra.backend.rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.OpenInfraApplication;

@Path("/version")
@Produces({MediaType.TEXT_PLAIN})
public class OpenInfraVersion {
	
	@GET
	public String getVersion(@Context ServletContext context) {
		return OpenInfraApplication.getOpenInfraVersion(context);
	}

}
