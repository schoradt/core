package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ValueListValueDao;
import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.jpa.model.ValueListValue;
import de.btu.openinfra.backend.db.pojos.ValueListValuePojo;

public class ValueListValueRbac extends OpenInfraValueRbac<ValueListValuePojo, 
	ValueListValue, ValueList, ValueListValueDao> {

	protected ValueListValueRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, 
				ValueList.class, ValueListValueDao.class);
	}
	
	

}
