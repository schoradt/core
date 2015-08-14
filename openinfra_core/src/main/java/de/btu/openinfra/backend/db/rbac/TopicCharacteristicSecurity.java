package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.TopicCharacteristicDao;
import de.btu.openinfra.backend.db.jpa.model.Project;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.TopicCharacteristicPojo;

public class TopicCharacteristicSecurity extends 
	OpenInfraValueSecurity<
	TopicCharacteristicPojo,
	TopicCharacteristic,
	Project,
	TopicCharacteristicDao> {


	public TopicCharacteristicSecurity(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicCharacteristicDao.class);
		// TODO Auto-generated constructor stub
	}

	public List<TopicCharacteristicPojo> read(Locale locale, String filter) {
		checkPermission();
		try {
			return dao.newInstance().read(locale, filter);
		} catch(Exception ex) {
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
		
	}
}
