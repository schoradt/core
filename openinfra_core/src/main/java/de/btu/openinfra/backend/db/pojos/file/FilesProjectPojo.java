package de.btu.openinfra.backend.db.pojos.file;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.file.FilesProject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class FilesProjectPojo extends OpenInfraPojo {

	private UUID file;
	private UUID project;

	public FilesProjectPojo() {}

	public FilesProjectPojo(FilesProject model) {
		super(model);
	}

	public UUID getFile() {
		return file;
	}

	public void setFile(UUID file) {
		this.file = file;
	}

	public UUID getProject() {
		return project;
	}

	public void setProject(UUID project) {
		this.project = project;
	}

}
