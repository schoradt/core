package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ValueListDao;
import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;

public class ValueListRbac extends 
	OpenInfraRbac<ValueListPojo, ValueList, ValueListDao> {

	public ValueListRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, ValueListDao.class);
	}
	
	public ValueListPojo newValueList(Locale locale) {
		checkPermission();
		return new ValueListDao(currentProjectId, schema).newValueList(locale);
	}

}
