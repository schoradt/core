package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeToAttributeTypeGroupDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeToAttributeTypeGroup;
import de.btu.openinfra.backend.db.pojos.AttributeTypeToAttributeTypeGroupPojo;

public class AttributeTypeToAttributeTypeGroupRbac extends 
	OpenInfraValueValueRbac<AttributeTypeToAttributeTypeGroupPojo,
	AttributeTypeToAttributeTypeGroup, AttributeTypeGroup, AttributeType, 
	AttributeTypeToAttributeTypeGroupDao> {

	public AttributeTypeToAttributeTypeGroupRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeTypeGroup.class, 
				AttributeType.class, 
				AttributeTypeToAttributeTypeGroupDao.class);
	}

}
