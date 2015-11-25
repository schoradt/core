package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.AttributeValueGeomDao;
import de.btu.openinfra.backend.db.jpa.model.AttributeValueGeom;
import de.btu.openinfra.backend.db.pojos.project.AttributeValueGeomPojo;

public class AttributeValueGeomRbac extends OpenInfraRbac<
	AttributeValueGeomPojo, AttributeValueGeom, AttributeValueGeomDao> {

	public AttributeValueGeomRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, AttributeValueGeomDao.class);
	}

}
