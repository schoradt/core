package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtFreeTextDao;
import de.btu.openinfra.backend.db.jpa.model.PtFreeText;
import de.btu.openinfra.backend.db.pojos.PtFreeTextPojo;

public class PtFreeTextRbac extends
	OpenInfraRbac<PtFreeTextPojo, PtFreeText, PtFreeTextDao> {

	public PtFreeTextRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, PtFreeTextDao.class);
	}

}
