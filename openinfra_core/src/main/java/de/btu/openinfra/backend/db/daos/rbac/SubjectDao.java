package de.btu.openinfra.backend.db.daos.rbac;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.daos.PtLocaleDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectRole;
import de.btu.openinfra.backend.db.pojos.rbac.RolePojo;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectPojo;

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
	
	public Subject read(String name) {
		List<Subject> subjects = em.createNamedQuery(
				"Subject.findByLogin",
				Subject.class).setParameter(0, name).getResultList();
		if(subjects.size() > 0) {
			System.out.println("true ");
			return subjects.get(0);
		}
		return null;
	}

	@Override
	public SubjectPojo mapToPojo(Locale locale, Subject modelObject) {
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