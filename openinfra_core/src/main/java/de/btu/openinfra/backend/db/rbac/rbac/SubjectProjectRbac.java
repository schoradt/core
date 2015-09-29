package de.btu.openinfra.backend.db.rbac.rbac;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.SubjectProjectDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectProject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectProjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SubjectProjectRbac extends OpenInfraRbac<
	SubjectProjectPojo, SubjectProject, SubjectProjectDao>{

	protected SubjectProjectRbac() {
		super(null, OpenInfraSchemas.RBAC, SubjectProjectDao.class);
	}

}
