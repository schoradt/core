package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.MetaData;
import de.btu.openinfra.backend.db.jpa.model.RelationshipType;
import de.btu.openinfra.backend.db.jpa.model.RelationshipTypeToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicToRelationshipTypePojo;

public class TopicCharacteristicToRelationshipTypeDao 
	extends OpenInfraValueValueDao<TopicCharacteristicToRelationshipTypePojo,
	RelationshipTypeToTopicCharacteristic, RelationshipType,
	TopicCharacteristic> {
	
	public TopicCharacteristicToRelationshipTypeDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema,
				RelationshipTypeToTopicCharacteristic.class,
				RelationshipType.class, TopicCharacteristic.class);
	}
	
	@Override
	public TopicCharacteristicToRelationshipTypePojo mapToPojo(
			Locale locale,
			RelationshipTypeToTopicCharacteristic association) {
		return mapToPojoStatically(
			locale,
			association,
			em.find(MetaData.class, association.getId()));
	}

	@Override
	public MappingResult<RelationshipTypeToTopicCharacteristic> mapToModel(
			TopicCharacteristicToRelationshipTypePojo pojoObject,
			RelationshipTypeToTopicCharacteristic modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static TopicCharacteristicToRelationshipTypePojo mapToPojoStatically(
			Locale locale,
			RelationshipTypeToTopicCharacteristic association,
			MetaData md) {
		
		if(association != null) {
			TopicCharacteristicToRelationshipTypePojo pojo =
					new TopicCharacteristicToRelationshipTypePojo();
			
			pojo.setUuid(association.getId());
			pojo.setRelationshipe(association.getRelationshipType().getId());
			pojo.setMultiplicity(MultiplicityDao.mapToPojoStatically(
					association.getMultiplicityBean()));
			pojo.setTopicCharacteristic(
				TopicCharacteristicDao.mapToPojoStatically(
					locale,
					association.getTopicCharacteristic(),
					md));
			
			return pojo;
		}
		else {
			return null;
		}
	}

}
