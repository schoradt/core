package de.btu.openinfra.backend.db.pojos.webapp;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.webapp.Webapp;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class WebappPojo extends OpenInfraPojo {

	private String data;
	private String ident;
	private String description;

	public WebappPojo() {}

	public WebappPojo(Webapp model) {
		super(model);
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
