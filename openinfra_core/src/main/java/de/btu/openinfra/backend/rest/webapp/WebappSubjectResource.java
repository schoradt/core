package de.btu.openinfra.backend.rest.webapp;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.daos.webapp.WebappSubjectDao;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSubjectPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/v1/webapp/{webappId}/subjects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class WebappSubjectResource {

	@GET
	public List<WebappSubjectPojo> read(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size,
			@PathParam("webappId") UUID webappId) {
		return new WebappSubjectDao().read(null, webappId, offset, size);
	}

	@GET
	@Path("/{webappSubjectId}")
	public WebappSubjectPojo read(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappSubjectId") UUID webappSubjectId) {
		return new WebappSubjectDao().read(null, webappSubjectId);
	}

	@PUT
	@Path("/{webappSubjectId}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappSubjectId") UUID webappSubjectId,
			WebappSubjectPojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new WebappSubjectDao().createOrUpdate(pojo, webappSubjectId));
	}

	@POST
	public Response post(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			WebappSubjectPojo pojo) {
		// set the web-application id in order to avoid wrong values
		pojo.setWebapp(webappId);
		return OpenInfraResponseBuilder.postResponse(
				new WebappSubjectDao().createOrUpdate(pojo, null));
	}

	@DELETE
	@Path("/{webappSubjectId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappSubjectId") UUID webappSubjectId) {
		return OpenInfraResponseBuilder.deleteResponse(
				new WebappSubjectDao().delete(webappSubjectId),
				webappSubjectId);
	}
}
