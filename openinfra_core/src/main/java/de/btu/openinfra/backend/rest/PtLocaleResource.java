package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.PtLocalePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.PtLocaleRbac;

/**
 * This class represents the PtLocal resource of the REST API.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/ptlocales")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
public class PtLocaleResource {

	@GET
	public List<PtLocalePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new PtLocaleRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
						offset,
						size);
	}

	@POST
    public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            PtLocalePojo pojo) {
        UUID id = new PtLocaleRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                        OpenInfraHttpMethod.valueOf(request.getMethod()),
                        uriInfo,
                        null,
                        pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new PtLocaleRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	@GET
	@Path("{ptLocaleId}")
	public PtLocalePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("ptLocaleId") UUID ptLocaleId) {
		return new PtLocaleRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						ptLocaleId);
	}

	@DELETE
    @Path("{ptLocaleId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("ptLocaleId") UUID ptLocaleId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new PtLocaleRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                ptLocaleId),
                ptLocaleId);
    }

}
