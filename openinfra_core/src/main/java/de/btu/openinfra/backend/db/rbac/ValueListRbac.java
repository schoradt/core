package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ValueListDao;
import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.pojos.ValueListPojo;

public class ValueListRbac extends 
	OpenInfraRbac<ValueListPojo, ValueList, ValueListDao> {

	public ValueListRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, ValueListDao.class);
	}
	
	public ValueListPojo newValueList(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo, Locale locale) {
		checkPermission(httpMethod, uriInfo);
		return new ValueListDao(currentProjectId, schema).newValueList(locale);
	}

}
