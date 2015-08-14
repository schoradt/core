package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.UserRole;
import de.btu.openinfra.backend.db.pojos.rbac.UserRolePojo;


public class UserRoleDao extends OpenInfraDao<UserRolePojo, UserRole> {

	protected UserRoleDao() {
		super(null, OpenInfraSchemas.RBAC, UserRole.class);
	}

	@Override
	public UserRolePojo mapToPojo(Locale locale, UserRole modelObject) {
		UserRolePojo pojo = new UserRolePojo(modelObject);
		pojo.setRole(
				new RoleDao().mapToPojo(locale, modelObject.getRoleBean()));
		pojo.setUser(
				new UserDao().mapToPojo(locale, modelObject.getUserBean()));
		return pojo;
	}

	@Override
	public MappingResult<UserRole> mapToModel(UserRolePojo pojoObject,
			UserRole modelObject) {
		// TODO Auto-generated method stub
		return null;
	}


}