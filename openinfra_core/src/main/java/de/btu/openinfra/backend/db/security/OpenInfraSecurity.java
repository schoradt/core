package de.btu.openinfra.backend.db.security;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraSecurity<TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject> {
	
	/**
	 * The UUID of the current project required for creating the entity manager.
	 */
	protected UUID currentProjectId;

	/**
	 * This variable defines the underlying JPA model class.
	 */
	protected Class<TypeModel> modelClass;

	/**
	 * The currently used schema.
	 */
	protected OpenInfraSchemas schema;
	
	public OpenInfraSecurity(
			UUID currentProjectId,
			OpenInfraSchemas schema,
			Class<TypeModel> modelClass) {
		this.currentProjectId = currentProjectId;
		this.schema = schema;
		this.modelClass = modelClass;
	}
	
	public List<TypePojo> read(Locale locale, int offset, int size) {
		switch (schema) {
		case PROJECTS:
			
			break;
		case META_DATA:
			break;
			
		case SYSTEM:
			// fall through
		default:
			break;
		}
		return null;
	}
	
	public List<TypePojo> read(
    		Locale locale,
    		OpenInfraSortOrder order,
    		OpenInfraOrderByEnum column,
    		int offset,
    		int size) {
		return null;
	}

}
