package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Permission;
import de.btu.openinfra.backend.db.pojos.rbac.PermissionPojo;

public class PermissionDao extends OpenInfraDao<PermissionPojo, Permission> {

	public PermissionDao() {
		super(null, OpenInfraSchemas.RBAC, Permission.class);
	}

	@Override
	public PermissionPojo mapToPojo(Locale locale, Permission modelObject) {
		PermissionPojo pojo = new PermissionPojo(modelObject);
		pojo.setDescription(modelObject.getDescription());
		pojo.setPermission(modelObject.getPermission());
		return pojo;
	}

	@Override
	public MappingResult<Permission> mapToModel(PermissionPojo pojoObject,
			Permission modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
