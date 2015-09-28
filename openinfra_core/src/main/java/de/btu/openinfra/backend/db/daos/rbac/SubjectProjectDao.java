package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectProject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectProjectPojo;

public class SubjectProjectDao 
	extends OpenInfraDao<SubjectProjectPojo, SubjectProject> {

	public SubjectProjectDao() {
		super(null, OpenInfraSchemas.RBAC, SubjectProject.class);
	}

	@Override
	public SubjectProjectPojo mapToPojo(Locale locale,
			SubjectProject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static SubjectProjectPojo mapToPojoStatically(
			Locale locale,
			SubjectProject modelObject) {
		SubjectProjectPojo pojo = new SubjectProjectPojo(modelObject);
		pojo.setProjectId(modelObject.getProjectId());
		pojo.setSubject(
				SubjectDao.mapToPojoStatically(
						locale, modelObject.getSubjectBean()));
		pojo.setProjectRelatedRole(
				ProjectRelatedRoleDao.mapToPojoStatically(
						locale, 
						modelObject.getProjectRelatedRoleBean()));
		return pojo;
	}

	@Override
	public MappingResult<SubjectProject> mapToModel(
			SubjectProjectPojo pojoObject, SubjectProject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
