package de.btu.openinfra.backend.rest.rbac;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.rbac.ProjectRelatedRolePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.rbac.ProjectRelatedRoleRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

/**
 * This class is used to retrieve all available OpenInfRA related roles. Every
 * role must be manually defined with a related adaption of the RBAC
 * implementation. Thus, this class only provides GET methods. PUT, POST or
 * DELETE are not provided here!
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/projectrelatedrole")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ProjectRelatedRoleResource {

	@GET
	public List<ProjectRelatedRolePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ProjectRelatedRoleRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				sortOrder,
				orderBy,
				offset,
				size);
	}

	@GET
	@Path("{id}")
	public ProjectRelatedRolePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new ProjectRelatedRoleRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()),
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request) {
		return new ProjectRelatedRoleRbac().getCount(
				OpenInfraHttpMethod.valueOf(request.getMethod()), uriInfo);
	}

}
