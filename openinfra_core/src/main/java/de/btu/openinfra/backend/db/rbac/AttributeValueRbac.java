package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueDao;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeValue;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.pojos.AttributeValuePojo;

public class AttributeValueRbac extends 
	OpenInfraValueValueRbac<AttributeValuePojo, AttributeValue, TopicInstance, 
	AttributeType, AttributeValueDao> {

	public AttributeValueRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, TopicInstance.class,
				AttributeType.class, AttributeValueDao.class);
	}
	
	public AttributeValuePojo read(
			Locale locale,
			UUID attributeValueId,
			AttributeValueGeomType geomType) {
		checkPermission();
		return new AttributeValueDao(
					currentProjectId, 
					schema).read(locale, attributeValueId, geomType);

	}
}
