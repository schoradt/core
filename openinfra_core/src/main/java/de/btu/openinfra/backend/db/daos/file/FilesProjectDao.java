package de.btu.openinfra.backend.db.daos.file;

import java.util.Locale;
import java.util.UUID;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.file.FilesProject;
import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;

public class FilesProjectDao extends
	OpenInfraDao<FilesProjectPojo, FilesProject> {

	public FilesProjectDao() {
		super(null, OpenInfraSchemas.FILE, FilesProject.class);
	}

	public FilesProjectDao(UUID currentProject, OpenInfraSchemas schema) {
		super(currentProject, schema, FilesProject.class);
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
