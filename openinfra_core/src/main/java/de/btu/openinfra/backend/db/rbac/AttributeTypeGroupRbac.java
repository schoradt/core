package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

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
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo,
			Locale locale,
			UUID attributeTypeGroupId) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeTypeGroupDao(
				currentProjectId,
				schema).readSubGroups(locale, attributeTypeGroupId);
	}
	
	public AttributeTypeGroupPojo newAttributeTypeGroup(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo,Locale locale) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeTypeGroupDao(
				currentProjectId, schema).newAttributeTypeGroup(locale);
	}

}
