package de.btu.openinfra.backend.db.daos.file;

import java.util.Locale;

import de.btu.openinfra.backend.OpenInfraTime;
import de.btu.openinfra.backend.db.MappingResult;
import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.OpenInfraDao;
import de.btu.openinfra.backend.db.jpa.model.file.File;
import de.btu.openinfra.backend.db.pojos.file.FilePojo;

public class FileDao extends OpenInfraDao<FilePojo, File> {

	protected FileDao() {
		super(null, OpenInfraSchemas.FILE, File.class);
	}

	@Override
	public FilePojo mapToPojo(Locale locale, File modelObject) {
		FilePojo pojo = new FilePojo(modelObject);
		pojo.setMimeType(modelObject.getMimeType());
		pojo.setOriginFileName(modelObject.getOriginFileName());
		pojo.setSubject(modelObject.getSubject());
		pojo.setUploadedOn(OpenInfraTime.format(modelObject.getUploadedOn()));
		return pojo;
	}

	@Override
	public MappingResult<File> mapToModel(
			FilePojo pojoObject, File modelObject) {
		modelObject.setMimeType(pojoObject.getMimeType());
		modelObject.setOriginFileName(modelObject.getOriginFileName());
		modelObject.setSubject(pojoObject.getSubject());
		modelObject.setUploadedOn(OpenInfraTime.now());
		return null;
	}
	
	

}
