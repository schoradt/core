package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.PasswordBlacklist;
import de.btu.openinfra.backend.db.pojos.rbac.PasswordBlacklistPojo;

/**
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class PasswordBlacklistDao extends 
	OpenInfraDao<PasswordBlacklistPojo, PasswordBlacklist> {

	public PasswordBlacklistDao() {
		super(null, OpenInfraSchemas.RBAC, PasswordBlacklist.class);
	}
	
	public PasswordBlacklistDao(
			UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, PasswordBlacklist.class);
	}

	@Override
	public PasswordBlacklistPojo mapToPojo(
			Locale locale,
			PasswordBlacklist modelObject) {
		
		PasswordBlacklistPojo pojo = new PasswordBlacklistPojo(modelObject);
		pojo.setPassword(modelObject.getPassword());
		return pojo;
	}

	@Override
	public MappingResult<PasswordBlacklist> mapToModel(
			PasswordBlacklistPojo pojoObject, PasswordBlacklist modelObject) {
		modelObject.setPassword(pojoObject.getPassword());
		return new MappingResult<PasswordBlacklist>(
				modelObject.getId(), modelObject);
	}


}