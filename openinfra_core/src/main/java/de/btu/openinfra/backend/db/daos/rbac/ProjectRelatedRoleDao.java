package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.ProjectRelatedRole;
import de.btu.openinfra.backend.db.pojos.rbac.ProjectRelatedRolePojo;

public class ProjectRelatedRoleDao extends
	OpenInfraDao<ProjectRelatedRolePojo, ProjectRelatedRole> {

	protected ProjectRelatedRoleDao() {
		super(null, OpenInfraSchemas.RBAC, ProjectRelatedRole.class);
	}

	@Override
	public ProjectRelatedRolePojo mapToPojo(Locale locale,
			ProjectRelatedRole modelObject) {
		ProjectRelatedRolePojo pojo = new ProjectRelatedRolePojo(modelObject);
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<ProjectRelatedRole> mapToModel(
			ProjectRelatedRolePojo pojoObject, ProjectRelatedRole modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
