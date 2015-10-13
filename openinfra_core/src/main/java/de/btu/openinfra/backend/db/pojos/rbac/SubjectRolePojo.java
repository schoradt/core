package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectRolePojo extends OpenInfraPojo {

	private UUID role;
	private UUID subject;
	
	public SubjectRolePojo() {}
	
	public SubjectRolePojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}

	public UUID getRole() {
		return role;
	}

	public void setRole(UUID role) {
		this.role = role;
	}

	public UUID getSubject() {
		return subject;
	}

	public void setSubject(UUID subject) {
		this.subject = subject;
	}

}