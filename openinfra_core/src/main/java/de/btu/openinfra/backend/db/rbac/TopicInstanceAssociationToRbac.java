package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.project.TopicInstanceAssociationToDao;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.TopicInstanceXTopicInstance;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationToPojo;

public class TopicInstanceAssociationToRbac extends OpenInfraValueValueRbac<
	TopicInstanceAssociationToPojo, TopicInstanceXTopicInstance,
	TopicInstance, TopicInstance, TopicInstanceAssociationToDao> {

	public TopicInstanceAssociationToRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicInstance.class,
				TopicInstance.class, TopicInstanceAssociationToDao.class);
	}

	public List<TopicInstanceAssociationToPojo> readAssociationToByTopchar(
					OpenInfraHttpMethod httpMethod,
					UriInfo uriInfo,
					Locale locale,
					UUID topicInstance,
					UUID topChar,
					int offset, int size,
					OpenInfraSortOrder sortOrder,
		            OpenInfraOrderBy orderBy) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceAssociationToDao(currentProjectId, schema)
			.readAssociationToByTopchar(locale, topicInstance, topChar,
					offset, size, sortOrder, orderBy);
	}

}
