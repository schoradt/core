package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicToAttributeTypeGroupDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroupToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToAttributeTypeGroupPojo;

public class TopicCharacteristicToAttributeTypeGroupRbac extends 
	OpenInfraValueValueRbac<TopicCharacteristicToAttributeTypeGroupPojo,
	AttributeTypeGroupToTopicCharacteristic, AttributeTypeGroup,
	TopicCharacteristic, TopicCharacteristicToAttributeTypeGroupDao> {

	public TopicCharacteristicToAttributeTypeGroupRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeTypeGroup.class,
				TopicCharacteristic.class, 
				TopicCharacteristicToAttributeTypeGroupDao.class);
	}

}
