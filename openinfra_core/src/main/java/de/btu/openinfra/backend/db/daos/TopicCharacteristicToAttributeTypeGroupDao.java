package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroupToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToAttributeTypeGroupPojo;

public class TopicCharacteristicToAttributeTypeGroupDao extends
	OpenInfraValueValueDao<TopicCharacteristicToAttributeTypeGroupPojo,
	AttributeTypeGroupToTopicCharacteristic, AttributeTypeGroup,
	TopicCharacteristic> {
	
	private TopicCharacteristicDao topicCharacteristicDao;

	public TopicCharacteristicToAttributeTypeGroupDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema,
				AttributeTypeGroupToTopicCharacteristic.class,
				AttributeTypeGroup.class, TopicCharacteristic.class);
		
		topicCharacteristicDao = new TopicCharacteristicDao(currentProjectId,
			schema);
	}

	@Override
	public TopicCharacteristicToAttributeTypeGroupPojo mapToPojo(
			Locale locale,
			AttributeTypeGroupToTopicCharacteristic modelObject) {
		
		if(modelObject != null) {
			TopicCharacteristicToAttributeTypeGroupPojo pojo =
					new TopicCharacteristicToAttributeTypeGroupPojo();
			
			pojo.setUuid(modelObject.getId());
			pojo.setTopicCharacteristic(
				topicCharacteristicDao.mapToPojo(locale,
					modelObject.getTopicCharacteristic()));
			pojo.setAttributTypeGroupId(
				modelObject.getAttributeTypeGroup().getId());
			pojo.setMultiplicity(MultiplicityDao.mapToPojoStatically(
					modelObject.getMultiplicityBean()));
			
			if(modelObject.getOrder() != null) {
				pojo.setOrder(modelObject.getOrder());
			}
			
			return pojo;
		}
		else {
			return null;
		}
	}

	@Override
	public MappingResult<AttributeTypeGroupToTopicCharacteristic> mapToModel(
			TopicCharacteristicToAttributeTypeGroupPojo pojoObject,
			AttributeTypeGroupToTopicCharacteristic modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
