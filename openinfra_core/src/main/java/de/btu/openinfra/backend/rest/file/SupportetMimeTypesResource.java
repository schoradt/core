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

/**
 * This class contains methods to view/manage mime types supported by the file
 * service.
 * <br/>
 * Only configured mime types are accepted for upload by the service.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path("/v1/files/mimetypes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class SupportetMimeTypesResource {

	/**
	 * Delivers the number of configured mime types.
	 *
	 * @param uriInfo
	 * @param request
	 * @return a number of configured mime types
	 */
	@GET
    @Path("count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getMimeTypesCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request) {
		return new SupportedMimeTypeRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

	/**
	 * Delivers a list of configured mime types. This resource is paging
	 * enabled.
	 *
	 * @param uriInfo
	 * @param request
	 * @param offset The offset where to start.
	 * @param size The max. number of result elements.
	 * @return A list of configured mime types.
	 */
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

	/**
	 * Configures a new mime type. The API allows both explicit and top level
	 * wild card naming.
	 * <ul>
	 * 	<li>Explicit naming e.g. 'application/pdf'</li>
	 * 	<li>Top level wild card naming e.g. 'image/*'</li>
	 * </ul>
	 * The term 'top level wild card naming' is used to describe that the
	 * slash is directly followed by the asterics '*'. Wild card naming such as
	 * 'application/vnd.*' is unsupported.
	 *
	 * @param uriInfo
	 * @param request
	 * @param pojo the new mime type which should be registered
	 * @return the UUID of the newly registered mime type
	 */
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

	/**
	 * Changes an existing mime type. Please consider POST method for supported
	 * mime type notations.
	 *
	 * @param uriInfo
	 * @param request
	 * @param mimeTypeId the id of the mime type
	 * @param pojo the changed mime type
	 * @return the UUID of the changed mime type
	 */
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

	/**
	 * Deletes a configured mime type. It'll not delete already uploaded files.
	 * Present files with unconfigured mime type are still accessible. The
	 * deletion of mime types only restricts the upload behavior.
	 *
	 * @param uriInfo
	 * @param request
	 * @param mimeTypeId the mime type which should be deleted
	 * @return the UUID of the deleted mime type
	 */
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
