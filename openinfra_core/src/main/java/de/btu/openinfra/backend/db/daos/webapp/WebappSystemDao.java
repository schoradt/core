package de.btu.openinfra.backend.db.daos.webapp;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.webapp.Webapp;
import de.btu.openinfra.backend.db.jpa.model.webapp.WebappSystem;
import de.btu.openinfra.backend.db.pojos.webapp.WebappSystemPojo;

public class WebappSystemDao
	extends OpenInfraDao<WebappSystemPojo, WebappSystem> {

	protected WebappSystemDao() {
		super(null, OpenInfraSchemas.WEBAPP, WebappSystem.class);
	}

	@Override
	public WebappSystemPojo mapToPojo(Locale locale, WebappSystem modelObject) {
		WebappSystemPojo pojo = new WebappSystemPojo(modelObject);
		pojo.setData(modelObject.getData());
		pojo.setWebapp(modelObject.getWebapp().getId());
		return pojo;
	}

	@Override
	public MappingResult<WebappSystem> mapToModel(WebappSystemPojo pojoObject,
			WebappSystem modelObject) {
		modelObject.setData(pojoObject.getData());
		if(modelObject.getWebapp() == null) {
			modelObject.setWebapp(
					em.find(Webapp.class, pojoObject.getWebapp()));
		}
		return new MappingResult<WebappSystem>(
				modelObject.getId(), modelObject);
	}


}
