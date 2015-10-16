package de.btu.openinfra.backend.db.daos.rbac;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Role;
import de.btu.openinfra.backend.db.jpa.model.rbac.RolePermission;
import de.btu.openinfra.backend.db.pojos.rbac.PermissionPojo;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;

public class RoleDao extends OpenInfraDao<RolePojo, Role> {

	public RoleDao() {
		super(null, OpenInfraSchemas.RBAC, Role.class);
	}
	
	public RoleDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, Role.class);
	}

	@Override
	public RolePojo mapToPojo(Locale locale, Role modelObject) {
		RolePojo pojo = new RolePojo(modelObject);
		List<PermissionPojo> permissions = new LinkedList<PermissionPojo>();
		for(RolePermission rp : modelObject.getRolePermissions()) {
			permissions.add(new PermissionDao().mapToPojo(
					null, 
					rp.getPermissionBean()));
		}
		pojo.setPermissions(permissions);
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<Role> mapToModel(
			RolePojo pojoObject, Role modelObject) {
		modelObject.setName(pojoObject.getName());
		modelObject.setDescription(pojoObject.getDescription());
		return new MappingResult<Role>(modelObject.getId(), modelObject);
	}

}