package de.btu.openinfra.backend.db.rbac;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import de.btu.openinfra.backend.db.OpenInfraOrderByEnum;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.OpenInfraSortOrder;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

public abstract class OpenInfraRbac<
	TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject,
	TypeDao extends OpenInfraDao<TypePojo, TypeModel>> {
	
	/**
	 * The UUID of the current project required for creating the entity manager.
	 */
	protected UUID currentProjectId;
	
	protected Class<TypeDao> dao;

	/**
	 * The currently used schema.
	 */
	protected OpenInfraSchemas schema;
	
	/**
	 * The current user.
	 */
	protected Subject user;
	
	protected OpenInfraRbac(
			UUID currentProjectId,
			OpenInfraSchemas schema,
			Class<TypeDao> dao) {
		this.currentProjectId = currentProjectId;
		this.schema = schema;
		this.dao = dao;
		this.user = SecurityUtils.getSubject();
	}
	
	public List<TypePojo> read(Locale locale, int offset, int size) {
		checkPermission();
		try {
			return dao.newInstance().read(locale, offset, size);
		} catch(Exception ex) {
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<TypePojo> read(
    		Locale locale,
    		OpenInfraSortOrder order,
    		OpenInfraOrderByEnum column,
    		int offset,
    		int size) {
		checkPermission();
		try {
			return dao.newInstance().read(locale, order, column, offset, size);
		} catch(Exception ex) {
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}		
	}
	
	public TypePojo read(Locale locale, UUID id) {
		checkPermission();
		try {
			return dao.newInstance().read(locale, id);
		} catch(Exception ex) {
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	protected void checkPermission() {
		
		if(!user.isAuthenticated()) {
			throw new WebApplicationException(Response.Status.UNAUTHORIZED);
		}
		
		switch (schema) {
		case PROJECTS:
			if(currentProjectId != null && user.isPermitted(
					"/projects/{id}:get:" + currentProjectId)) {
				return;
			}
			break;

		case META_DATA:
			break;
			
		case RBAC:
			break;
			
		case SYSTEM:
			System.out.println("test");
			if(user.isPermitted("/system:get")) {
				return;
			}
			break;
		}
		
		throw new WebApplicationException(Response.Status.FORBIDDEN);
		
	}

}
