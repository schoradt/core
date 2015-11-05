package de.btu.openinfra.backend.rest.file;

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

import de.btu.openinfra.backend.db.pojos.file.SupportedMimeTypePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.file.SupportedMimeTypeRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/v1/files/mimetypes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class SupportetMimeTypesResource {

	@GET
    @Path("count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getMimeTypesCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request) {
		return new SupportedMimeTypeRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

	@GET
	public List<SupportedMimeTypePojo> get(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new SupportedMimeTypeRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo, null, offset, size);
	}

	@POST
	public Response post(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		SupportedMimeTypePojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
				new SupportedMimeTypeRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, null, pojo));
	}

	@PUT
	@Path("{mimeTypeId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}")
	public Response put(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		@PathParam("mimeTypeId") UUID mimeTypeId,
    		SupportedMimeTypePojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new SupportedMimeTypeRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, mimeTypeId, pojo));
	}

	@DELETE
	@Path("{mimeTypeId:([0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12})}")
	public Response delete(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		@PathParam("mimeTypeId") UUID mimeTypeId) {
		return OpenInfraResponseBuilder.deleteResponse(
				new SupportedMimeTypeRbac().delete(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, mimeTypeId), mimeTypeId);
	}

}
