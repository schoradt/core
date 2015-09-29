package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.PermissionDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Permission;
import de.btu.openinfra.backend.db.pojos.rbac.PermissionPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class PermissionRbac extends 
	OpenInfraRbac<PermissionPojo, Permission, PermissionDao> {

	protected PermissionRbac() {
		super(null, OpenInfraSchemas.RBAC, PermissionDao.class);
	}

}
