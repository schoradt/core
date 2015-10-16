package de.btu.openinfra.backend.db.rbac;

import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.jpa.model.PtLocale;
import de.btu.openinfra.backend.db.pojos.PtLocalePojo;

public class PtLocaleRbac extends 
	OpenInfraRbac<PtLocalePojo, PtLocale, PtLocaleDao>{

	public PtLocaleRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, PtLocaleDao.class);
	}
	
	public PtLocale read(
			OpenInfraHttpMethod httpMethod, 
			UriInfo uriInfo, Locale locale) {
		checkPermission(httpMethod, uriInfo);
		return new PtLocaleDao(currentProjectId, schema).read(locale);
	}

}
