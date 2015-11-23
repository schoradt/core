package de.btu.openinfra.backend.db.pojos.webapp;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.webapp.WebappProject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class WebappProjectPojo extends OpenInfraPojo {

	private UUID webapp;
	private UUID project;
	private String data;

	public WebappProjectPojo() {}

	public WebappProjectPojo(WebappProject model) {
		super(model);
	}

	public UUID getWebapp() {
		return webapp;
	}

	public void setWebapp(UUID webapp) {
		this.webapp = webapp;
	}

	public UUID getProject() {
		return project;
	}

	public void setProject(UUID project) {
		this.project = project;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
