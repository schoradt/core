package de.btu.openinfra.backend.rest.project;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.daos.TopicInstanceAssociationDao;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.pojos.TopicInstanceAssociationPojo;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;
import de.btu.openinfra.backend.rest.OpenInfraResponseBuilder;

@Path("/projects/{projectId}/topicinstances")
@Produces({MediaType.APPLICATION_JSON + OpenInfraResponseBuilder.JSON_PRIORITY, 
	MediaType.APPLICATION_XML + OpenInfraResponseBuilder.XML_PRIORITY})
public class TopicInstanceResource {
	
	@GET
	@Path("count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstanceCount(@PathParam("projectId") UUID projectId) {
		return new TopicInstanceDao(
				projectId, 
				OpenInfraSchemas.PROJECTS).getCount();
	}
	
	@GET
	@Path("{topicInstanceId}")
	public TopicInstancePojo get(
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId) {
		return new TopicInstanceDao(
				projectId, 
				OpenInfraSchemas.PROJECTS).read(
						PtLocaleDao.forLanguageTag(language),
						topicInstanceId);
	}
	
	@GET
	@Path("{topicInstanceId}/associations")
	public List<TopicInstanceAssociationPojo> getAssociations(			
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("offset") int offset, 
			@PathParam("size") int size) {
		return new TopicInstanceAssociationDao(
				projectId, 
				OpenInfraSchemas.PROJECTS).read(
						PtLocaleDao.forLanguageTag(language), 
						topicInstanceId, 
						offset, 
						size);
	}
	
	@GET
	@Path("{associatedTopicInstanceId}/associations/count")
	@Produces({MediaType.TEXT_PLAIN})
	public long getTopicInstanceAssociationCount(
			@PathParam("projectId") UUID projectId,
			@PathParam("associatedTopicInstanceId")
				UUID associatedTopicInstanceId) {
		return new TopicInstanceAssociationDao(
				projectId, 
				OpenInfraSchemas.PROJECTS).getCount(associatedTopicInstanceId);
	}
	
	@GET
	@Path("{associatedTopicInstanceId}/associations/{topicInstanceId}")
	public List<TopicInstanceAssociationPojo> getAssociation(			
			@QueryParam("language") String language,
			@PathParam("projectId") UUID projectId,
			@PathParam("associatedTopicInstanceId")
				UUID associatedTopicInstanceId,
			@PathParam("topicInstanceId") UUID topicInstanceId,
			@PathParam("offset") int offset, 
			@PathParam("size") int size) {
		return new TopicInstanceAssociationDao(
				projectId, 
				OpenInfraSchemas.PROJECTS).read(
						PtLocaleDao.forLanguageTag(language), 
						associatedTopicInstanceId,
						topicInstanceId, offset, size);
	}
}
