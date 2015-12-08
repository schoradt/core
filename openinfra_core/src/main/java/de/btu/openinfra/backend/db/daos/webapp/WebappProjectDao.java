package de.btu.openinfra.backend.db.daos.webapp;

import java.util.Locale;
import java.util.UUID;

import javax.persistence.NoResultException;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.webapp.Webapp;
import de.btu.openinfra.backend.db.jpa.model.webapp.WebappProject;
import de.btu.openinfra.backend.db.pojos.webapp.WebappProjectPojo;

public class WebappProjectDao
	extends OpenInfraDao<WebappProjectPojo, WebappProject> {

	public WebappProjectDao() {
		super(null, OpenInfraSchemas.WEBAPP, WebappProject.class);
	}

	public WebappProjectPojo read(UUID webappId, UUID projectId) {
		try {
			return mapToPojo(null, em.createNamedQuery(
					"WebappProject.findByWebappAndProject", WebappProject.class)
					.setParameter("webapp", em.find(Webapp.class, webappId))
					.setParameter("projectId", projectId)
					.getSingleResult());
		} catch(NoResultException ex) {
			return null;
		}
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
		modelObject.setData(pojoObject.getData());
		if(modelObject.getWebapp() == null) {
			modelObject.setWebapp(
					em.find(Webapp.class, pojoObject.getWebapp()));
		}
		return new MappingResult<WebappProject>(
				modelObject.getId(), modelObject);
	}
}
