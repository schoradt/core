package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeGroup;
import de.btu.openinfra.backend.db.pojos.AttributeTypePojo;

public class AttributeTypeRbac extends OpenInfraValueRbac<AttributeTypePojo, 
	AttributeType, AttributeTypeGroup, AttributeTypeDao> {
	
	public AttributeTypeRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, 
				schema, AttributeTypeGroup.class, AttributeTypeDao.class);
	}

	public AttributeTypePojo read(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo, Locale locale, String dataType) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeTypeDao(
				currentProjectId, 
				schema).read(locale, dataType);
	}
	
	public AttributeTypePojo newAttributeType(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo, Locale locale) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeTypeDao(
				currentProjectId, 
				schema).newAttributeType(locale);
	}

}
