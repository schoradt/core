package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.ValueListAssociationDao;
import de.btu.openinfra.backend.db.jpa.model.ValueList;
import de.btu.openinfra.backend.db.jpa.model.ValueListXValueList;
import de.btu.openinfra.backend.db.pojos.ValueListAssociationPojo;

public class ValueListAssociationRbac extends 
	OpenInfraValueValueRbac<ValueListAssociationPojo, ValueListXValueList, 
	ValueList, ValueList, ValueListAssociationDao> {

	protected ValueListAssociationRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, ValueList.class, 
				ValueList.class, ValueListAssociationDao.class);
	}

}
