package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.RolePermission;
import de.btu.openinfra.backend.db.pojos.rbac.RolePermissionPojo;

public class RolePermissionDao extends 
	OpenInfraDao<RolePermissionPojo, RolePermission> {

	protected RolePermissionDao() {
		super(null, OpenInfraSchemas.RBAC, RolePermission.class);
	}

	@Override
	public RolePermissionPojo mapToPojo(
			Locale locale,
			RolePermission modelObject) {
		RolePermissionPojo pojo = new RolePermissionPojo(modelObject);
		pojo.setPermission(
				new PermissionDao().mapToPojo(
						locale, 
						modelObject.getPermissionBean()));
		pojo.setRole(new RoleDao().mapToPojo(
				locale, 
				modelObject.getRoleBean()));
		return pojo;
	}

	@Override
	public MappingResult<RolePermission> mapToModel(
			RolePermissionPojo pojoObject, RolePermission modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

}