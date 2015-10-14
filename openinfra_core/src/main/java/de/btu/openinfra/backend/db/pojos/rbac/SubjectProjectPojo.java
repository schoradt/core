package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectProjectPojo extends OpenInfraPojo {
	
	private UUID projectId;
	private UUID projectRelatedRole;
	private UUID subject;
	
	public SubjectProjectPojo() {}
	
	public SubjectProjectPojo(OpenInfraModelObject model) {
		super(model);
	}

	public UUID getProjectId() {
		return projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public UUID getProjectRelatedRole() {
		return projectRelatedRole;
	}

	public void setProjectRelatedRole(UUID projectRelatedRole) {
		this.projectRelatedRole = projectRelatedRole;
	}

	public UUID getSubject() {
		return subject;
	}

	public void setSubject(UUID subject) {
		this.subject = subject;
	}

}
