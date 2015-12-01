package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueDao;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomType;
import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeValue;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.pojos.project.AttributeValuePojo;

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
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			UUID attributeValueId,
			AttributeValueGeomType geomType) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeValueDao(
					currentProjectId,
					schema).read(locale, attributeValueId, geomType);

	}

	public UUID distributeTypes(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			Locale locale,
			AttributeValuePojo pojo,
			UUID projectId,
	        UUID attributeValueId) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeValueDao(
				currentProjectId,
				schema).distributeTypes(locale, pojo, projectId,
				        attributeValueId);
	}

	public AttributeValuePojo newAttributeValue(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
	        UUID topicInstanceId,
	        UUID attributeTypeId,
	        Locale locale) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeValueDao(
		        currentProjectId,
				schema).newAttributeValue(
						topicInstanceId, attributeTypeId, locale);
	}
}
