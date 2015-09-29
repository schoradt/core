package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.OpenInfraObjectDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.OpenInfraObject;
import de.btu.openinfra.backend.db.pojos.rbac.OpenInfraObjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class OpenInfraObjectRbac extends OpenInfraRbac<
	OpenInfraObjectPojo, OpenInfraObject, OpenInfraObjectDao>{

	protected OpenInfraObjectRbac() {
		super(null, OpenInfraSchemas.RBAC, OpenInfraObjectDao.class);
	}

}
