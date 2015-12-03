package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.RelationshipTypeToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.db.rbac.AttributeTypeGroupToTopicCharacteristicRbac;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.RelationshipTypeRbac;
import de.btu.openinfra.backend.db.rbac.RelationshipTypeToTopicCharacteristicRbac;
import de.btu.openinfra.backend.db.rbac.TopicCharacteristicRbac;

/**
 * This class refers to topic characteristics
 *
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/topiccharacteristics")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
public class TopicCharacteristicResource {

	/**
	 * Delivers a number of all available topic characteristics of a specific
	 * project.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the specific project id
	 * @param schema the used schema
	 * @return a number of all available topic characteristics
	 */
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicCharacteristicsCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new TopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo);
	}

	/**
	 * Delivers a list of topic characteristics by a filter and sorted. This
	 * resource is paging enabled.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param projectId
	 * @param schema
	 * @param filter
	 * @param sortOrder
	 * @param orderBy
	 * @param offset the number where to start
	 * @param size the max. number of list entries
	 * @return a list of topic characteristics
	 */
	@GET
	public List<TopicCharacteristicPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("filter") String filter,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderBy orderBy,
			@PathParam("offset") int offset,
			@PathParam("size") int size) {
		if(filter != null && filter.length() > 0) {
			return new TopicCharacteristicRbac(
					projectId,
					OpenInfraSchemas.valueOf(
							schema.toUpperCase())).read(
									OpenInfraHttpMethod.valueOf(
											request.getMethod()),
									uriInfo,
									PtLocaleDao.forLanguageTag(
											language), filter);
		} else {
			TopicCharacteristicRbac rbac = new TopicCharacteristicRbac(
					projectId, OpenInfraSchemas.valueOf(schema.toUpperCase()));
			List<TopicCharacteristicPojo> list = rbac.read(
					OpenInfraHttpMethod.valueOf(request.getMethod()),
					uriInfo,
					PtLocaleDao.forLanguageTag(language),
					sortOrder, orderBy,	offset,	size);
			return list;
		} // end if else
	}

	/**
	 * Delivers a topic characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the id of the requested topic characteristic
	 * @return a topic characteristic
	 */
	@GET
	@Path("{topicCharacteristicId}")
	public TopicCharacteristicPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new TopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId);
	}

	/**
	 * Delivers a list of AttributeTypeGroupToTopicCharacteristic objects. These
	 * describe associations between attribute type groups to topic
	 * characteristic. This resource is paging enabled.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the id of the topic characteristic
	 * @param offset the number where to start
	 * @param size the max. number of list entries
	 * @return a list of AttributeTypeGroupToTopicCharacteristic objects
	 */
	@GET
	@Path("{topicCharacteristicId}/attributetypegroups")
	public List<AttributeTypeGroupToTopicCharacteristicPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupToTopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId,
						offset,
						size);
	}

	/**
	 * Adds an existing attribute type group to an existing topic
	 * characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the topic characteristic id
	 * @param pojo the content to add including the UUID of the attribute type
	 * 	group
	 * @return the UUID of the newly created object
	 */
	@POST
	@Path("{topicCharacteristicId}/attributetypegroups")
    public Response createRelationshipType(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            AttributeTypeGroupToTopicCharacteristicPojo pojo) {
		return OpenInfraResponseBuilder.postResponse(
			new AttributeTypeGroupToTopicCharacteristicRbac(
			                projectId,
			                OpenInfraSchemas.valueOf(schema.toUpperCase())
			                ).createOrUpdate(
			                        OpenInfraHttpMethod.valueOf(
			                                request.getMethod()),
			                        uriInfo,
			                        pojo,
			                        topicCharacteristicId,
			                        pojo.getTopicCharacteristicId(),
			                        null,
			                        null));
    }

	/**
	 * This resource delivers the number of attribute type groups which are
	 * assigned to the specified topic characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the id of the project
	 * @param schema the schema
	 * @param topicCharacteristicId the id of the related topic characteristic
	 * @return a number of attribute type groups
	 */
	@GET
    @Path("{topicCharacteristicId}/attributetypegroups/count")
    @Produces({MediaType.TEXT_PLAIN})
    public long getAttributeTypeGroupCount(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
        return new AttributeTypeGroupToTopicCharacteristicRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        topicCharacteristicId);
    }

	/**
	 * Delivers an AttributeTypeGroupToTopicCharacteristicPojo which is an
	 * association of an attribute type group to a topic characteristic. This
	 * is necessary in order to create an association between an attribute type
	 * and an attribute type group.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param projectId the id of the project
	 * @param schema the schema
	 * @param attributeTypeGroupToTopicCharacteristicId the id of the required
	 * 			association
	 * @return an association of an attribute type group to a topic
	 * 	characteristic
	 */
	@GET
	@Path("{topicCharacteristicId}/attributetypegroups/"
			+ "{attributeTypeGroupId}")
	public AttributeTypeGroupToTopicCharacteristicPojo get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
		// The used read method returns a list of objects. In this special
		// case, there should be only one list entry. Thus, we only take the
		// first list element.
		return new AttributeTypeGroupToTopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId,
						attributeTypeGroupId,
						0,
						1).get(0);
	}

	/**
	 * This resource updates an existing association of an attribute type group
	 * to a topic characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param attributeTypeGroupToTopicCharacteristicId the id of the required
	 * 			association
	 * @param pojo the content to change
	 * @return the id of the changed object
	 */
	@PUT
	@Path("{topicCharacteristicId}/attributetypegroups/"
            + "{attributeTypeGroupId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
            AttributeTypeGroupToTopicCharacteristicPojo pojo) {
			return OpenInfraResponseBuilder.putResponse(
			        new AttributeTypeGroupToTopicCharacteristicRbac(
			                projectId,
			                OpenInfraSchemas.valueOf(schema.toUpperCase())
			                ).createOrUpdate(OpenInfraHttpMethod.valueOf(
			                		request.getMethod()), uriInfo,
			                		pojo,
			                		topicCharacteristicId,
			                		pojo.getTopicCharacteristicId(),
			                		attributeTypeGroupId,
			                		pojo.getAttributeTypeGroup().getUuid()));
    }

	/**
	 * This method deletes an existing association of an attribute type group
	 * to a topic characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param attributeTypeGroupToTopicCharacteristicId the id of the required
	 * 			association
	 * @return the id of the deleted object
	 */
    @DELETE
    @Path("{topicCharacteristicId}/attributetypegroups/"
            + "{attributeTypeGroupId}")
    public Response deleteAttributeTypeGroupToTopicCharacteristic(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("attributeTypeGroupId") UUID attributeTypeGroupId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new AttributeTypeGroupToTopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                topicCharacteristicId),
                                attributeTypeGroupId);
    }

    /**
     * Delivers a list of relationship types of a specific topic characteristic.
     *
     * @param uriInfo
     * @param request
     * @param language
     * @param projectId the project id
     * @param schema the schema
     * @param topicCharacteristicId the id of the related topic characteristic
     * @param offset the number where to start
     * @param size the max. number of list entries
     * @return a list of relationship types
     */
	@GET
	@Path("{topicCharacteristicId}/relationshiptypes")
	public List<RelationshipTypeToTopicCharacteristicPojo> getRelationshipTypes(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new RelationshipTypeToTopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId,
						offset,
						size);
	}

	/**
	 * Creates an association between two topic characteristics.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the topic characteristic id
	 * @param pojo the content to add
	 * @return the UUID of the newly created object
	 */
	@POST
	@Path("{topicCharacteristicId}/relationshiptypes")
    public Response createRelationshipType(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            RelationshipTypeToTopicCharacteristicPojo pojo) {
        return OpenInfraResponseBuilder.postResponse(
                new RelationshipTypeToTopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                topicCharacteristicId,
                                pojo.getTopicCharacteristicId(),
                                null,
                                null));
    }

	/**
	 * Delivers a specific relation between two topic characteristics.
	 * This resource is paging enabled.
	 *
	 * @param uriInfo
	 * @param request
	 * @param language
	 * @param projectId the project id
	 * @param schema the schema
	 * @param relationshipTypeId the id of the relation ship type
	 * @return the UUID of the relation ship
	 */
	@GET
	@Path("{topicCharacteristicId}/relationshiptypes/{relationshipTypeId}")
	public RelationshipTypeToTopicCharacteristicPojo getRelationshipTypes(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("relationshipTypeId") UUID relationshipTypeId) {
		return new RelationshipTypeToTopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						relationshipTypeId);
	}

	/**
	 * Changes an existing relation ship type.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param relationshipTypeId
	 * @param pojo
	 * @return the uuid of the changed object
	 */
	@PUT
	@Path("{topicCharacteristicId}/relationshiptypes/{relationshipTypeId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("relationshipTypeId") UUID relationshipTypeId,
            RelationshipTypeToTopicCharacteristicPojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new RelationshipTypeToTopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())
                        ).createOrUpdate(
                                OpenInfraHttpMethod.valueOf(
                                        request.getMethod()),
                                uriInfo,
                                pojo,
                                topicCharacteristicId,
                                pojo.getTopicCharacteristicId(),
                                relationshipTypeId,
                                pojo.getRelationshipType().getUuid()));
    }


	/**
	 * Deletes an existing relation.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the topic characteristic id
	 * @param relationshipTypeId the relation
	 * @return the UUID of the deleted object
	 */
	@DELETE
    @Path("{topicCharacteristicId}/relationshiptypes/{relationshipTypeId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("relationshipTypeId") UUID relationshipTypeId) {
	    return OpenInfraResponseBuilder.deleteResponse(
                new RelationshipTypeToTopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                topicCharacteristicId,
                                relationshipTypeId));
	}

	/**
	 * Delivers the number of relation ship types related to a specific topic
	 * characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the topic characteristic id
	 * @return the number of relation ship types
	 */
	@GET
	@Path("{topicCharacteristicId}/relationshiptypes/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getRelationshipTypeCount(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new RelationshipTypeRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						topicCharacteristicId);
	}

	/**
	 * Crates a new topic characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId project id
	 * @param schema the schema
	 * @param pojo the content to create
	 * @return the UUID of the newly created object
	 */
	@POST
    public Response create(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            TopicCharacteristicPojo pojo) {
        UUID id = new TopicCharacteristicRbac(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).createOrUpdate(
                		OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
                        null,
                        pojo);
        return OpenInfraResponseBuilder.postResponse(id);
    }

	/**
	 * Changes an existing topic characteristic.
	 *
	 * @param uriInfo
	 * @param request
	 * @param projectId the project id
	 * @param schema the schema
	 * @param topicCharacteristicId the topic characteristic id
	 * @param pojo the content to change
	 * @return the UUID of the changed object
	 */
    @PUT
    @Path("{topicCharacteristicId}")
    public Response update(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            TopicCharacteristicPojo pojo) {
        return OpenInfraResponseBuilder.putResponse(
                new TopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
                        		OpenInfraHttpMethod.valueOf(
                        				request.getMethod()),
        						uriInfo,
                                topicCharacteristicId,
                                pojo));
    }

    /**
     * This method deletes a topic characteristic.
     *
     * @param uriInfo
     * @param request
     * @param projectId the project id
     * @param schema the schema
     * @param topicCharacteristicId the id of the requested topic characteristic
     * @return the UUID of the deleted object
     */
    @DELETE
    @Path("{topicCharacteristicId}")
    public Response delete(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
        return OpenInfraResponseBuilder.deleteResponse(
                new TopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                        		OpenInfraHttpMethod.valueOf(
                        				request.getMethod()),
        						uriInfo,
                                topicCharacteristicId),
                                topicCharacteristicId);
    }

}
