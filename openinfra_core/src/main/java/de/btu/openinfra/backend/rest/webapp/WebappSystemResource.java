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

import de.btu.openinfra.backend.db.daos.webapp.WebappSystemDao;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSystemPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * The same as by WebappResource we only provide GET and PUT methods. There
 * is only one entry per web-application allowed which should be registered
 * or deleted by the owner of the web-application.
 *
 * Registration and delete methods might be part of the system administration
 * in the future.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/webapp/{webappId}/system")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class WebappSystemResource {

	/**
	 * Delivers a web-application data object.
	 *
	 * @param uriInfo
	 * @param request
	 * @param webappId the id of the web-application
	 * @return a web-application data object
	 */
	@GET
	public WebappSystemPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId) {
		// There should be only one result in the list!
		return new WebappSystemDao().read(null, webappId, 0, 1).get(0);
	}

	/**
	 * Changes an existing web-application data object. It's not required to set
	 * the UUID of the POJO.
	 *
	 * @param uriInfo
	 * @param request
	 * @param webappSystemId the id of the web-application
	 * @param pojo the content to change
	 * @return the UUID of the changed data object
	 */
	@PUT
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappSystemId") UUID webappSystemId,
			WebappSystemPojo pojo) {
		pojo.setUuid(webappSystemId);
		return OpenInfraResponseBuilder.putResponse(
				new WebappSystemDao().createOrUpdate(pojo, webappSystemId));
	}

}
