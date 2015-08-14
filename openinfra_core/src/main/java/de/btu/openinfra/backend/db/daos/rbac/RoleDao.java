package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Role;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;

public class RoleDao extends OpenInfraDao<RolePojo, Role> {

	protected RoleDao() {
		super(null, OpenInfraSchemas.RBAC, Role.class);
	}

	@Override
	public RolePojo mapToPojo(Locale locale, Role modelObject) {
		RolePojo pojo = new RolePojo(modelObject);
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<Role> mapToModel(RolePojo pojoObject, Role modelObject) {
		// TODO Auto-generated method stub
		return null;
	}


}