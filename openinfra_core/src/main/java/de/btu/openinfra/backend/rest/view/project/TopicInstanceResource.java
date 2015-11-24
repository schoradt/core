package de.btu.openinfra.backend.rest.view.project;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.pojos.TopicPojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path(OpenInfraResponseBuilder.REST_URI_PROJECTS + "/topicinstances")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class TopicInstanceResource {

	@GET
	@Path("{topicInstanceId}/associations")
	@Template(name="/views/list/TopicInstancesAssociations.jsp")
	public List<TopicInstanceAssociationPojo> getAssociations(
			@Context UriInfo uriInfo,
			@Context HttpServletRequest request,
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("offset") int offset,
			@PathParam("size") int size) {
		return new de.btu.openinfra.backend.rest.project.TopicInstanceResource()
				.getAssociations(
						uriInfo,
						request,
						language,
						projectId,
						topicInstanceId,
						offset,
						size);
	}


	@GET
    @Path("{topicInstanceId}/topic")
    @Template(name="/views/Topic.jsp")
    public TopicPojo getView(
    		@Context UriInfo uriInfo,
    		@Context HttpServletRequest request,
            @QueryParam("language") String language,
            @PathParam("projectId") UUID projectId,
            @PathParam("topicInstanceId") UUID topicInstanceId,
            @QueryParam("geomType") AttributeValueGeomType geomType) {
        return new de.btu.openinfra.backend.rest.project.TopicInstanceResource()
                       .get(uriInfo, request, language, projectId,
                    		   topicInstanceId, geomType);
    }

}
