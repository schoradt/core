package de.btu.openinfra.backend.db.jpa.model.webapp;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the webapp_system database table.
 *
 */
@Entity
@Table(name="webapp_system")
@NamedQuery(name="WebappSystem.findAll", query="SELECT w FROM WebappSystem w")
public class WebappSystem extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String data;

	//bi-directional many-to-one association to Webapp
	@ManyToOne
	private Webapp webapp;

	public WebappSystem() {
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Webapp getWebapp() {
		return this.webapp;
	}

	public void setWebapp(Webapp webapp) {
		this.webapp = webapp;
	}

}