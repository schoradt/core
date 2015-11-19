package de.btu.openinfra.backend.db.daos.webapp;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.webapp.Webapp;
import de.btu.openinfra.backend.db.pojos.webapp.WebappPojo;

public class WebappDao extends OpenInfraDao<WebappPojo, Webapp> {

	public WebappDao() {
		super(null, OpenInfraSchemas.WEBAPP, Webapp.class);
	}

	@Override
	public WebappPojo mapToPojo(Locale locale, Webapp modelObject) {
		WebappPojo pojo = new WebappPojo(modelObject);
		pojo.setData(modelObject.getData());
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<Webapp> mapToModel(WebappPojo pojoObject,
			Webapp modelObject) {
		modelObject.setData(pojoObject.getData());
		modelObject.setDescription(pojoObject.getDescription());
		modelObject.setName(pojoObject.getName());
		return new MappingResult<Webapp>(modelObject.getId(), modelObject);
	}

}
