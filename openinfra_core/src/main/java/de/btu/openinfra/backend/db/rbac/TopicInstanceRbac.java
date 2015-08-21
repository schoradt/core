package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;

public class TopicInstanceRbac extends OpenInfraValueRbac<TopicInstancePojo,
	TopicInstance, TopicCharacteristic, TopicInstanceDao> {

	protected TopicInstanceRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, 
				TopicCharacteristic.class, TopicInstanceDao.class);
	}
	
	public List<TopicInstancePojo> read(
			Locale locale,
			UUID topicCharacteristicId,
			String filter,
			int offset,
			int size) {
		checkPermission();
		return new TopicInstanceDao(topicCharacteristicId, schema).read(
				locale, topicCharacteristicId, filter, offset, size);
	}
	
	public List<TopicInstancePojo> readWithGeomz(
            Locale locale,
            UUID topicCharacteristicId,
            int offset,
            int size) {
		checkPermission();
		return new TopicInstanceDao(
				topicCharacteristicId, 
				schema).readWithGeomz(locale, 
						topicCharacteristicId, offset, size);
	}
	
	public long getCountWithGeomz(UUID topicCharacteristicId) {
		checkPermission();
		return new TopicInstanceDao(
				topicCharacteristicId, 
				schema).getCountWithGeomz(topicCharacteristicId);
		}

}
