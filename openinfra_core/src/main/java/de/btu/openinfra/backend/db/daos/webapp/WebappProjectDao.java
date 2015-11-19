package de.btu.openinfra.backend.db.daos.webapp;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.webapp.WebappProject;
import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;

public class WebappProjectDao extends
	OpenInfraDao<WebappProjectPojo, WebappProject> {

	public WebappProjectDao() {
		super(null, OpenInfraSchemas.WEBAPP, WebappProject.class);
	}

	@Override
	public WebappProjectPojo mapToPojo(
			Locale locale, WebappProject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MappingResult<WebappProject> mapToModel(
			WebappProjectPojo pojoObject, WebappProject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
