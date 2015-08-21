package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.MultiplicityDao;
import de.btu.openinfra.backend.db.jpa.model.Multiplicity;
import de.btu.openinfra.backend.db.pojos.MultiplicityPojo;

public class MultiplicityRbac extends
	OpenInfraRbac<MultiplicityPojo, Multiplicity, MultiplicityDao> {

	public MultiplicityRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, MultiplicityDao.class);
	}

}
