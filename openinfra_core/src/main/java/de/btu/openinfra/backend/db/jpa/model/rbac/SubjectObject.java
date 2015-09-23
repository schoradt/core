package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the subject_objects database table.
 * 
 */
@Entity
@Table(name="subject_objects")
@NamedQuery(name="SubjectObject.findAll", query="SELECT s FROM SubjectObject s")
public class SubjectObject extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="project_id")
	private UUID projectId;

	//bi-directional many-to-one association to Object
	@ManyToOne
	@JoinColumn(name="object")
	private Object objectBean;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subject")
	private Subject subjectBean;

	public SubjectObject() {
	}

	public UUID getProjectId() {
		return this.projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public Object getObjectBean() {
		return this.objectBean;
	}

	public void setObjectBean(Object objectBean) {
		this.objectBean = objectBean;
	}

	public Subject getSubjectBean() {
		return this.subjectBean;
	}

	public void setSubjectBean(Subject subjectBean) {
		this.subjectBean = subjectBean;
	}

}