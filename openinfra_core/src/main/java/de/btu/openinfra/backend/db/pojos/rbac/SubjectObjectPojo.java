package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectObjectPojo extends OpenInfraPojo {

	private UUID projectId;
	/**
	 * The openinfra object
	 */
	private UUID object;
	private UUID subject;
	private boolean writeObject;
	/**
	 * The id of the specific object: object = photo & objectId = which photo
	 */
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

	public UUID getObject() {
		return object;
	}

	public void setObject(UUID object) {
		this.object = object;
	}

	public UUID getSubject() {
		return subject;
	}

	public void setSubject(UUID subject) {
		this.subject = subject;
	}

	public boolean isWriteObject() {
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
