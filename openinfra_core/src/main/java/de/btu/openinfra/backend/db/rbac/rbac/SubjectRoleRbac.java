package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.SubjectRoleDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectRole;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectRolePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SubjectRoleRbac extends 
	OpenInfraRbac<SubjectRolePojo, SubjectRole, SubjectRoleDao> {

	protected SubjectRoleRbac() {
		super(null, OpenInfraSchemas.RBAC, SubjectRoleDao.class);
	}

}
