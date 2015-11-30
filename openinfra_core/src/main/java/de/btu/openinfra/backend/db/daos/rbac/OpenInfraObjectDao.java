package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.OpenInfraObject;
import de.btu.openinfra.backend.db.pojos.rbac.OpenInfraObjectPojo;

public class OpenInfraObjectDao
	extends OpenInfraDao<OpenInfraObjectPojo, OpenInfraObject> {

	public OpenInfraObjectDao() {
		super(null, OpenInfraSchemas.RBAC, OpenInfraObject.class);
	}

	public OpenInfraObjectDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, OpenInfraObject.class);
	}

	@Override
	public OpenInfraObjectPojo mapToPojo(
			Locale locale, OpenInfraObject modelObject) {
		OpenInfraObjectPojo pojo = new OpenInfraObjectPojo(modelObject);
		pojo.setDescription(modelObject.getDescription());
		pojo.setName(modelObject.getName());
		return pojo;
	}

	@Override
	public MappingResult<OpenInfraObject> mapToModel(
			OpenInfraObjectPojo pojoObject, OpenInfraObject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

}
