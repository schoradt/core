package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.SubjectObjectDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectObject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectObjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SubjectObjectRbac extends OpenInfraRbac<
	SubjectObjectPojo, SubjectObject, SubjectObjectDao>{

	public SubjectObjectRbac() {
		super(null, OpenInfraSchemas.RBAC, SubjectObjectDao.class);
	}
	

}
