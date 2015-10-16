package de.btu.openinfra.backend.db.rbac.file;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.file.FileDao;
import de.btu.openinfra.backend.db.jpa.model.file.File;
import de.btu.openinfra.backend.db.pojos.file.FilePojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class FileRbac extends OpenInfraRbac<FilePojo, File, FileDao> {

	protected FileRbac() {
		super(null, OpenInfraSchemas.FILE, FileDao.class);
	}

}
