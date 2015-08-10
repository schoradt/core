package de.btu.openinfra.backend.db.security;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.daos.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.daos.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraSecurity<
	TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject,
	TypeDao extends OpenInfraDao<TypePojo, TypeModel>> {
	
	/**
	 * The UUID of the current project required for creating the entity manager.
	 */
	protected UUID currentProjectId;
	
	protected OpenInfraDao<TypePojo, TypeModel> dao;

	/**
	 * The currently used schema.
	 */
	protected OpenInfraSchemas schema;
	
	/**
	 * The current user.
	 */
	protected Subject user;
	
	public OpenInfraSecurity(
			UUID currentProjectId,
			OpenInfraSchemas schema,
			OpenInfraDao<TypePojo, TypeModel> dao) {
		this.currentProjectId = currentProjectId;
		this.schema = schema;
		this.dao = dao;
		this.user = SecurityUtils.getSubject();
	}
	
	public List<TypePojo> read(Locale locale, int offset, int size) 
			throws WebApplicationException {
		checkPermission();
		return dao.read(locale, offset, size);
	}
	
	public List<TypePojo> read(
    		Locale locale,
    		OpenInfraSortOrder order,
    		OpenInfraOrderByEnum column,
    		int offset,
    		int size) throws WebApplicationException {
		checkPermission();
		return dao.read(locale, order, column, offset, size);		
	}
	
	public TypePojo read(Locale locale, UUID id) {
		checkPermission();
		return dao.read(locale, id);
	}
	
	protected boolean checkPermission() throws WebApplicationException {
		
		if(!user.isAuthenticated()) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		
		switch (schema) {
		case PROJECTS:
			if(currentProjectId != null && user.isPermitted(
					"/projects/{id}:get:" + currentProjectId)) {
				return true;
			}
			break;

		case META_DATA:
			break;
			
		case SYSTEM:
			if(user.isPermitted("/system:get")) {
				return true;
			}
			break;
		}
		
		throw new WebApplicationException(Response.Status.FORBIDDEN);
		
	}

}
