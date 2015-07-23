package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.OpenInfraProperties;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.TopicGeomzDao;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.pojos.TopicGeomzPojo;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/projects/{projectId}/topiccharacteristics")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY,
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class TopicCharacteristicResource {

	/**
	 * This method will read a list of topic instances for a specific topic
	 * characteristic id. Additional to the standard query parameter it will
	 * accept a filter parameter and orderBy parameter. It is possible to set
	 * both orderBy parameter simultaneously but the
	 *
	 * @param language
	 * @param projectId
	 * @param schema
	 * @param topicCharacteristicId
	 * @param filter
	 * @param sortOrder
	 * @param orderByEnum
	 * @param orderByUuid
	 * @param offset
	 * @param size
	 * @return
	 */
	@GET
	@Path("{topicCharacteristicId}/topicinstances")
	public List<TopicInstancePojo> getTopicInstances(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("filter") String filter,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
            @QueryParam("orderBy") String orderBy,
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
					sortOrder,
					new OpenInfraOrderBy(orderBy),
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
}
