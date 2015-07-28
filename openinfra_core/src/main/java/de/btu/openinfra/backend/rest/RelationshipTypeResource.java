package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.RelationshipTypeDao;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicToRelationshipTypeDao;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToRelationshipTypePojo;

@Path(OpenInfraResponseBuilder.REST_URI + "/relationshiptypes")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET,
    MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY
    + OpenInfraResponseBuilder.UTF8_CHARSET})
public class RelationshipTypeResource {

    @GET
    public List <RelationshipTypePojo> get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
            @QueryParam("offset") int offset,
            @QueryParam("size") int size) {
        return new RelationshipTypeDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                        PtLocaleDao.forLanguageTag(language),
						sortOrder,
						orderBy,
                        offset,
                        size);
    }

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getAttributeTypeGroupCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new RelationshipTypeDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount();
	}

    @GET
    @Path("{relationshipTypeId}")
    public RelationshipTypePojo get(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("relationshipTypeId") UUID relationshipTypeId) {
        return new RelationshipTypeDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
                        PtLocaleDao.forLanguageTag(language),
                        relationshipTypeId);
    }

    @GET
	@Path("{relationshipTypeId}/topiccharacteristics")
	public List<TopicCharacteristicToRelationshipTypePojo>
    	getTopicCharacteristics(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("relationshipTypeId") UUID relationshipTypeId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {

		return new TopicCharacteristicToRelationshipTypeDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						relationshipTypeId,
						offset,
						size);
	}

	@GET
	@Path("{relationshipTypeId}/topiccharacteristics/{topicCharacteristicId}")
	public List<TopicCharacteristicToRelationshipTypePojo>
		getTopicCharacteristics(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("relationshipTypeId") UUID relationshipTypeId,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {

		return new TopicCharacteristicToRelationshipTypeDao(
				projectId,
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						relationshipTypeId,
						topicCharacteristicId,
						offset,
						size);
	}
}
