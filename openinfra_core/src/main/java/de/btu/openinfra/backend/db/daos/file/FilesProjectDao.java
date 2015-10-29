package de.btu.openinfra.backend.db.daos.file;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.file.FilesProject;
import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;

public class FilesProjectDao extends
	OpenInfraDao<FilesProjectPojo, FilesProject> {

	public FilesProjectDao(UUID currentProject, OpenInfraSchemas schema) {
		super(currentProject, schema, FilesProject.class);
	}

	public FilesProjectDao() {
		super(null, OpenInfraSchemas.FILE, FilesProject.class);
	}

	public List<FilesProjectPojo> readByFileId(UUID file) {
		return readByValue("FilesProject.findByFileId", file);
	}

	public List<FilesProjectPojo> readByProject(UUID project) {
		return readByValue("FilesProject.findByProject", project);
	}

	private List<FilesProjectPojo> readByValue(String namedQuery, UUID value) {
		List<FilesProject> fps =
				em.createNamedQuery(namedQuery,
						FilesProject.class).setParameter("value", value)
						.getResultList();
		List<FilesProjectPojo> pojos = new LinkedList<FilesProjectPojo>();
		for(FilesProject fp : fps) {
			pojos.add(mapToPojo(null, fp));
		}
		return pojos;
	}

	@Override
	public FilesProjectPojo mapToPojo(Locale locale, FilesProject modelObject) {
		FilesProjectPojo pojo = new FilesProjectPojo(modelObject);
		pojo.setProject(modelObject.getProjectId());
		pojo.setFile(modelObject.getFileId());
		return pojo;
	}

	@Override
	public MappingResult<FilesProject> mapToModel(FilesProjectPojo pojoObject,
			FilesProject modelObject) {
		modelObject.setFileId(pojoObject.getFile());
		modelObject.setProjectId(pojoObject.getProject());
		return new MappingResult<FilesProject>(
				modelObject.getId(), modelObject);
	}

}
