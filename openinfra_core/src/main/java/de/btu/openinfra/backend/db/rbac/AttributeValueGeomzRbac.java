package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.project.AttributeValueGeomzDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueGeomz;
import de.btu.openinfra.backend.db.jpa.model.TopicInstance;
import de.btu.openinfra.backend.db.pojos.project.AttributeValueGeomzPojo;

public class AttributeValueGeomzRbac extends 
	OpenInfraValueRbac<AttributeValueGeomzPojo, AttributeValueGeomz,
	TopicInstance, AttributeValueGeomzDao> {

	public AttributeValueGeomzRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, 
				TopicInstance.class, AttributeValueGeomzDao.class);
	}

}
