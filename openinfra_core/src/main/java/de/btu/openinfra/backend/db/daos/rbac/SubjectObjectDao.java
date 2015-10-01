package de.btu.openinfra.backend.db.daos.rbac;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraValueDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectObject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectObjectPojo;

public class SubjectObjectDao extends 
	OpenInfraValueDao<SubjectObjectPojo, SubjectObject, Subject> {
	
	public SubjectObjectDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, SubjectObject.class, Subject.class);
	}
	
	public SubjectObjectDao() {
		super(null, OpenInfraSchemas.RBAC, SubjectObject.class, Subject.class);
	}
	
	/**
	 * This class is not an OpenInfraValueValue class. However, it is necessary
	 * to retrieve a list of subject-objects which refer to one project.
	 * 
	 * @param suject the subject as id
	 * @param project the project as id
	 * @return
	 */
	public List<SubjectObjectPojo> read(UUID suject, UUID project) {
		List<SubjectObject> soList = em.createNamedQuery(
				"SubjectObject.findBySubjectAndProject", SubjectObject.class)
				.setParameter("subjectId", suject).setParameter(
						"projectId", project).getResultList();
		List<SubjectObjectPojo> pojos = new LinkedList<SubjectObjectPojo>();
		for(SubjectObject so : soList) {
			pojos.add(mapToPojo(null, so));
		}
		return pojos; 
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
		pojo.setWriteObject(modelObject.getObjectWrite().booleanValue());
		pojo.setObjectId(modelObject.getObjectId());
		return pojo;
	}

	@Override
	public MappingResult<SubjectObject> mapToModel(
			SubjectObjectPojo pojoObject, SubjectObject modelObject) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
