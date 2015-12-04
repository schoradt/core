package de.btu.openinfra.backend.rest.webapp;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.daos.webapp.WebappProjectDao;
import de.btu.openinfra.backend.db.daos.webapp.WebappSubjectDao;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSubjectPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class refers to subject specific web-application data.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/webapp/{webappId}/subjects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class WebappSubjectResource {

	/**
	 * Reads a data object related to a specific web-application and a specific
	 * subject (user).
	 *
	 * @param uriInfo
	 * @param request
	 * @param subjectId the subject (user) id
	 * @return a data object
	 */
	@GET
	@Path("/{subjectId}")
	public WebappSubjectPojo read(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			@PathParam("subjectId") UUID subjectId) {
		return new WebappSubjectDao().read(webappId, subjectId);
	}

	/**
	 * A PUT method which also includes POST. It creates a new or replaces an
	 * existing data object.
	 *
	 * @param uriInfo
	 * @param request
	 * @param subjectId the id of the related subject
	 * @param pojo the content add/replace
	 * @return the UUID of the added/replaced object
	 */
	@PUT
	@Path("/{subjectId}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			@PathParam("subjectId") UUID subjectId,
			WebappSubjectPojo pojo) {
		WebappSubjectPojo wsp =
				new WebappSubjectDao().read(webappId, subjectId);
		if(wsp == null) {
			pojo.setSubject(subjectId);
			pojo.setWebapp(webappId);
			return OpenInfraResponseBuilder.postResponse(
					new WebappSubjectDao().createOrUpdate(pojo, null));
		} else {
			pojo.setUuid(wsp.getUuid());
			return OpenInfraResponseBuilder.putResponse(
					new WebappSubjectDao()
					.createOrUpdate(pojo, pojo.getUuid()));
		}
	}

	/**
	 * Deletes an existing data object.
	 *
	 * @param uriInfo
	 * @param request
	 * @param subjectId the subject (user) id
	 * @return the UUID of the deleted object
	 */
	@DELETE
	@Path("/{subjectId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			@PathParam("subjectId") UUID subjectId) {
		WebappSubjectPojo wsp =
				new WebappSubjectDao().read(webappId, subjectId);
		if(wsp != null) {
			return OpenInfraResponseBuilder.deleteResponse(
					new WebappProjectDao().delete(wsp.getUuid()),
					wsp.getUuid());
		} else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
}
