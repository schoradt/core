package de.btu.openinfra.backend.db.rbac;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.MetaDataDao;
import de.btu.openinfra.backend.db.jpa.model.MetaData;
import de.btu.openinfra.backend.db.pojos.MetaDataPojo;
import de.btu.openinfra.backend.exception.OpenInfraWebException;

public class MetaDataRbac extends OpenInfraRbac<MetaDataPojo, MetaData,
	MetaDataDao> {

	public MetaDataRbac(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, MetaDataDao.class);
	}

    public MetaDataPojo read(
            OpenInfraHttpMethod httpMethod,
            UriInfo uriInfo,
            UUID id) {
        checkPermission(httpMethod, uriInfo);
        try {
            return dao.getDeclaredConstructor(constructorTypes).newInstance(
                    currentProjectId,
                    schema).read(id);
        } catch (InstantiationException   | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException |
                 NoSuchMethodException    | SecurityException ex) {
            throw new OpenInfraWebException(ex);
        }
    }

}
