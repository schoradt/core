package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicToRelationshipTypeDao;
import de.btu.openinfra.backend.db.jpa.model.RelationshipType;
import de.btu.openinfra.backend.db.jpa.model.RelationshipTypeToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToRelationshipTypePojo;

public class TopicCharacteristicToRelationshipTypeRbac extends 
	OpenInfraValueValueRbac<TopicCharacteristicToRelationshipTypePojo,
	RelationshipTypeToTopicCharacteristic, RelationshipType,
	TopicCharacteristic, TopicCharacteristicToRelationshipTypeDao> {

	public TopicCharacteristicToRelationshipTypeRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, RelationshipType.class,
				TopicCharacteristic.class, 
				TopicCharacteristicToRelationshipTypeDao.class);
	}

}
