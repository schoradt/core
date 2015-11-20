package de.btu.openinfra.backend.db.jpa.model.webapp;

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
 * The persistent class for the webapp_project database table.
 *
 */
@Entity
@Table(name="webapp_project")
@NamedQueries({
	@NamedQuery(name="WebappProject.findAll",
			query="SELECT w FROM WebappProject w"),
	@NamedQuery(name="WebappProject.findByWebapp",
			query="SELECT w "
					+ "FROM WebappProject w "
					+ "WHERE w.webapp = :value")
})
public class WebappProject extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	private String data;

	@Column(name="project_id")
	private UUID projectId;

	//bi-directional many-to-one association to Webapp
	@ManyToOne
	private Webapp webapp;

	public WebappProject() {}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UUID getProjectId() {
		return this.projectId;
	}

	public void setProjectId(UUID projectId) {
		this.projectId = projectId;
	}

	public Webapp getWebapp() {
		return this.webapp;
	}

	public void setWebapp(Webapp webapp) {
		this.webapp = webapp;
	}

}