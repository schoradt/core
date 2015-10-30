package de.btu.openinfra.backend.db.rbac.file;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;

import de.btu.openinfra.backend.db.OpenInfraSchemas;
import de.btu.openinfra.backend.db.daos.file.FilesProjectDao;
import de.btu.openinfra.backend.db.jpa.model.file.FilesProject;
import de.btu.openinfra.backend.db.pojos.file.FilesProjectPojo;
import de.btu.openinfra.backend.db.rbac.OpenInfraHttpMethod;
import de.btu.openinfra.backend.db.rbac.OpenInfraRbac;

public class FilesProjectRbac extends OpenInfraRbac<
	FilesProjectPojo, FilesProject, FilesProjectDao> {

	public FilesProjectRbac() {
		super(null, OpenInfraSchemas.FILE, FilesProjectDao.class);
	}

	public List<FilesProjectPojo> readByFileId(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			UUID file) {
		checkPermission(httpMethod, uriInfo);
		return new FilesProjectDao().readByFileId(file);
	}

	public List<FilesProjectPojo> readByProject(
			OpenInfraHttpMethod httpMethod,
			UriInfo uriInfo,
			UUID project) {
		checkPermission(httpMethod, uriInfo);
		return new FilesProjectDao().readByProject(project);
	}

}
