package de.btu.openinfra.backend.rest;

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

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.MultiplicityPojo;
import de.btu.openinfra.backend.db.rbac.MultiplicityRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;

/**
 * This class represents and implements the Multiplicity resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/multiplicities")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MultiplicityResource {

	@GET
	public List<MultiplicityPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new MultiplicityRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						offset,
						size);
	}

	@GET
    @Path("count")
    @Produces({MediaType.TEXT_PLAIN})
    public long getMultiplicityCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema) {
        return new MultiplicityRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
                		OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo);
    }

	@GET
	@Path("{multiplicityId}")
	public MultiplicityPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("multiplicityId") UUID multiplicityId) {
		return new MultiplicityRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						multiplicityId);
	}

	@GET
    @Path("/new")
    public MultiplicityPojo newMultiplicity(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema) {
	    return new MultiplicityPojo();
    }

	@POST
	public Response create(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			MultiplicityPojo pojo) {
		UUID id = new MultiplicityRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						null,
						pojo, pojo.getMetaData());
		return OpenInfraResponseBuilder.postResponse(id);
	}

	@DELETE
	@Path("{multiplicityId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("multiplicityId") UUID multiplicityId) {
		return OpenInfraResponseBuilder.deleteResponse(
				new MultiplicityRbac(
						projectId,
						OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
								OpenInfraHttpMethod.valueOf(request.getMethod()), 
								uriInfo,
								multiplicityId),
				multiplicityId);
	}

	@PUT
	@Path("{multiplicityId}")
	public Response update(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("multiplicityId") UUID multiplicityId,
			MultiplicityPojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
				new MultiplicityRbac(
						projectId,
						OpenInfraSchemas.PROJECTS).createOrUpdate(
								OpenInfraHttpMethod.valueOf(request.getMethod()), 
								uriInfo,
								pojo,
						        multiplicityId, pojo.getMetaData()));
	}

}
