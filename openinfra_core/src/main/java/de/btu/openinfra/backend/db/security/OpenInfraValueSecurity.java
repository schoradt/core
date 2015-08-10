package de.btu.openinfra.backend.db.security;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;

import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.OpenInfraValueDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraValueSecurity<
		TypePojo extends OpenInfraPojo,
		TypeModel extends OpenInfraModelObject,
		TypeModelValue,
		TypeDao extends 
			OpenInfraValueDao<TypePojo, TypeModel, TypeModelValue>> 
		extends OpenInfraSecurity<TypePojo, TypeModel, 
			OpenInfraDao<TypePojo,TypeModel>> {
	
	
	protected OpenInfraValueSecurity(
			UUID currentProjectId,
			OpenInfraSchemas schema, 
			OpenInfraDao<TypePojo, TypeModel> dao) {
		super(currentProjectId, schema, dao);
	}

	public List<TypePojo> read(
			Locale locale,
			UUID valueId,
			int offset,
			int size) throws WebApplicationException {
		return null;
	}
	
	public List<TypePojo> read(
            Locale locale,
            UUID valueId,
            OpenInfraSortOrder order,
            OpenInfraOrderBy column,
            int offset,
            int size) throws WebApplicationException {
		return null;
	}
	
	

}
