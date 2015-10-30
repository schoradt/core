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

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.ValueListAssociationPojo;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.ValueListAssociationRbac;
import de.btu.openinfra.backend.db.rbac.ValueListRbac;
import de.btu.openinfra.backend.db.rbac.ValueListValueRbac;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/valuelists")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ValueListResource {

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getValueListsCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new ValueListRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	@GET
	public List<ValueListPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListRbac(
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

	@GET
	@Path("{valueListId}/associations")
	public List<ValueListAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						offset,
						size);
	}

	@GET
    @Path("{valueListId}/associations/count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getAssociationsCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId) {
        return new ValueListAssociationRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        valueListId);
    }

	@GET
	@Path("{valueListId}/associations/{associatedValueListId}")
	public List<ValueListAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@PathParam("associatedValueListId") UUID associatedValueListId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						associatedValueListId,
						offset,
						size);
	}

	@POST
	public Response createValueList(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId,
	        @PathParam("schema") String schema,
	        ValueListPojo pojo) {
	    // call the create or update method for the DAO and return the uuid
	    return OpenInfraResponseBuilder.postResponse(
	                new ValueListRbac(
	                        projectId,
	                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
	                        .createOrUpdate(OpenInfraHttpMethod.valueOf(
	                        		request.getMethod()),
	        						uriInfo,
	        						pojo,
	        						null,
	        						pojo.getMetaData()));
	}

	@GET
	@Path("{valueListId}")
	public ValueListPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId);
	}

	@DELETE
	@Path("{valueListId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId,
	        @PathParam("valueListId") UUID valueListId,
            @PathParam("schema") String schema) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new ValueListRbac(
	                    projectId,
	                    OpenInfraSchemas.valueOf(schema.toUpperCase()))
	                .delete(
	                		OpenInfraHttpMethod.valueOf(request.getMethod()),
    						uriInfo,
    						valueListId),
	                valueListId);
	}

	@GET
    @Path("/new")
    public ValueListPojo newValueList(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema) {
        return new ValueListRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
                    .newValueList(
                    		OpenInfraHttpMethod.valueOf(request.getMethod()),
    						uriInfo,
    						PtLocaleDao.forLanguageTag(language));
    }

	@PUT
    @Path("{valueListId}")
    public Response update(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("valueListId") UUID valueListId,
            ValueListPojo pojo) {
	    UUID uuid = new ValueListRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        pojo, valueListId, pojo.getMetaData());
        return OpenInfraResponseBuilder.postResponse(uuid);
    }

	@GET
	@Path("{valueListId}/valuelistvalues")
	public List<ValueListValuePojo> getValueListValues(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new ValueListValueRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						valueListId,
						sortOrder,
						orderBy,
						offset,
						size);
	}

	@POST
    @Path("{valueListId}/valuelistvalues")
	public Response createValueListValue(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("valueListId") UUID valueListId,
            @PathParam("schema") String schema,
            ValueListValuePojo pojo) {
        // call the create or update method for the DAO and return the uuid
        return OpenInfraResponseBuilder.postResponse(
                    new ValueListValueRbac(
                            projectId,
                            OpenInfraSchemas.valueOf(schema.toUpperCase()))
                            .createOrUpdate(
                            		OpenInfraHttpMethod.valueOf(
                            				request.getMethod()),
            						uriInfo,
            						pojo,
            						valueListId,
            						pojo.getBelongsToValueList(),
                                    pojo.getMetaData()));
	}

	@GET
	@Path("{valueListId}/valuelistvalues/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getValueListValuesCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("valueListId") UUID valueListId) {
		return new ValueListValueRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						valueListId);
	}

}
