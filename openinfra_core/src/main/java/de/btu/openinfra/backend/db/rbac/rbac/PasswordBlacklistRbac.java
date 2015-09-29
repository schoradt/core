package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.PasswordBlacklistDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.PasswordBlacklist;
import de.btu.openinfra.backend.db.pojos.rbac.PasswordBlacklistPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class PasswordBlacklistRbac extends OpenInfraRbac<
	PasswordBlacklistPojo, PasswordBlacklist, PasswordBlacklistDao> {

	protected PasswordBlacklistRbac() {
		super(null, OpenInfraSchemas.RBAC, PasswordBlacklistDao.class);
	}

}
