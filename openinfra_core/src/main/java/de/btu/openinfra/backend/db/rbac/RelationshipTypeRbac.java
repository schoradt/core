package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.RelationshipTypeDao;
import de.btu.openinfra.backend.db.jpa.model.RelationshipType;
import de.btu.openinfra.backend.db.jpa.model.TopicCharacteristic;
import de.btu.openinfra.backend.db.pojos.RelationshipTypePojo;

public class RelationshipTypeRbac extends 
	OpenInfraValueRbac<RelationshipTypePojo, RelationshipType, 
	TopicCharacteristic, RelationshipTypeDao> {

	public RelationshipTypeRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, 
				TopicCharacteristic.class, RelationshipTypeDao.class);
	}

}
