package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueValueDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueValue;
import de.btu.openinfra.backend.db.pojos.AttributeValueValuePojo;

public class AttributeValueValueRbac extends 
	OpenInfraRbac<AttributeValueValuePojo, AttributeValueValue, 
	AttributeValueValueDao>{

	public AttributeValueValueRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeValueValueDao.class);
	}

}
