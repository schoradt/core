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

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.rbac.PasswordBlacklistPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.rbac.PasswordBlacklistRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class refers to a set of strings which are not allowed to use as
 * (black listed) passwords.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/passwordblacklist")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class PasswordBlacklistResource {

	/**
	 * Delivers a list of all black listed passwords. This resource is paging
	 * enabled.
	 * <br/>
	 * Currently, there exists no default maximum.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param sortOrder
	 * @param orderBy
	 * @param offset
	 * @param size
	 * @return a list of all black listed passwords
	 */
	@GET
	public List<PasswordBlacklistPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new PasswordBlacklistRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				sortOrder,
				orderBy,
				offset,
				size);
	}

	/**
	 * Creates a new string as black listed password.
	 *
	 * @param uriInfo
	 * @param request
	 * @param pojo
	 * @return the UUID of the new object
	 */
	@POST
	public Response create(
			@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
    		PasswordBlacklistPojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
				new PasswordBlacklistRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, null, pojo));
	}

	/**
	 * Delivers a specific black listed password.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param uuid the UUID of the requested password
	 * @return a specific black listed password
	 */
	@GET
	@Path("{id}")
	public PasswordBlacklistPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new PasswordBlacklistRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}

	/**
	 * Changes a specific black listed password.
	 *
	 * @param uriInfo
	 * @param request
	 * @param uuid the UUID of the black listed password which should be changed
	 * @param pojo the content to change
	 * @return the UUID of the changed password
	 */
	@PUT
	@Path("{id}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid,
			PasswordBlacklistPojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new PasswordBlacklistRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid, pojo));
	}

	/**
	 * Deletes a specific black listed password.
	 *
	 * @param uriInfo
	 * @param request
	 * @param uuid the UUID of the black listed password which should be deleted
	 * @return the UUID of the deleted object
	 */
	@DELETE
	@Path("{id}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid) {
		return OpenInfraResponseBuilder.deleteResponse(
				new PasswordBlacklistRbac().delete(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid), uuid);
	}

	/**
	 * Delivers the number of black listed passwords.
	 *
	 * @param uriInfo
	 * @param request
	 * @return the number of black listed passwords
	 */
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request) {
		return new PasswordBlacklistRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

}