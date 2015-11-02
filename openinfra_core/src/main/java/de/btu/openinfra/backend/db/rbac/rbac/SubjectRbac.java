package de.btu.openinfra.backend.db.rbac.rbac;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.rbac.SubjectDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class SubjectRbac extends OpenInfraRbac<
	SubjectPojo, Subject, SubjectDao>{

	public SubjectRbac() {
		super(null, OpenInfraSchemas.RBAC, SubjectDao.class);
	}

	public SubjectPojo read(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			String login) {
		checkPermission(httpMethod, uriInfo);
		return new SubjectDao().read(login);
	}

}
