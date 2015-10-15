package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeTypeAssociationDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeType;
import de.btu.openinfra.backend.db.jpa.model.AttributeTypeXAttributeType;
import de.btu.openinfra.backend.db.pojos.AttributeTypeAssociationPojo;

public class AttributeTypeAssociationRbac extends OpenInfraValueValueRbac<
	AttributeTypeAssociationPojo, AttributeTypeXAttributeType, AttributeType,
	AttributeType, AttributeTypeAssociationDao> {

	public AttributeTypeAssociationRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId,	schema, AttributeType.class,
				AttributeType.class, AttributeTypeAssociationDao.class);
	}

	public AttributeTypeAssociationPojo newAttributeTypeAssociation(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			UUID attributeTypeId) {
		checkPermission(httpMethod, uriInfo);
		return new AttributeTypeAssociationDao(
		        currentProjectId,
				schema).newAttributeTypeAssociation(attributeTypeId);
	}

}
