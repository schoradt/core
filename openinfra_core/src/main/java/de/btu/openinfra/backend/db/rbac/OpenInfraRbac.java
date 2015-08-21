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

/**
 * This class implements the role-based access control layer. Methods of this
 * class are called by rest classes and the call passes a user (subject) 
 * permissions check. When the permissions check is successful, the underlying
 * DAO class is called.
 * 
 * @see OpenInfraDao
 * 
 * Permission examples:
 *  
 * Root permission (access to all rest resources with read, write, update and
 * delete):
 * *:*:*
 *  
 * Permission to access all projects with read, write, update and delete:
 * /projects/{id}:*:* or /projects/{id}:get,post,put,delete:* 
 *  
 * Permission to read all available projects:
 * /projects/{id}:get:*
 *  
 * Permission to read and write available projects (delete isn't allowed):
 * /projects/{id}:get,post,put:*
 *  
 * Permission to read only one project:
 * /projects/{id}:get:e7d42bff-4e40-4f43-9d1b-1dc5a190cd75
 *  
 * Permission to read two projects:
 * /projects/{id}:get:e7d42bff-4e40-4f43-9d1b-1dc5a190cd75,fd27a347-4e33-4ed7-aebc-eeff6dbf1054
 * 
 * These permissions can be extended to secure each resource in detail. For 
 * example you want to secure the following url:
 * /projects/{id}/topiccharacteristics/{id}/topicinstances
 * Each {id} refers to an additional : like so
 * /projects/{id}/topiccharacteristics/{id}/topicinstances:get:{id 1}:{id 2}
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 * @param <TypePojo>
 * @param <TypeModel>
 * @param <TypeDao>
 */
public abstract class OpenInfraRbac<
	TypePojo extends OpenInfraPojo,
	TypeModel extends OpenInfraModelObject,
	TypeDao extends OpenInfraDao<TypePojo, TypeModel>> {
	
	/**
	 * The UUID of the current project required for creating the entity manager.
	 */
	protected UUID currentProjectId;
	
	/**
	 * The referring DAO class.
	 */
	protected Class<TypeDao> dao;

	/**
	 * The currently used schema.
	 */
	protected OpenInfraSchemas schema;
	
	/**
	 * The current user.
	 */
	protected Subject user;
	
	/**
	 * This defines the constructor types in order to call the constructor in a 
	 * generic way via: 
	 * getDeclaredConstructor(constructorTypes).newInstance(constructorTypes)
	 */
	protected Class<?>[] constructorTypes =	
			new Class[] {UUID.class, OpenInfraSchemas.class};
	
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
			return dao.getDeclaredConstructor(constructorTypes).newInstance(
					currentProjectId, 
					schema).read(locale, offset, size);
		} catch(Exception ex) {
			ex.printStackTrace();
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
			return dao.getDeclaredConstructor(constructorTypes).newInstance(
					currentProjectId, 
					schema).read(locale, order, column, offset, size);
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new WebApplicationException(
					ex.getMessage(),
					Response.Status.INTERNAL_SERVER_ERROR);
		}		
	}
	
	public TypePojo read(Locale locale, UUID id) {
		checkPermission();
		try {
			return dao.getDeclaredConstructor(constructorTypes).newInstance(
					currentProjectId, 
					schema).read(locale, id);
		} catch(Exception ex) {
			ex.printStackTrace();
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
