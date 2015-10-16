package de.btu.openinfra.backend.db.daos.file;

import java.util.Locale;

import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.file.FilesProject;
import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;

public class FilesProjectDao extends 
	OpenInfraDao<FilesProjectPojo, FilesProject> {

	protected FilesProjectDao() {
		super(null, OpenInfraSchemas.FILE, FilesProject.class);
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
