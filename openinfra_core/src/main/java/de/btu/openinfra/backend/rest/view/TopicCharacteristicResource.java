package de.btu.openinfra.backend.rest.view;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToTopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI + "/topiccharacteristics")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class TopicCharacteristicResource {

	@GET
	@Template(name="/views/list/TopicCharacteristics.jsp")
	public List<TopicCharacteristicPojo> getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@QueryParam("filter") String filter,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") OpenInfraOrderByEnum orderBy,
			@PathParam("offset") int offset,
			@PathParam("size") int size) {
		return new de.btu.openinfra.backend.rest.TopicCharacteristicResource()
			.get(language, projectId, schema, filter, sortOrder, orderBy,
					offset, size);
	}

	@GET
	@Path("{topicCharacteristicId}")
	@Template(name="/views/detail/TopicCharacteristics.jsp")
	public TopicCharacteristicPojo getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId) {
		return new de.btu.openinfra.backend.rest.TopicCharacteristicResource()
			.get(language, projectId, schema, topicCharacteristicId);
	}

	@GET
	@Template(name="/views/list/TopicInstances.jsp")
	@Path("{topicCharacteristicId}/topicinstances")
	public List<TopicInstancePojo> getTopicInstancesView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("filter") String filter,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") String orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.project.
		        TopicCharacteristicResource().getTopicInstances(
		                language, projectId, schema, topicCharacteristicId,
		                filter, sortOrder, orderBy, offset, size);
	}

	@GET
	@Path("{topicCharacteristicId}/attributetypegroups")
	@Template(name="/views/list/AttributeTypeGroupsToTopicCharactristics.jsp")
	public List<AttributeTypeGroupToTopicCharacteristicPojo> getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("schema") String schema,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.TopicCharacteristicResource()
			.get(language, projectId, schema,
					topicCharacteristicId, offset, size);
	}

}
