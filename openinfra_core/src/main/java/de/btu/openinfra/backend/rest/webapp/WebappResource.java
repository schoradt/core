package de.btu.openinfra.backend.rest.webapp;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.daos.webapp.WebappDao;
import de.btu.openinfra.backend.db.pojos.webapp.WebappPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * The web-application schema is used to provide a persistent storage for
 * applications (mostly web-applications) which consume the OpenInfRA REST
 * API. The current resource only provides a READ and a PUT method.
 * <br/>
 * POST isn't provided since the web-application owner is obliged to register
 * the application by the OpenInfRA owner.
 * <br/>
 * DELETE isn't provided since the OpenInfRA owner is obliged to delete
 * unnecessary web-application registrations.
 * <br/>
 * There is no GET method which provides a list of all available
 * web-applications. Thus, every web-application must be aware of it's own id.
 * and it becomes more difficult (but it's not impossible) to catch the id of
 * a different web-application.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/webapp")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class WebappResource {

	/**
	 * Reads a registered web-application from database. The id should be known
	 * by the owner of the web-application.
	 *
	 * @param uriInfo
	 * @param request
	 * @param id UUID known by the web-application owner.
	 * @return A web-application POJO of the requested web-application.
	 */
	@GET
	@Path("/{id}")
	public WebappPojo read(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID id) {
		return new WebappDao().read(null, id);
	}

	/**
	 * Changes an already registered web-application. It's not required to set
	 * the UUID of the POJO.
	 *
	 * @param uriInfo
	 * @param request
	 * @param id The id of the web-application data which should be changed.
	 * @param pojo The content to change.
	 * @return A response if the resource has changed.
	 */
	@PUT
	@Path("/{id}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID id,
			WebappPojo pojo) {
		pojo.setUuid(id);
		return OpenInfraResponseBuilder.postResponse(
				new WebappDao().createOrUpdate(pojo, id));
	}


}
