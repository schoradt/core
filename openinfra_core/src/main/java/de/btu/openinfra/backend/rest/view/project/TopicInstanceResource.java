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

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.TopicInstanceAssociationPojo;
import de.btu.openinfra.backend.db.pojos.TopicPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/projects/{projectId}/topicinstances")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class TopicInstanceResource {

	@GET
	@Path("{topicInstanceId}/associations")
	@Template(name="/views/list/TopicInstancesAssociations.jsp")
	public List<TopicInstanceAssociationPojo> getAssociations(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("offset") int offset,
			@PathParam("size") int size) {
		return new de.btu.openinfra.backend.rest.project.TopicInstanceResource()
				.getAssociations(
						language,
						projectId,
						topicInstanceId,
						offset,
						size);
	}

	@GET
	@Path("{topicInstanceId}/parents")
	@Template(name="/views/list/TopicInstanceParents.jsp")
	public List<TopicInstanceAssociationPojo> getParents(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId) {
		return new de.btu.openinfra.backend.rest.project.TopicInstanceResource()
				.getParents(language, projectId, topicInstanceId);
	}

	@GET
    @Path("{topicInstanceId}/topic")
    @Template(name="/views/Topic.jsp")
    public TopicPojo getView(
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @QueryParam("geomType") AttributeValueGeomType geomType) {
        return new de.btu.openinfra.backend.rest.project.TopicInstanceResource()
                       .get(language, projectId, topicInstanceId, geomType);
    }

}
