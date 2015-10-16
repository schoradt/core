package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.RoleDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Role;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class RoleRbac extends OpenInfraRbac<RolePojo, Role, RoleDao> {

	public RoleRbac() {
		super(null, OpenInfraSchemas.RBAC, RoleDao.class);
	}

}
