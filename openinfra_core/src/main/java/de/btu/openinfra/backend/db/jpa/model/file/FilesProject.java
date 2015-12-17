package de.btu.openinfra.backend.db.jpa.model.file;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
			query="SELECT f FROM FilesProject f ORDER BY f.id"),
	@NamedQuery(name="FilesProject.countByProject",
			query="SELECT COUNT(f) FROM "
					+ "FilesProject f "
					+ "WHERE f.projectId = :value "),
	@NamedQuery(name="FilesProject.findByFile",
			query="SELECT f FROM "
					+ "FilesProject f "
					+ "WHERE f.file = :value ORDER BY f.id"),
	@NamedQuery(name="FilesProject.findByProject",
					query="SELECT f "
							+ "FROM FilesProject f "
							+ "WHERE f.projectId = :value ORDER BY f.id")
})
public class FilesProject extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="project_id")
	private UUID projectId;

	//bi-directional many-to-one association to File
	@ManyToOne
	private File file;

	public FilesProject() {
	}

	public UUID getProjectId() {
		return this.projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}


	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}