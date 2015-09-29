package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.ProjectRelatedRoleDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.ProjectRelatedRole;
import de.btu.openinfra.backend.db.pojos.rbac.ProjectRelatedRolePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class ProjectRelatedRoleRbac extends 
	OpenInfraRbac<ProjectRelatedRolePojo, ProjectRelatedRole, 
	ProjectRelatedRoleDao> {

	protected ProjectRelatedRoleRbac() {
		super(null, OpenInfraSchemas.RBAC, ProjectRelatedRoleDao.class);
	}

}
