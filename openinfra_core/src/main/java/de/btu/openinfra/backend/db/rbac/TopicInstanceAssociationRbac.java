package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceAssociationDao;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.TopicInstanceXTopicInstance;
import de.btu.openinfra.backend.db.pojos.project.TopicInstanceAssociationPojo;

public class TopicInstanceAssociationRbac extends OpenInfraValueValueRbac<
	TopicInstanceAssociationPojo, TopicInstanceXTopicInstance, 
	TopicInstance, TopicInstance, TopicInstanceAssociationDao> {

	public TopicInstanceAssociationRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicInstance.class, 
				TopicInstance.class, TopicInstanceAssociationDao.class);
	}
	
	public List<TopicInstanceAssociationPojo> readParents(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo, 
			Locale locale, UUID self) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceAssociationDao(
				currentProjectId,
				schema).readParents(locale, self);
	}

}
