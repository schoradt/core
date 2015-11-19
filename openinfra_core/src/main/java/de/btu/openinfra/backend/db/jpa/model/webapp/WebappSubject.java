package de.btu.openinfra.backend.db.jpa.model.webapp;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the webapp_subject database table.
 *
 */
@Entity
@Table(name="webapp_subject")
@NamedQuery(name="WebappSubject.findAll", query="SELECT w FROM WebappSubject w")
public class WebappSubject extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	private String data;

	@Column(name="subject_id")
	private UUID subjectId;

	//bi-directional many-to-one association to Webapp
	@ManyToOne
	private Webapp webapp;

	public WebappSubject() {
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UUID getSubjectId() {
		return this.subjectId;
	}

	public void setSubjectId(UUID subjectId) {
		this.subjectId = subjectId;
	}

	public Webapp getWebapp() {
		return this.webapp;
	}

	public void setWebapp(Webapp webapp) {
		this.webapp = webapp;
	}

}