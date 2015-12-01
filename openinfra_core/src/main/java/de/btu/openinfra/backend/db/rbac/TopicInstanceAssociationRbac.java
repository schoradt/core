package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceAssociationDao;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.TopicInstanceXTopicInstance;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationToPojo;

public class TopicInstanceAssociationRbac extends OpenInfraValueValueRbac<
	TopicInstanceAssociationToPojo, TopicInstanceXTopicInstance,
	TopicInstance, TopicInstance, TopicInstanceAssociationDao> {

	public TopicInstanceAssociationRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicInstance.class,
				TopicInstance.class, TopicInstanceAssociationDao.class);
	}

	public List<TopicInstanceAssociationToPojo> readAssociationToByTopchar(
					OpenInfraHttpMethod httpMethod,
					UriInfo uriInfo,
					Locale locale,
					UUID topicInstance,
					UUID topChar,
					int offset, int size) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceAssociationDao(currentProjectId, schema)
			.readAssociationToByTopchar(locale, topicInstance, topChar,
					offset, size);
	}

	public List<TopicInstanceAssociationToPojo> readAssociationFromByTopchar(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			UUID topicInstance,
			UUID topChar,
			int offset, int size) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceAssociationDao(currentProjectId, schema)
			.readAssociationFromByTopchar(locale, topicInstance, topChar,
					offset, size);
	}

}
