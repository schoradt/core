package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectObjectPojo extends OpenInfraPojo {
	
	private UUID projectId;
	private OpenInfraObjectPojo object;
	private SubjectPojo subject;
	private boolean writeObject;
	private UUID objectId;
	
	public SubjectObjectPojo() {}
	
	public SubjectObjectPojo(SubjectObject model) {
		super(model);
	}
	
	public UUID getProjectId() {
		return projectId;
	}
	
	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}
	
	public OpenInfraObjectPojo getObject() {
		return object;
	}
	
	public void setObject(OpenInfraObjectPojo object) {
		this.object = object;
	}
	
	public SubjectPojo getSubject() {
		return subject;
	}
	
	public void setSubject(SubjectPojo subject) {
		this.subject = subject;
	}

	public boolean getWriteObject() {
		return writeObject;
	}

	public void setWriteObject(boolean writeObject) {
		this.writeObject = writeObject;
	}

	public UUID getObjectId() {
		return objectId;
	}

	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}

}
