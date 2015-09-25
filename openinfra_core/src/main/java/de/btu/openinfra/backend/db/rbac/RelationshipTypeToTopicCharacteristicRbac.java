package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.RelationshipTypeToTopicCharacteristicDao;
import de.btu.openinfra.backend.db.jpa.model.RelationshipType;
import de.btu.openinfra.backend.db.jpa.model.RelationshipTypeToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.RelationshipTypeToTopicCharacteristicPojo;

public class RelationshipTypeToTopicCharacteristicRbac extends 
	OpenInfraValueValueRbac<RelationshipTypeToTopicCharacteristicPojo,
	RelationshipTypeToTopicCharacteristic, TopicCharacteristic,
	RelationshipType, RelationshipTypeToTopicCharacteristicDao> {

	public RelationshipTypeToTopicCharacteristicRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicCharacteristic.class,
				RelationshipType.class, 
				RelationshipTypeToTopicCharacteristicDao.class);
	}

}
