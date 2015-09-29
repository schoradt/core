package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDao;
import de.btu.openinfra.backend.db.jpa.model.Project;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;

public class TopicCharacteristicRbac extends OpenInfraValueRbac<
	TopicCharacteristicPojo, TopicCharacteristic, Project, 
	TopicCharacteristicDao> {

	public TopicCharacteristicRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, 
				schema,
				Project.class,
				TopicCharacteristicDao.class);
	}

	public List<TopicCharacteristicPojo> read(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo, Locale locale, String filter) {
		checkPermission(httpMethod, uriInfo);
		return new TopicCharacteristicDao(
				currentProjectId, 
				schema).read(locale, filter);
	}
}
