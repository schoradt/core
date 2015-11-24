package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

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

}
