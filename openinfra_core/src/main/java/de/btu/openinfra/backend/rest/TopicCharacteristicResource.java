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

@Path(OpenInfraResponseBuilder.REST_URI_DEFAULT + "/topiccharacteristics")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
	+ OpenInfraResponseBuilder.UTF8_CHARSET})
public class TopicCharacteristicResource {

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
			return new TopicCharacteristicRbac(
					projectId,
					OpenInfraSchemas.valueOf(
							schema.toUpperCase())).read(
									OpenInfraHttpMethod.valueOf(
											request.getMethod()),
									uriInfo,
									PtLocaleDao.forLanguageTag(
											language),
										sortOrder,
										orderBy,
										offset,
										size);
		} // end if else
	}

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

	@GET
	@Path("{topicCharacteristicId}/attributetypegroups/"
			+ "{attributeTypeGroupId}")
	public List<AttributeTypeGroupToTopicCharacteristicPojo> get(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@PathParam("attributeTypeGroupId") UUID attributeTypeGroupId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new AttributeTypeGroupToTopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId,
						attributeTypeGroupId,
						offset,
						size);

	}

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
                                null,
                                pojo.getMetaData()));
    }

	@GET
	@Path("{topicCharacteristicId}/relationshiptypes/{relationShipTypeId}")
	public List<RelationshipTypeToTopicCharacteristicPojo> getRelationshipTypes(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@PathParam("relationShipTypeId") UUID relationShipTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new RelationshipTypeToTopicCharacteristicRbac(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						OpenInfraHttpMethod.valueOf(request.getMethod()),
						uriInfo,
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId,
						relationShipTypeId,
						offset,
						size);
	}

	@PUT
	@Path("{topicCharacteristicId}/relationshiptypes/{relationShipTypeId}")
    public Response update(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("relationShipTypeId") UUID relationShipTypeId,
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
                                relationShipTypeId,
                                pojo.getRelationshipType().getUuid(),
                                pojo.getMetaData()));
    }

	@DELETE
    @Path("{topicCharacteristicId}/relationshiptypes/{relationShipTypeId}")
    public Response delete(
            @Context UriInfo uriInfo,
            @Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @PathParam("relationShipTypeId") UUID relationShipTypeId) {
	    return OpenInfraResponseBuilder.deleteResponse(
                new RelationshipTypeToTopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.valueOf(schema.toUpperCase())).delete(
                                OpenInfraHttpMethod.valueOf(
                                      request.getMethod()),
                                uriInfo,
                                topicCharacteristicId,
                                relationShipTypeId));
	}

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
                        pojo,
                        null,
                        pojo.getMetaData());
        return OpenInfraResponseBuilder.postResponse(id);
    }

    @PUT
    @Path("{topicCharacteristicId}")
    public Response update(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            TopicCharacteristicPojo pojo) {
        return OpenInfraResponseBuilder.postResponse(
                new TopicCharacteristicRbac(
                        projectId,
                        OpenInfraSchemas.PROJECTS).createOrUpdate(
                        		OpenInfraHttpMethod.valueOf(
                        				request.getMethod()),
        						uriInfo,
        						pojo,
                                topicCharacteristicId,
                                pojo.getMetaData()));
    }

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
