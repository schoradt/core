package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the subject_objects database table.
 *
 */
@Entity
@Table(name="subject_objects")
@NamedQueries({
	@NamedQuery(name="SubjectObject.findAll",
			query="SELECT s FROM SubjectObject s"),
	@NamedQuery(name="SubjectObject.findBySubject",
			query="SELECT s FROM SubjectObject s "
					+ "WHERE s.subjectBean = :value"),
	@NamedQuery(name="SubjectObject.findBySubjectAndProject",
			query="SELECT s FROM SubjectObject s "
					+ "WHERE s.subjectBean.id = :subjectId "
					+ "AND s.projectId = :projectId "),
	@NamedQuery(name="SubjectObject.count",
			query="SELECT COUNT(s) FROM SubjectObject s")
})

public class SubjectObject extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="object_id")
	private UUID objectId;

	@Column(name="object_write")
	private Boolean objectWrite;

	@Column(name="project_id")
	private UUID projectId;

	//bi-directional many-to-one association to Object
	@ManyToOne
	@JoinColumn(name="openinfra_objects_id")
	private OpenInfraObject objectBean;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subject_id")
	private Subject subjectBean;

	public SubjectObject() {
	}

	public UUID getProjectId() {
		return this.projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public OpenInfraObject getObjectBean() {
		return this.objectBean;
	}

	public void setObjectBean(OpenInfraObject objectBean) {
		this.objectBean = objectBean;
	}

	public Subject getSubjectBean() {
		return this.subjectBean;
	}

	public void setSubjectBean(Subject subjectBean) {
		this.subjectBean = subjectBean;
	}

	public UUID getObjectId() {
		return this.objectId;
	}

	public void setObjectId(UUID objectId) {
		this.objectId = objectId;
	}

	public Boolean getObjectWrite() {
		return this.objectWrite;
	}

	public void setObjectWrite(Boolean objectWrite) {
		this.objectWrite = objectWrite;
	}

}