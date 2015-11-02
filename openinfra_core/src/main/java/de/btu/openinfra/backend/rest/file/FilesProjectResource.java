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

@Path("/v1/files/{fileId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}/"
		+ "filesproject")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class FilesProjectResource {

	@GET
	public List<FilesProjectPojo> read(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("fileId") UUID fileId) {
		return new FilesProjectRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, null, fileId, 0, Integer.MAX_VALUE);
	}

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
