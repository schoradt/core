package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Role;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectRole;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectRolePojo;


public class SubjectRoleDao extends OpenInfraDao<SubjectRolePojo, SubjectRole> {

	public SubjectRoleDao() {
		super(null, OpenInfraSchemas.RBAC, SubjectRole.class);
	}
	
	public SubjectRoleDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, SubjectRole.class);
	}

	@Override
	public SubjectRolePojo mapToPojo(Locale locale, SubjectRole modelObject) {
		SubjectRolePojo pojo = new SubjectRolePojo(modelObject);
		pojo.setRole(modelObject.getRoleBean().getId());
		pojo.setSubject(modelObject.getSubjectBean().getId());
		return pojo;
	}

	@Override
	public MappingResult<SubjectRole> mapToModel(SubjectRolePojo pojoObject,
			SubjectRole modelObject) {
		modelObject.setRoleBean(em.find(Role.class, pojoObject.getRole()));
		modelObject.setSubjectBean(
				em.find(Subject.class, pojoObject.getSubject()));
		return new MappingResult<SubjectRole>(modelObject.getId(), modelObject);
	}


}