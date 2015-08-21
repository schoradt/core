package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeGroupDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.pojos.AttributeTypeGroupPojo;

public class AttributeTypeGroupRbac extends OpenInfraRbac<
	AttributeTypeGroupPojo, AttributeTypeGroup, AttributeTypeGroupDao> {

	public AttributeTypeGroupRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeTypeGroupDao.class);
	}
	
	public List<AttributeTypeGroupPojo> readSubGroups(
			Locale locale,
			UUID attributeTypeGroupId) {
		checkPermission();
		return new AttributeTypeGroupDao(
				currentProjectId,
				schema).readSubGroups(locale, attributeTypeGroupId);
	}

}
