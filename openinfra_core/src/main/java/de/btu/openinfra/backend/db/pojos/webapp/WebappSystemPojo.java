package de.btu.openinfra.backend.db.pojos.webapp;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.webapp.WebappSystem;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class WebappSystemPojo extends OpenInfraPojo {

	private String data;
	private UUID webapp;

	public WebappSystemPojo() {}

	public WebappSystemPojo(WebappSystem model) {
		super(model);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public UUID getWebapp() {
		return webapp;
	}

	public void setWebapp(UUID webapp) {
		this.webapp = webapp;
	}

}
