package de.btu.openinfra.backend.rest;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.db.daos.AttributeTypeGroupToTopicCharacteristicDao;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.RelationshipTypeDao;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDao;
import de.btu.openinfra.backend.db.daos.TopicGeomzDao;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicGeomzPojo;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;

@Path(OpenInfraResponseBuilder.REST_URI + "/topiccharacteristics")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class TopicCharacteristicResource {

	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicCharacteristicsCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema) {
		return new TopicCharacteristicDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount();
	}
	
	@GET
	public List<TopicCharacteristicPojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("filter") String filter,
			@PathParam("offset") int offset, 
			@PathParam("size") int size) {
		if(filter != null && filter.length() > 0) {
			return new TopicCharacteristicDao(
					projectId, 
					OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
							PtLocaleDao.forLanguageTag(language), 
							filter);
		} else {
			return new TopicCharacteristicDao(
					projectId, 
					OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
							PtLocaleDao.forLanguageTag(language),
							offset, 
							size);
		} // end if else
	}
	
	
	
	@GET
	@Path("{topicCharacteristicId}")
	public TopicCharacteristicPojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new TopicCharacteristicDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId);
	}
	
	@GET
	@Path("{topicCharacteristicId}/topicinstances")
	public List<TopicInstancePojo> getTopicInstances(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("filter") String filter,
			@QueryParam("offset") int offset, 
			@QueryParam("size") int size) {
		// Define the specific parameters when not specified correctly
		if(size == 0) {
			offset = OpenInfraProperties.DEFAULT_OFFSET;
			size = OpenInfraProperties.DEFAULT_SIZE;
		} // end if
		
		if(filter != null && filter.length() > 0) {
			return new TopicInstanceDao(
					projectId, 
					OpenInfraSchemas.PROJECTS).read(
							PtLocaleDao.forLanguageTag(language),
							topicCharacteristicId,
							filter,
							offset, 
							size);
		} else {
			return new TopicInstanceDao(
					projectId, 
					OpenInfraSchemas.PROJECTS).read(
					PtLocaleDao.forLanguageTag(language),
					topicCharacteristicId, 
					offset, 
					size);
		} // end if else
	}
	
	@GET
	@Path("{topicCharacteristicId}/topicinstances/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstancesCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new TopicInstanceDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						topicCharacteristicId);
	}
	
	/**
	 * This is a special representation of the topic object. It delivers always
	 * a list of topic instances with corresponding 3D attribute values as X3D.
	 * 
	 * @param language
	 * @param projectId
	 * @param topicCharacteristicId
	 * @param geomType
	 * @param offset
	 * @param size
	 * @return
	 */
	@GET
    @Path("{topicCharacteristicId}/topicinstances/geomz")
    public List<TopicGeomzPojo> getTopicInstancesGeomz(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @QueryParam("geomType") AttributeValueGeomType geomType,
            @QueryParam("offset") int offset, 
            @QueryParam("size") int size) {
	    return new TopicGeomzDao(
	            projectId,
	            OpenInfraSchemas.valueOf(schema.toUpperCase()),
	            geomType).read(
	                    PtLocaleDao.forLanguageTag(language),
	                    topicCharacteristicId,
	                    offset,
	                    size);
    }
	
	/**
     * This is a special representation of the topic object. It delivers always
     * a list of topic instances with corresponding 3D attribute values as X3D.
     * 
     * @param language
     * @param projectId
     * @param topicCharacteristicId
     * @param geomType
     * @return
     */
    @GET
    @Path("{topicCharacteristicId}/topicinstances/geomz/count")
    public long getTopicInstancesGeomzCount(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("schema") String schema,
            @PathParam("topicCharacteristicId") UUID topicCharacteristicId,
            @QueryParam("geomType") AttributeValueGeomType geomType) {
        return new TopicGeomzDao(
                projectId,
                OpenInfraSchemas.valueOf(schema.toUpperCase()),
                geomType).getCount(topicCharacteristicId);
    }

	@GET
	@Path("{topicCharacteristicId}/attributetypegroups")
	public List<AttributeTypeGroupToTopicCharacteristicPojo> get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset, 
			@QueryParam("size") int size) {
		
		return new AttributeTypeGroupToTopicCharacteristicDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language),
						topicCharacteristicId, 
						offset, 
						size);
	}

	@GET
	@Path("{topicCharacteristicId}/attributetypegroups/"
			+ "{attributeTypeGroupToTopicCharacteristicId}")
	public AttributeTypeGroupToTopicCharacteristicPojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@PathParam("attributeTypeGroupToTopicCharacteristicId") 
				UUID attributeTypeGroupToTopicCharacteristicId) {
		return new AttributeTypeGroupToTopicCharacteristicDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language), 
						attributeTypeGroupToTopicCharacteristicId);
	}
	
	@GET
	@Path("{topicCharacteristicId}/relationshiptypes")
	public List<RelationshipTypePojo> getRelationshipTypes(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset, 
			@QueryParam("size") int size) {
		
		return new RelationshipTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).read(
						PtLocaleDao.forLanguageTag(language), 
						topicCharacteristicId, 
						offset, 
						size);
	}
	
	@GET
	@Path("{topicCharacteristicId}/relationshiptypes/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getAttributeTypeGroupCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new RelationshipTypeDao(
				projectId, 
				OpenInfraSchemas.valueOf(schema.toUpperCase())).getCount(
						topicCharacteristicId);
	}

}
