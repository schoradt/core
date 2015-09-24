package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeGroupToTopicCharacteristicDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroupToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupToTopicCharacteristicPojo;

public class AttributeTypeGroupToTopicCharacteristicRbac extends 
	OpenInfraValueValueRbac<AttributeTypeGroupToTopicCharacteristicPojo,
	AttributeTypeGroupToTopicCharacteristic, TopicCharacteristic,
	AttributeTypeGroup, AttributeTypeGroupToTopicCharacteristicDao> {

	public AttributeTypeGroupToTopicCharacteristicRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicCharacteristic.class, 
				AttributeTypeGroup.class, 
				AttributeTypeGroupToTopicCharacteristicDao.class);
	}
}
