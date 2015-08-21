package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceAssociationDao;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.jpa.model.TopicInstanceXTopicInstance;
import de.btu.openinfra.backend.db.pojos.TopicInstanceAssociationPojo;

public class TopicInstanceAssociationRbac extends OpenInfraValueValueRbac<
	TopicInstanceAssociationPojo, TopicInstanceXTopicInstance, 
	TopicInstance, TopicInstance, TopicInstanceAssociationDao> {

	protected TopicInstanceAssociationRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicInstance.class, 
				TopicInstance.class, TopicInstanceAssociationDao.class);
	}
	
	public List<TopicInstanceAssociationPojo> readParents(
			Locale locale, UUID self) {
		checkPermission();
		return new TopicInstanceAssociationDao(
				currentProjectId,
				schema).readParents(locale, self);
	}

}
