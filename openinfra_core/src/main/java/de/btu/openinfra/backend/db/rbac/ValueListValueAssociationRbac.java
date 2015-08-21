package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ValueListValueAssociationDao;
import de.btu.openinfra.backend.db.jpa.model.ValueListValue;
import de.btu.openinfra.backend.db.jpa.model.ValueListValuesXValueListValue;
import de.btu.openinfra.backend.db.pojos.ValueListValueAssociationPojo;

public class ValueListValueAssociationRbac extends OpenInfraValueValueRbac<
	ValueListValueAssociationPojo, ValueListValuesXValueListValue, 
	ValueListValue, ValueListValue, ValueListValueAssociationDao> {

	public ValueListValueAssociationRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, ValueListValue.class, 
				ValueListValue.class, ValueListValueAssociationDao.class);
	}

}
