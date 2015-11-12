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
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.AttributeTypeToAttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToAttributeTypeGroupPojo;
import de.btu.openinfra.backend.db.rbac.AttributeTypeGroupRbac;
import de.btu.openinfra.backend.db.rbac.AttributeTypeRbac;
import de.btu.openinfra.backend.db.rbac.AttributeTypeToAttributeTypeGroupRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.TopicCharacteristicToAttributeTypeGroupRbac;

/**
 * This class represents and implements the AttributeTypeGroupResource resource.
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/attributetypegroups")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AttributeTypeGroupResource {

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getAttributeTypeGroupCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new AttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	@GET
	public List<AttributeTypeGroupPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupRbac(
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
	@Path("{attributeTypeGroupId}")
	public AttributeTypeGroupPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
		return new AttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeGroupId);
	}

	@GET
	@Path("{attributeTypeGroupId}/attributetypes")
	public List<AttributeTypeToAttributeTypeGroupPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeToAttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeGroupId,
						offset,
						size);
	}

	@POST
	@Path("{attributeTypeGroupId}/attributetypes")
    public Response createRelationshipType(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            AttributeTypeToAttributeTypeGroupPojo pojo) {
        return OpenInfraResponseBuilder.postResponse(
                new AttributeTypeToAttributeTypeGroupRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                attributeTypeGroupId,
                                pojo.getAttributeTypeGroupId(),
                                null,
                                null,
                                pojo.getMetaData()));
    }

	@GET
	@Path("{attributeTypeGroupId}/attributetypes/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getAttributeTypeCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
		return new AttributeTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						attributeTypeGroupId);
	}

	@GET
	@Path("{attributeTypeGroupId}/attributetypes/{attributeTypeId}")
	public List<AttributeTypeToAttributeTypeGroupPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@PathParam("attributeTypeId") UUID attributeTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeToAttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeGroupId,
						attributeTypeId,
						offset,
						size);
	}

	@PUT
	@Path("{attributeTypeGroupId}/attributetypes/{attributeTypeId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            @PathParam("attributeTypeId") UUID attributeTypeId,
            AttributeTypeToAttributeTypeGroupPojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new AttributeTypeToAttributeTypeGroupRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                attributeTypeGroupId,
                                pojo.getAttributeTypeGroupId(),
                                attributeTypeId,
                                pojo.getAttributeType().getUuid(),
                                pojo.getMetaData()));
    }

    @DELETE
    @Path("{attributeTypeGroupId}/attributetypes/{attributeTypeId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            @PathParam("attributeTypeId") UUID attributeTypeId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new AttributeTypeToAttributeTypeGroupRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                attributeTypeGroupId,
                                attributeTypeId));
    }

	@GET
	@Path("{attributeTypeGroupId}/topiccharacteristics")
	public List<TopicCharacteristicToAttributeTypeGroupPojo>
		getTopicCharacteristics(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new TopicCharacteristicToAttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeGroupId,
						offset,
						size);
	}

	@POST
    @Path("{attributeTypeGroupId}/topiccharacteristics")
	public Response create(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            TopicCharacteristicToAttributeTypeGroupPojo pojo) {
	    return OpenInfraResponseBuilder.postResponse(
                new TopicCharacteristicToAttributeTypeGroupRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                attributeTypeGroupId,
                                pojo.getAttributTypeGroupId(),
                                null,
                                null,
                                pojo.getMetaData()));
    }

	@GET
    @Path("{attributeTypeGroupId}/topiccharacteristics/count")
    @Produces({MediaType.TEXT_PLAIN})
    public long getTopicCharacteristicCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
	    return new TopicCharacteristicToAttributeTypeGroupRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        attributeTypeGroupId);
    }

	@GET
	@Path("{attributeTypeGroupId}/topiccharacteristics/{topicCharacteristicId}")
	public List<TopicCharacteristicToAttributeTypeGroupPojo>
		getTopicCharacteristics(
		    @Context UriInfo uriInfo,
		    @Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new TopicCharacteristicToAttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeGroupId,
						topicCharacteristicId,
						offset,
						size);
	}

	@PUT
    @Path("{attributeTypeGroupId}/topiccharacteristics/{topicCharacteristicId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            TopicCharacteristicToAttributeTypeGroupPojo pojo) {
	    return OpenInfraResponseBuilder.putResponse(
                new TopicCharacteristicToAttributeTypeGroupRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                attributeTypeGroupId,
                                pojo.getAttributTypeGroupId(),
                                topicCharacteristicId,
                                pojo.getTopicCharacteristic().getUuid(),
                                pojo.getMetaData()));
	}

	@DELETE
    @Path("{attributeTypeGroupId}/topiccharacteristics/{topicCharacteristicId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
	    return OpenInfraResponseBuilder.deleteResponse(
                new TopicCharacteristicToAttributeTypeGroupRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                attributeTypeGroupId,
                                topicCharacteristicId));
    }

	/**
	 * This method represents a specific HTTP GET method and is used to retrieve
	 * a list sub groups of an AttributeTypeGroup.
	 *
	 * @param projectId            the required project id
	 * @param attributeTypeGroupId the id of the required AttributeTypeGroup
	 * @return                     a list of AttributeTypeGroups (sub groups)
	 */
	@GET
	@Path("{attributeTypeGroupId}/subgroups")
	public List<AttributeTypeGroupPojo> getSubGroups(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
		return new AttributeTypeGroupRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).readSubGroups(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						attributeTypeGroupId);
	}

	@POST
    public Response create(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            AttributeTypeGroupPojo pojo) {
        UUID id = new AttributeTypeGroupRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						pojo,
						null,
						pojo.getMetaData());
        return OpenInfraResponseBuilder.postResponse(id);
    }

	@PUT
	@Path("{attributeTypeGroupId}")
	public Response update(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId,
	        @PathParam("schema") String schema,
	        @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
	        AttributeTypeGroupPojo pojo) {
	    UUID id = new AttributeTypeGroupRbac(
	            projectId,
	            OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
	            		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						pojo,
						attributeTypeGroupId,
						pojo.getMetaData());
	    return OpenInfraResponseBuilder.putResponse(id);
	}

	@DELETE
	@Path("{attributeTypeGroupId}")
	public Response delete(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
	        @PathParam("projectId") UUID projectId,
	        @PathParam("schema") String schema,
	        @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
	    return OpenInfraResponseBuilder.deleteResponse(
	            new AttributeTypeGroupRbac(
	                    projectId,
	                    OpenInfraSchemas.valueOf(schema.toUpperCase()))
	            .delete(OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						attributeTypeGroupId), attributeTypeGroupId);
	}
}
