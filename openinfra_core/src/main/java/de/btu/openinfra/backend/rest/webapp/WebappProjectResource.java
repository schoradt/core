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

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.project.ProjectDao;
import de.btu.openinfra.backend.db.daos.webapp.WebappDao;
import de.btu.openinfra.backend.db.daos.webapp.WebappProjectDao;
import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class refers to project specific web-application data.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/webapp/{webappId}/projects")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class WebappProjectResource {

	/**
	 * Reads a data object related to a specific web-application and a specific
	 * project.
	 *
	 * @param uriInfo
	 * @param request
	 * @param webappId the web-application id
	 * @param projectId the project id
	 * @return a data object
	 */
	@GET
	@Path("/{projectId}")
	public WebappProjectPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			@PathParam("projectId") UUID projectId) {
		return new WebappProjectDao().read(webappId, projectId);
	}

	/**
	 * A PUT method which also includes POST. It creates a new or replaces an
	 * existing data object. It checks if the web-application is registered and
	 * if the project really exists.
	 *
	 * @param uriInfo
	 * @param request
	 * @param webappId the web-application id
	 * @param projectId the project id
	 * @param pojo the content create/change
	 * @return the UUID of the created/changed object
	 */
	@PUT
	@Path("/{projectId}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			@PathParam("projectId") UUID projectId,
			WebappProjectPojo pojo) {
		WebappProjectPojo wpp =
				new WebappProjectDao().read(webappId, projectId);
		if(wpp == null) {
			// Check if the requested web-application and the requested project
			// really exists.
			if(new WebappDao().read(null, webappId) != null &&
					new ProjectDao(
							projectId, OpenInfraSchemas.PROJECTS).read(
									null, projectId) != null) {
				pojo.setProject(projectId);
				pojo.setWebapp(webappId);
				return OpenInfraResponseBuilder.postResponse(
						new WebappProjectDao().createOrUpdate(pojo, null));
			} else {
				throw new WebApplicationException(Status.NOT_FOUND);
			}
		} else {
			wpp.setData(pojo.getData());
			return OpenInfraResponseBuilder.putResponse(
					new WebappProjectDao()
					.createOrUpdate(wpp, wpp.getUuid()));
		}
	}

	/**
	 * Deletes an existing data object.
	 *
	 * @param uriInfo
	 * @param request
	 * @param webappId the web-application id
	 * @param projectId the project id
	 * @return the UUID of the deleted object
	 */
	@DELETE
	@Path("/{projectId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("webappId") UUID webappId,
			@PathParam("projectId") UUID projectId) {
		WebappProjectPojo wpp =
				new WebappProjectDao().read(webappId, projectId);
		if(wpp != null) {
			return OpenInfraResponseBuilder.deleteResponse(
					new WebappProjectDao().delete(wpp.getUuid()),
					wpp.getUuid());
		} else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

}
