package de.btu.openinfra.backend.db.jpa.model.file;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the files_projects database table.
 *
 */
@Entity
@Table(name="files_projects")
@NamedQueries({
	@NamedQuery(name="FilesProject.findAll",
			query="SELECT f FROM FilesProject f"),
	@NamedQuery(name="FilesProject.findByFileId",
			query="SELECT f FROM "
					+ "FilesProject f "
					+ "WHERE f.fileId = :value "),
	@NamedQuery(name="FilesProject.findByProject",
					query="SELECT f "
							+ "FROM FilesProject f "
							+ "WHERE f.projectId = :value")
})
public class FilesProject extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="file_id")
	private UUID fileId;

	@Column(name="project_id")
	private UUID projectId;

	public FilesProject() {
	}

	public UUID getFileId() {
		return this.fileId;
	}

	public void setFileId(UUID fileId) {
		this.fileId = fileId;
	}

	public UUID getProjectId() {
		return this.projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

}