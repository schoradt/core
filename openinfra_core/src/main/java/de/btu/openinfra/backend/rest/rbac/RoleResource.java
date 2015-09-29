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

import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.rbac.RoleRbac;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_RBAC + "/roles")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class RoleResource {
	
	@GET
	public List<RolePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new RoleRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()), 
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				offset,
				size);
	}
	
	@GET
	@Path("{id}")
	public RolePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("id") UUID uuid) {
		return new RoleRbac().read(
				OpenInfraHttpMethod.valueOf(request.getMethod()), 
				uriInfo,
				PtLocaleDao.forLanguageTag(language),
				uuid);
	}
}