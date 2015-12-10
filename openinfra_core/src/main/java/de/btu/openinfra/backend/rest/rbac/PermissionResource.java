package de.btu.openinfra.backend.rest.rbac;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
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

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.rbac.PermissionPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraRealm;
import de.btu.openinfra.backend.db.rbac.rbac.PermissionRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This resource class can be used to manage permission strings which are
 * directly processed by Apache Shiro. We distinguish between default and
 * project related permissions. For more information please also consider
 * the following classes:
 *
 * @see OpenInfraRealm
 * @see OpenInfraRbac
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/permissions")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PermissionResource {

	/**
	 * Delivers a list of all available permissions. This resource is paging
	 * enabled.
	 *
	 * @param uriInfo
	 * @param request
	 * @param offset
	 * @param size
	 * @return a list of all available permissions
	 */
	@GET
	public List<PermissionPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new PermissionRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				null,
				offset,
				size);
	}

	/**
	 * This method creates a new permission.
	 *
	 * @param uriInfo
	 * @param request
	 * @param pojo the content
	 * @return the UUID of the newly created object
	 */
	@POST
	public Response create(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		PermissionPojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
				new PermissionRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, null, pojo));
	}

	/**
	 * This resource changes an existing permission.
	 *
	 * @param uriInfo
	 * @param request
	 * @param uuid the UUID of the permission which should be changed
	 * @param pojo the content to change
	 * @return the UUID of the changed object
	 */
	@PUT
	@Path("{id}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid,
			PermissionPojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new PermissionRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid, pojo));
	}

	/**
	 * This resource deletes an existing permission.
	 *
	 * @param uriInfo
	 * @param request
	 * @param uuid the UUID of the object which should be deleted
	 * @return the UUID of the deleted object
	 */
	@DELETE
	@Path("{id}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid) {
		return OpenInfraResponseBuilder.deleteResponse(
				new PermissionRbac().delete(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid), uuid);
	}

	/**
	 * This resource retrieves a single permission.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param uuid the UUID of the requested object
	 * @return a single permission
	 */
	@GET
	@Path("{id}")
	public PermissionPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new PermissionRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}

	/**
	 * This resource retrieves the number of all available permissions.
	 *
	 * @param uriInfo
	 * @param request
	 * @return the number of all available permissions
	 */
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request) {
		return new PermissionRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

}