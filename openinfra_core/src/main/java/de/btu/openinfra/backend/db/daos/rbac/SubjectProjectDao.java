package de.btu.openinfra.backend.db.daos.rbac;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.daos.ProjectDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.ProjectRelatedRole;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectProject;
import de.btu.openinfra.backend.db.pojos.rbac.SubjectProjectPojo;

public class SubjectProjectDao
	extends OpenInfraDao<SubjectProjectPojo, SubjectProject> {

	public SubjectProjectDao() {
		super(null, OpenInfraSchemas.RBAC, SubjectProject.class);
	}

	public SubjectProjectDao(UUID currentProjectId, OpenInfraSchemas schema) {
		super(currentProjectId, schema, SubjectProject.class);
	}

	@Override
	public SubjectProjectPojo mapToPojo(Locale locale,
			SubjectProject modelObject) {
		SubjectProjectPojo pojo = new SubjectProjectPojo(modelObject);
		pojo.setProjectId(modelObject.getProjectId());
		pojo.setProjectNames(
				new ProjectDao(
						modelObject.getProjectId(),
						OpenInfraSchemas.PROJECTS).read(
								locale, modelObject.getProjectId()).getNames());
		pojo.setSubject(modelObject.getSubjectBean().getId());
		pojo.setProjectRelatedRole(
				modelObject.getProjectRelatedRoleBean().getId());
		return pojo;
	}

	@Override
	public MappingResult<SubjectProject> mapToModel(
			SubjectProjectPojo pojoObject, SubjectProject modelObject) {
		modelObject.setProjectId(pojoObject.getProjectId());
		modelObject.setProjectRelatedRoleBean(
				em.find(ProjectRelatedRole.class,
						pojoObject.getProjectRelatedRole()));
		modelObject.setSubjectBean(
				em.find(Subject.class, pojoObject.getSubject()));
		return new MappingResult<SubjectProject>(
				modelObject.getId(), modelObject);
	}

}
