package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectObject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectObjectPojo;

public class SubjectObjectDao extends 
	OpenInfraDao<SubjectObjectPojo, SubjectObject> {

	public SubjectObjectDao() {
		super(null, OpenInfraSchemas.RBAC, SubjectObject.class);
	}

	@Override
	public SubjectObjectPojo mapToPojo(
			Locale locale, SubjectObject modelObject) {
		SubjectObjectPojo pojo = new SubjectObjectPojo(modelObject);
		pojo.setObject(OpenInfraObjectDao.mapToPojoStatically(
				locale, modelObject.getObjectBean()));
		pojo.setProjectId(modelObject.getProjectId());
		pojo.setSubject(SubjectDao.mapToPojoStatically(
				locale, modelObject.getSubjectBean()));
		return pojo;
	}

	@Override
	public MappingResult<SubjectObject> mapToModel(
			SubjectObjectPojo pojoObject, SubjectObject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
