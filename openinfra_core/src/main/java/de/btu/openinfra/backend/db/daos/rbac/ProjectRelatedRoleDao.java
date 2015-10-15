package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.ProjectRelatedRole;
import de.btu.openinfra.backend.db.pojos.rbac.ProjectRelatedRolePojo;

public class ProjectRelatedRoleDao extends
	OpenInfraDao<ProjectRelatedRolePojo, ProjectRelatedRole> {

	public ProjectRelatedRoleDao() {
		super(null, OpenInfraSchemas.RBAC, ProjectRelatedRole.class);
	}
	
	public ProjectRelatedRoleDao(
			UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, ProjectRelatedRole.class);
	}

	@Override
	public ProjectRelatedRolePojo mapToPojo(Locale locale,
			ProjectRelatedRole modelObject) {
		return mapToPojoStatically(locale, modelObject);
	}
	
	public static ProjectRelatedRolePojo mapToPojoStatically(Locale locale,
			ProjectRelatedRole modelObject) {
		ProjectRelatedRolePojo pojo = new ProjectRelatedRolePojo(modelObject);
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<ProjectRelatedRole> mapToModel(
			ProjectRelatedRolePojo pojoObject, ProjectRelatedRole modelObject) {
		modelObject.setDescription(pojoObject.getDescription());
		modelObject.setName(pojoObject.getName());
		return new MappingResult<ProjectRelatedRole>(
				modelObject.getId(), modelObject);
	}

}
