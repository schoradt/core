package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.RolePermissionDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.RolePermission;
import de.btu.openinfra.backend.db.pojos.rbac.RolePermissionPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class RolePermissionRbac extends 
	OpenInfraRbac<RolePermissionPojo, RolePermission, RolePermissionDao> {

	public RolePermissionRbac() {
		super(null, OpenInfraSchemas.RBAC, RolePermissionDao.class);
	}

}
