package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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
import de.btu.openinfra.backend.db.pojos.ValueListValueAssociationPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.ValueListValueAssociationRbac;
import de.btu.openinfra.backend.db.rbac.ValueListValueRbac;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/valuelistvalues")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ValueListValuesResource {

	@GET
	@Path("{valueListValueId}")
	public ValueListValuePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListValueId") UUID valueListValueId) {
		return new ValueListValueRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListValueId);
	}

	@GET
	@Path("{valueListValueId}/associations")
	public List<ValueListValueAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListValueId") UUID valueListValueId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListValueAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListValueId,
						offset,
						size);
	}

	@GET
    @Path("{valueListValueId}/associations/count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getAssociationsCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListValueId") UUID valueListValueId) {
        return new ValueListValueAssociationRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        valueListValueId);
    }

	@GET
	@Path("{valueListValueId}/associations/{associatedValueListValueId}")
	public List<ValueListValueAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListValueId") UUID valueListValueId,
			@PathParam("associatedValueListValueId")
				UUID associatedValueListValueId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListValueAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListValueId,
						associatedValueListValueId,
						offset,
						size);
	}

	@PUT
    @Path("{valueListValueId}")
    public Response update(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListValueId") UUID valueListValueId,
            ValueListValuePojo pojo) {
		UUID uuid = new ValueListValueRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        pojo, valueListValueId, pojo.getMetaData());
        return OpenInfraResponseBuilder.postResponse(uuid);
    }

	@DELETE
    @Path("{valueListValueId}")
    public Response delete(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("valueListValueId") UUID valueListValueId,
            @PathParam("schema") String schema) {
        return OpenInfraResponseBuilder.deleteResponse(
                new ValueListValueRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
                    .delete(OpenInfraHttpMethod.valueOf(request.getMethod()),
    						uriInfo,
    						valueListValueId),
                    valueListValueId);
    }
}
