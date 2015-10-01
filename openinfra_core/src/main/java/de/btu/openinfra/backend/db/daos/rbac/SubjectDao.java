package de.btu.openinfra.backend.db.daos.rbac;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectProject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectRole;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectProjectPojo;

/**
 * This is the DAO class for users. In this case the map to model method skips
 * some values of the model class. This includes the salt string and the 
 * password sting. These values should not be transferred to the client.
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class SubjectDao extends OpenInfraDao<SubjectPojo, Subject> {

	public SubjectDao() {
		super(null, OpenInfraSchemas.RBAC, Subject.class);
	}
	
	public SubjectDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, Subject.class);
	}
	
	/**
	 * This method reads a RBAC model object by login from database. This is
	 * required for Apache Shiro login. 
	 * @param login the login name
	 * @return the subject (user) as model object
	 */
	public Subject readModel(String login) {
		try {
			return em.createNamedQuery(
					"Subject.findByLogin",
					Subject.class).setParameter(
							"login",
							login).getSingleResult();			
		} catch(Exception ex) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	public SubjectPojo read(String login) {
		return mapToPojo(null, readModel(login));
	}

	@Override
	public SubjectPojo mapToPojo(Locale locale, Subject modelObject) {
		return mapToPojoStatically(locale, modelObject);
	}
	
	public static SubjectPojo mapToPojoStatically(
			Locale locale, Subject modelObject) {
		SubjectPojo pojo = new SubjectPojo(modelObject);
		pojo.setCreatedOn(modelObject.getCreatedOn());
		pojo.setDefaultLanguage(PtLocaleDao.forLanguageTag(
				modelObject.getDefaultLanguage()));
		pojo.setDescription(modelObject.getDescription());
		pojo.setLastLoginOn(modelObject.getLastLoginOn());
		pojo.setLogin(modelObject.getLogin());
		pojo.setMail(pojo.getMail());
		pojo.setName(modelObject.getName());
		pojo.setPasswordCreatedOn(modelObject.getPasswordCreatedOn());
		pojo.setStatus(modelObject.getStatus());
		pojo.setUpdatedOn(modelObject.getUpdatedOn());
		
		List<SubjectProjectPojo> spList = new LinkedList<SubjectProjectPojo>();
		for(SubjectProject sp : modelObject.getSubjectProjects()) {
			spList.add(SubjectProjectDao.mapToPojoStatically(locale, sp));
		}
		pojo.setProjects(spList);
		
		List<RolePojo> roles = new LinkedList<RolePojo>();
		RoleDao dao = new RoleDao();		
		for(SubjectRole ur : modelObject.getSubjectRoles()) {
			roles.add(dao.mapToPojo(locale, ur.getRoleBean()));
		}
		
		pojo.setRoles(roles);
		
		return pojo;		
	}

	@Override
	public MappingResult<Subject> mapToModel(SubjectPojo pojoObject, Subject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}



}