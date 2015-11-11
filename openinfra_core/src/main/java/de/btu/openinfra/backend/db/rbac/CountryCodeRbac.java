package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.CountryCodeDao;
import de.btu.openinfra.backend.db.jpa.model.CountryCode;
import de.btu.openinfra.backend.db.pojos.CountryCodePojo;

public class CountryCodeRbac extends 
	OpenInfraRbac<CountryCodePojo, CountryCode, CountryCodeDao> {

	public CountryCodeRbac(
			UUID currentProjectId, 
			OpenInfraSchemas schema) {
		super(currentProjectId, schema, CountryCodeDao.class);
	}

}
