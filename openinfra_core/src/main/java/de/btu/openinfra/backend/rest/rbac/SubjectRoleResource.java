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
import de.btu.openinfra.backend.db.pojos.rbac.SubjectRolePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.rbac.SubjectRoleRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This resource class can be used to manage the relation between subjects and
 * their roles.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/subjectroles")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectRoleResource {

	/**
	 * This method is used to retrieve all available relations. This method is
	 * paging enabled.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param offset
	 * @param size
	 * @return all available relations
	 */
	@GET
	public List<SubjectRolePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new SubjectRoleRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				null,
				offset,
				size);
	}

	/**
	 * This method retrieves a specific relation between a subject and a role.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param uuid the UUID of the requested object
	 * @return a specific relation between a subject and a role
	 */
	@GET
	@Path("{id}")
	public SubjectRolePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new SubjectRoleRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}

	/**
	 * This method creates a new relation.
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
    		SubjectRolePojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
				new SubjectRoleRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, null, pojo));
	}

	/**
	 * This method changes an existing relation between a subject and a role.
	 *
	 * @param uriInfo
	 * @param request
	 * @param uuid the UUID of the object which should be changed
	 * @param pojo the content
	 * @return the UUID of the changed object
	 */
	@PUT
	@Path("{id}")
	public Response put(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid,
			SubjectRolePojo pojo) {
		return OpenInfraResponseBuilder.putResponse(
				new SubjectRoleRbac().createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid, pojo));
	}

	/**
	 * This method deletes an existing relation.
	 *
	 * @param uriInfo
	 * @param request
	 * @param uuid the UUID of the relation
	 * @return the UUID of the deleted relation
	 */
	@DELETE
	@Path("{id}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("id") UUID uuid) {
		return OpenInfraResponseBuilder.deleteResponse(
				new SubjectRoleRbac().delete(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo, uuid), uuid);
	}

	/**
	 * This method retrieves the number of all available relations.
	 *
	 * @param uriInfo
	 * @param request
	 * @return the number of all available relations
	 */
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request) {
		return new SubjectRoleRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

}