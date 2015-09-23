package de.btu.openinfra.backend.db.pojos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.rbac.OpenInfraObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class OpenInfraObjectPojo extends OpenInfraPojo {

	private String description;
	private String name;
	
	public OpenInfraObjectPojo() {}
	
	public OpenInfraObjectPojo(OpenInfraObject model) {
		super(model);
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
