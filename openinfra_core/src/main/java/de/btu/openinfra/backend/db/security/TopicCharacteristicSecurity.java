package de.btu.openinfra.backend.db.security;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;

import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDao;
import de.btu.openinfra.backend.db.jpa.model.Project;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;

public class TopicCharacteristicSecurity extends 
	OpenInfraValueSecurity<TopicCharacteristicPojo,
	TopicCharacteristic, Project, TopicCharacteristicDao> {

	public TopicCharacteristicSecurity(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, 
				new TopicCharacteristicDao(currentProjectId, schema));
	}
	
	public List<TopicCharacteristicPojo> read(Locale locale, String filter) 
			throws UnauthorizedException, UnauthenticatedException{
		checkPermission();
		return ((TopicCharacteristicDao)dao).read(locale, filter);
	}
}
