package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.LanguageCodeDao;
import de.btu.openinfra.backend.db.jpa.model.LanguageCode;
import de.btu.openinfra.backend.db.pojos.LanguageCodePojo;

public class LanguageCodeRbac extends 
	OpenInfraRbac<LanguageCodePojo, LanguageCode, LanguageCodeDao> {

	public LanguageCodeRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, LanguageCodeDao.class);
	}

}
