package de.btu.openinfra.backend.rest.view.project;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Template;

import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.TopicDao;
import de.btu.openinfra.backend.db.pojos.TopicPojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/projects/{projectId}/topicinstances")
@Produces(MediaType.TEXT_HTML +
		OpenInfraResponseBuilder.UTF8_CHARSET +
		OpenInfraResponseBuilder.HTML_PRIORITY)
public class TopicResource {

	@GET
	@Path("{topicInstanceId}/topic")
	@Template(name="/views/Topic.jsp")
	public TopicPojo getView(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@QueryParam("geomType") AttributeValueGeomType geomType) {
		return new TopicDao(
				projectId,
				OpenInfraSchemas.PROJECTS).read(
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId,
						geomType);
	}

}
