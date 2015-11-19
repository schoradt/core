package de.btu.openinfra.backend.db.jpa.model.webapp;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the webapp database table.
 *
 */
@Entity
@NamedQuery(name="Webapp.findAll", query="SELECT w FROM Webapp w")
public class Webapp extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String data;

	private String description;

	private String name;

	//bi-directional many-to-one association to WebappProject
	@OneToMany(mappedBy="webapp")
	private List<WebappProject> webappProjects;

	public Webapp() {}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WebappProject> getWebappProjects() {
		return this.webappProjects;
	}

	public void setWebappProjects(List<WebappProject> webappProjects) {
		this.webappProjects = webappProjects;
	}

	public WebappProject addWebappProject(WebappProject webappProject) {
		getWebappProjects().add(webappProject);
		webappProject.setWebapp(this);

		return webappProject;
	}

	public WebappProject removeWebappProject(WebappProject webappProject) {
		getWebappProjects().remove(webappProject);
		webappProject.setWebapp(null);

		return webappProject;
	}

}