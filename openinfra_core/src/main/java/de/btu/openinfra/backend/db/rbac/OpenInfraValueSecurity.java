package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.OpenInfraOrderBy;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.OpenInfraValueDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraValueSecurity<
		TypePojo extends OpenInfraPojo,
		TypeModel extends OpenInfraModelObject,
		TypeModelValue,
		TypeDao extends OpenInfraValueDao<TypePojo, TypeModel, TypeModelValue>> 
		extends OpenInfraSecurity<TypePojo, TypeModel, TypeDao> {

	protected OpenInfraValueSecurity(
			UUID currentProjectId,
			OpenInfraSchemas schema, 
			Class<TypeDao> dao) {
		super(currentProjectId, schema, dao);
	}

	public List<TypePojo> read(
			Locale locale,
			UUID valueId,
			int offset,
			int size) {
		return null;
	}
	
	public List<TypePojo> read(
            Locale locale,
            UUID valueId,
            OpenInfraSortOrder order,
            OpenInfraOrderBy column,
            int offset,
            int size) {
		return null;
	}
	
	

}
