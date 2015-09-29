package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.CharacterCodeDao;
import de.btu.openinfra.backend.db.jpa.model.CharacterCode;
import de.btu.openinfra.backend.db.pojos.CharacterCodePojo;

public class CharacterCodeRbac extends 
	OpenInfraRbac<CharacterCodePojo, CharacterCode, CharacterCodeDao> {

	public CharacterCodeRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, CharacterCodeDao.class);
	}

}
