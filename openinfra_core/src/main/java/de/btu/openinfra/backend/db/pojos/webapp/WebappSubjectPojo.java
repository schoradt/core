package de.btu.openinfra.backend.db.pojos.webapp;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.webapp.WebappSubject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class WebappSubjectPojo extends OpenInfraPojo {

	private String data;
	private UUID subject;
	private UUID webapp;

	public WebappSubjectPojo() {}

	public WebappSubjectPojo(WebappSubject model) {
		super(model);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UUID getSubject() {
		return subject;
	}

	public void setSubject(UUID subject) {
		this.subject = subject;
	}

	public UUID getWebapp() {
		return webapp;
	}

	public void setWebapp(UUID webapp) {
		this.webapp = webapp;
	}

}
