package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

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

	protected PasswordBlacklistDao() {
		super(null, OpenInfraSchemas.RBAC, PasswordBlacklist.class);
	}

	@Override
	public PasswordBlacklistPojo mapToPojo(Locale locale,
			PasswordBlacklist modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MappingResult<PasswordBlacklist> mapToModel(
			PasswordBlacklistPojo pojoObject, PasswordBlacklist modelObject) {
		// TODO Auto-generated method stub
		return null;
	}


}