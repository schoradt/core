package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceAssociationFromDao;
import de.btu.openinfra.backend.db.daos.TopicInstanceAssociationToDao;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.TopicInstanceXTopicInstance;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationFromPojo;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationToPojo;

public class TopicInstanceAssociationFromRbac extends OpenInfraValueValueRbac<
	TopicInstanceAssociationToPojo, TopicInstanceXTopicInstance,
	TopicInstance, TopicInstance, TopicInstanceAssociationToDao> {

	public TopicInstanceAssociationFromRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicInstance.class,
				TopicInstance.class, TopicInstanceAssociationToDao.class);
	}

	public List<TopicInstanceAssociationFromPojo> readAssociationFromByTopchar(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			UUID topicInstance,
			UUID topChar,
			int offset, int size) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceAssociationFromDao(currentProjectId, schema)
			.readAssociationFromByTopchar(locale, topicInstance, topChar,
					offset, size);
	}

}
