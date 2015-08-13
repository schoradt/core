package de.btu.openinfra.backend.rest.view.project;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("projects/{projectId}/topiccharacteristics/{topicCharacteristicId}"
        + "/topicinstances")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class TopicCharacteristicResource {

	@GET
	@Template(name="/views/list/TopicInstances.jsp")
	public List<TopicInstancePojo> getTopicInstancesView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicCharacteristicId") UUID topicCharacteristicId,
			@QueryParam("filter") String filter,
			@QueryParam("sortOrder") OpenInfraSortOrder sortOrder,
			@QueryParam("orderBy") String orderBy,
			@QueryParam("offset") int offset,
			@QueryParam("size") int size) {
		return new de.btu.openinfra.backend.rest.project.
		        TopicCharacteristicResource().getTopicInstances(
		                language, projectId, topicCharacteristicId,
		                filter, sortOrder, orderBy, offset, size);
	}
}
