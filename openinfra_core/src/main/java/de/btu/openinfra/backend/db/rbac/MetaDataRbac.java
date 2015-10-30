package de.btu.openinfra.backend.db.rbac;

import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.MetaData;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;

public class MetaDataRbac extends OpenInfraRbac<MetaDataPojo, MetaData,
	MetaDataDao> {

	public MetaDataRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, MetaDataDao.class);
	}

}
