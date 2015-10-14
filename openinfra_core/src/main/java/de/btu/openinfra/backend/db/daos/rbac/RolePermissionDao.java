package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Permission;
import de.btu.openinfra.backend.db.jpa.model.rbac.Role;
import de.btu.openinfra.backend.db.jpa.model.rbac.RolePermission;
import de.btu.openinfra.backend.db.pojos.rbac.RolePermissionPojo;

public class RolePermissionDao extends 
	OpenInfraDao<RolePermissionPojo, RolePermission> {

	public RolePermissionDao() {
		super(null, OpenInfraSchemas.RBAC, RolePermission.class);
	}
	
	public RolePermissionDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, RolePermission.class);
	}

	@Override
	public RolePermissionPojo mapToPojo(
			Locale locale,
			RolePermission modelObject) {
		RolePermissionPojo pojo = new RolePermissionPojo(modelObject);
		pojo.setPermission(modelObject.getPermissionBean().getId());
		pojo.setRole(modelObject.getRoleBean().getId());
		return pojo;
	}

	@Override
	public MappingResult<RolePermission> mapToModel(
			RolePermissionPojo pojoObject, RolePermission modelObject) {
		modelObject.setPermissionBean(
				em.find(Permission.class, pojoObject.getPermission()));
		modelObject.setRoleBean(em.find(Role.class, pojoObject.getRole()));
		return new MappingResult<RolePermission>(
				modelObject.getId(), modelObject);
	}

}