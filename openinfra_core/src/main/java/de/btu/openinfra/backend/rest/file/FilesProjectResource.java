package de.btu.openinfra.backend.rest.file;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.file.FilesProjectRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * A resource which is used to manages the relations between files and projects.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/files/{fileId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}/"
		+ "filesproject")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class FilesProjectResource {

	/**
	 * Delivers a list of relations between a file and projects.
	 *
	 * @param uriInfo
	 * @param request
	 * @param fileId the related file
	 * @return a list of relations
	 */
	@GET
	public List<FilesProjectPojo> read(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId) {
		return new FilesProjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, null, fileId, 0, Integer.MAX_VALUE);
	}

	/**
	 * Delivers a relation between a file an a project.
	 *
	 * @param uriInfo
	 * @param request
	 * @param filesProjectId the id of the relation
	 * @return a relation between a file and a project
	 */
	@GET
	@Path("{filesProjectId}")
	public FilesProjectPojo readSingle(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("filesProjectId") UUID filesProjectId) {
		return new FilesProjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, null, filesProjectId);
	}

	/**
	 * Creates a relation between a file and a project. PUT method isn't
	 * allowed. You can delete the relation and create a new one.
	 * <br/>
	 * { <br/>
	 * &nbsp;"uuid":null, <br/>
	 * &nbsp;"file":"8ee2e881-efad-4be2-8db9-2bed281f0360", <br/>
	 * &nbsp;"project":"fd27a347-4e33-4ed7-aebc-eeff6dbf1054" <br/>
	 * }
	 *
	 * @param uriInfo
	 * @param request
	 * @param fileId the file id
	 * @param pojo the content
	 * @return the UUID of the newly created project
	 */
	@POST
	public Response create(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId,
			FilesProjectPojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
				new FilesProjectRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, null, pojo));
	}

	/**
	 * Deletes an existing relation between a file and a project.
	 *
	 * @param uriInfo
	 * @param request
	 * @param filesProjectId the id of the relation
	 * @return the UUID of the deleted object
	 */
	@DELETE
	@Path("{filesProjectId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("filesProjectId") UUID filesProjectId) {
		boolean res =
				new FilesProjectRbac().delete(OpenInfraHttpMethod.valueOf(
						request.getMethod()), uriInfo, filesProjectId);
		return OpenInfraResponseBuilder.deleteResponse(res, filesProjectId);
	}


}
