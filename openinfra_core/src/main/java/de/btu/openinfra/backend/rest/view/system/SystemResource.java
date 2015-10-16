package de.btu.openinfra.backend.rest.view.system;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.shiro.SecurityUtils;
import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_SYSTEM)
@Produces(MediaType.TEXT_HTML +
        OpenInfraResponseBuilder.UTF8_CHARSET +
        OpenInfraResponseBuilder.HTML_PRIORITY)
public class SystemResource {

	@GET
	@Template(name="/views/System.jsp")
	public Response getView(
			@Context UriInfo uri,
			@Context HttpHeaders headers) {
		if(SecurityUtils.getSubject().isPermitted("/system:r")) {
			return Response.ok("system resource").build();
		} else {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		
	}

}
