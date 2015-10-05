package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
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
		pojo.setRole(
				new RoleDao().mapToPojo(locale, modelObject.getRoleBean()));
		pojo.setSubject(
				new SubjectDao().mapToPojo(
						locale, modelObject.getSubjectBean()));
		return pojo;
	}

	@Override
	public MappingResult<SubjectRole> mapToModel(SubjectRolePojo pojoObject,
			SubjectRole modelObject) {
		// TODO Auto-generated method stub
		return null;
	}


}