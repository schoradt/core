package de.btu.openinfra.backend.db.daos;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.jpa.model.RelationshipType;
import de.btu.openinfra.backend.db.jpa.model.RelationshipTypeToTopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.RelationshipTypeToTopicCharacteristicPojo;

public class RelationshipTypeToTopicCharacteristicDao
	extends OpenInfraValueValueDao<RelationshipTypeToTopicCharacteristicPojo,
	RelationshipTypeToTopicCharacteristic, TopicCharacteristic,
	RelationshipType> {
	
	public RelationshipTypeToTopicCharacteristicDao(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema,
				RelationshipTypeToTopicCharacteristic.class,
				TopicCharacteristic.class, RelationshipType.class);
	}
	
	@Override
	public RelationshipTypeToTopicCharacteristicPojo mapToPojo(
			Locale locale,
			RelationshipTypeToTopicCharacteristic association) {
		return mapToPojoStatically(
			locale,
			association);
	}

	@Override
	public MappingResult<RelationshipTypeToTopicCharacteristic> mapToModel(
			RelationshipTypeToTopicCharacteristicPojo pojoObject,
			RelationshipTypeToTopicCharacteristic modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static RelationshipTypeToTopicCharacteristicPojo mapToPojoStatically(
			Locale locale,
			RelationshipTypeToTopicCharacteristic association) {
		
		if(association != null) {
			RelationshipTypeToTopicCharacteristicPojo pojo =
					new RelationshipTypeToTopicCharacteristicPojo();
			
			pojo.setUuid(association.getId());
			pojo.setTopicCharacteristicId(
				association.getTopicCharacteristic().getId());
			pojo.setMultiplicity(MultiplicityDao.mapToPojoStatically(
					association.getMultiplicityBean()));
			pojo.setRelationshipType(
				RelationshipTypeDao.mapToPojoStatically(
					locale,
					association.getRelationshipType()));
			
			return pojo;
		}
		else {
			return null;
		}
	}
}
