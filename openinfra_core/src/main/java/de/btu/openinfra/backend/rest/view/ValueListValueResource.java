package de.btu.openinfra.backend.rest.view;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/valuelistvalues")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class ValueListValueResource {

	@GET
	@Path("{valueListValueId}")
	@Template(name="/views/detail/ValueListValues.jsp")
	public ValueListValuePojo getView(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListValueId") UUID valueListValueId) {
		return new de.btu.openinfra.backend.rest.ValueListValuesResource()
			.get(uriInfo, request, language, projectId, 
					schema, valueListValueId);
	}

    @GET
    @Path("/new")
    @Template(name="/views/detail/ValueListValues.jsp")
    public Response index() {
        return Response.ok().entity("ValueListValues").build();
    }
}
