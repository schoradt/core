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

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.pojos.AttributeTypeAssociationPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToAttributeTypePojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypePojo;
import de.btu.openinfra.backend.db.rbac.AttributeTypeAssociationRbac;
import de.btu.openinfra.backend.db.rbac.AttributeTypeGroupToAttributeTypeRbac;
import de.btu.openinfra.backend.db.rbac.AttributeTypeRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/attributetypes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttributeTypeResource {

	@GET
	public List<AttributeTypePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeRbac(
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
	@Path("{attributeTypeId}/associations")
	public List<AttributeTypeAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						offset,
						size);
	}

	@GET
    @Path("{attributeTypeId}/associations/count")
	@Produces({MediaType.TEXT_PLAIN})
    public long getAssociationsCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeId") UUID attributeTypeId) {
        return new AttributeTypeAssociationRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
                        attributeTypeId);
    }

	@GET
	@Path("{attributeTypeId}/associations/{associatedAttributeTypeId}")
	public List<AttributeTypeAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@PathParam("associatedAttributeTypeId")
				UUID associatedAttributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeAssociationRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						associatedAttributeTypeId,
						offset,
						size);
	}

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new AttributeTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo);
	}

	@GET
	@Path("{attributeTypeId}")
	public AttributeTypePojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId) {
		return new AttributeTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId);
	}

	@GET
	@Path("{attributeTypeId}/attributetypegroups")
	public List<AttributeTypeGroupToAttributeTypePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupToAttributeTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						offset,
						size);
	}

	@GET
	@Path("{attributeTypeId}/attributetypegroups/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getAttributeTypeGroupsCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId) {
		return new AttributeTypeGroupToAttributeTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						attributeTypeId);
	}

	@GET
	@Path("{attributeTypeId}/attributetypegroups/{attributeTypeGroupId}")
	public List<AttributeTypeGroupToAttributeTypePojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupToAttributeTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeId,
						attributeTypeGroupId,
						offset,
						size);
	}

	@GET
    @Path("/new")
    public AttributeTypePojo newAttributeType(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema) {
        return new AttributeTypeRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
                    .newAttributeType(
    						OpenInfraHttpMethod.valueOf(request.getMethod()), 
    						uriInfo,
    						PtLocaleDao.forLanguageTag(language));
    }

    @POST
    public Response create(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            AttributeTypePojo pojo) {
        UUID id = new AttributeTypeRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
                        pojo,
                        null,
                        pojo.getMetaData());
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @DELETE
    @Path("{attributeTypeId}")
    public Response delete(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeId") UUID attributeTypeId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new AttributeTypeRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
        						OpenInfraHttpMethod.valueOf(
        								request.getMethod()), 
        						uriInfo,
                                attributeTypeId),
                        attributeTypeId);
    }

    @PUT
    @Path("{attributeTypeId}")
    public Response update(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            AttributeTypePojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new AttributeTypeRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
        						OpenInfraHttpMethod.valueOf(
        								request.getMethod()), 
        						uriInfo,
        						pojo,
                                attributeTypeId, 
                                pojo.getMetaData()));
    }

    @GET
    @Path("{attributeTypeId}/associations/new")
    public AttributeTypeAssociationPojo newAttributeTypeAssociation(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeId") UUID attributeTypeId) {
        return new AttributeTypeAssociationRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
                    .newAttributeTypeAssociation(
    						OpenInfraHttpMethod.valueOf(request.getMethod()), 
    						uriInfo,
    						attributeTypeId);
    }

    @POST
    @Path("{attributeTypeId}/associations")
    public Response createAssociation(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            AttributeTypeAssociationPojo pojo) {
        UUID id = new AttributeTypeAssociationRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
						OpenInfraHttpMethod.valueOf(request.getMethod()), 
						uriInfo,
                        pojo, attributeTypeId,
                        pojo.getAssociationAttributeTypeId(),
                        pojo.getMetaData());
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @DELETE
    @Path("{attributeTypeId}/associations/{associatedAttributeTypeId}")
    public Response deleteAssociation(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("associatedAttributeTypeId")
                UUID associatedAttributeTypeId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new AttributeTypeAssociationRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
        						OpenInfraHttpMethod.valueOf(
        								request.getMethod()), 
        						uriInfo,
                                associatedAttributeTypeId),
                                associatedAttributeTypeId);
    }

    @PUT
    @Path("{attributeTypeId}/associations/{associatedAttributeTypeId}")
    public Response updateAssociation(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            @PathParam("associatedAttributeTypeId")
                UUID associatedAttributeTypeId,
            @PathParam("schema") String schema,
            AttributeTypeAssociationPojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new AttributeTypeAssociationRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase()))
                            .createOrUpdate(
                            		OpenInfraHttpMethod.valueOf(
                            				request.getMethod()), 
            						uriInfo,
                                    pojo, attributeTypeId,
                                    pojo.getAssociationAttributeTypeId(),
                                    associatedAttributeTypeId,
                                    pojo.getAssociatedAttributeType().getUuid(),
                                    pojo.getMetaData()));
    }
}
