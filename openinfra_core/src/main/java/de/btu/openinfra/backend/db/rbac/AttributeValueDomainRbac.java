package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueDomainDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueDomain;
import de.btu.openinfra.backend.db.pojos.AttributeValueDomainPojo;

public class AttributeValueDomainRbac extends OpenInfraRbac<
	AttributeValueDomainPojo, AttributeValueDomain, AttributeValueDomainDao> {

	public AttributeValueDomainRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeValueDomainDao.class);
	}
	
}
