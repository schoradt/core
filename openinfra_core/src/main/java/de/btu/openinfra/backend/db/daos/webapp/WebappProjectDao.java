package de.btu.openinfra.backend.db.daos.webapp;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraValueDao;
import de.btu.openinfra.backend.db.jpa.model.webapp.Webapp;
import de.btu.openinfra.backend.db.jpa.model.webapp.WebappProject;
import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;

public class WebappProjectDao
	extends OpenInfraValueDao<WebappProjectPojo, WebappProject, Webapp> {

	public WebappProjectDao() {
		super(null, OpenInfraSchemas.WEBAPP, WebappProject.class, Webapp.class);
	}

	@Override
	public WebappProjectPojo mapToPojo(
			Locale locale, WebappProject modelObject) {
		WebappProjectPojo pojo = new WebappProjectPojo(modelObject);
		pojo.setData(modelObject.getData());
		pojo.setProject(modelObject.getProjectId());
		pojo.setWebapp(modelObject.getWebapp().getId());
		return pojo;
	}

	@Override
	public MappingResult<WebappProject> mapToModel(
			WebappProjectPojo pojoObject, WebappProject modelObject) {
		modelObject.setProjectId(pojoObject.getProject());
		modelObject.setData(modelObject.getData());
		if(modelObject.getWebapp() == null) {
			modelObject.setWebapp(
					em.find(Webapp.class, pojoObject.getWebapp()));
		}
		return new MappingResult<WebappProject>(
				modelObject.getId(), modelObject);
	}
}
