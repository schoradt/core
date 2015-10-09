package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicInstanceDao;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.pojos.TopicInstancePojo;

public class TopicInstanceRbac extends OpenInfraValueRbac<TopicInstancePojo,
	TopicInstance, TopicCharacteristic, TopicInstanceDao> {

	public TopicInstanceRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema,
				TopicCharacteristic.class, TopicInstanceDao.class);
	}

	public List<TopicInstancePojo> read(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			UUID topicCharacteristicId,
			String filter,
			int offset,
			int size) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceDao(currentProjectId, schema).read(
				locale, topicCharacteristicId, filter, offset, size);
	}

	public List<TopicInstancePojo> readWithGeomz(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
            Locale locale,
            UUID topicCharacteristicId,
            int offset,
            int size) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceDao(
		        currentProjectId,
				schema).readWithGeomz(locale,
						topicCharacteristicId, offset, size);
	}

	public long getCountWithGeomz(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo, UUID topicCharacteristicId) {
		checkPermission(httpMethod, uriInfo);
		return new TopicInstanceDao(
		        currentProjectId,
				schema).getCountWithGeomz(topicCharacteristicId);
		}

}
