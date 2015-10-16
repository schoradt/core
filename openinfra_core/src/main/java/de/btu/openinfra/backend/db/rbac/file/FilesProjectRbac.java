package de.btu.openinfra.backend.db.rbac.file;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.file.FilesProjectDao;
import de.btu.openinfra.backend.db.jpa.model.file.FilesProject;
import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class FilesProjectRbac extends OpenInfraRbac<
	FilesProjectPojo, FilesProject, FilesProjectDao> {

	protected FilesProjectRbac() {
		super(null, OpenInfraSchemas.FILE, FilesProjectDao.class);
	}

}
