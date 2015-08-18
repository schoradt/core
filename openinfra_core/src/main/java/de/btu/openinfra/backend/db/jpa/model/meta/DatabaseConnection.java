package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the database_connection database table.
 * 
 */
@Entity
@Table(name="database_connection", schema="meta_data")
@NamedQueries({
	@NamedQuery(name="DatabaseConnection.findAll",
		query="SELECT d FROM DatabaseConnection d"),
    @NamedQuery(name="DatabaseConnection.count",
    	query="SELECT COUNT(d) FROM DatabaseConnection d")
})
public class DatabaseConnection extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Credentials
	@ManyToOne
	@JoinColumn(name="credentials")
	private Credentials credential;

	//bi-directional many-to-one association to Databases
	@ManyToOne
	@JoinColumn(name="database")
	private Databases databaseBean;

	//bi-directional many-to-one association to Ports
	@ManyToOne
	@JoinColumn(name="port")
	private Ports portBean;

	//bi-directional many-to-one association to Schemas
	@ManyToOne
	@JoinColumn(name="schema")
	private Schemas schemaBean;

	//bi-directional many-to-one association to Servers
	@ManyToOne
	@JoinColumn(name="server")
	private Servers serverBean;

	public DatabaseConnection() {
	}

	public Credentials getCredential() {
		return this.credential;
	}

	public void setCredential(Credentials credential) {
		this.credential = credential;
	}

	public Databases getDatabaseBean() {
		return this.databaseBean;
	}

	public void setDatabaseBean(Databases databaseBean) {
		this.databaseBean = databaseBean;
	}

	public Ports getPortBean() {
		return this.portBean;
	}

	public void setPortBean(Ports portBean) {
		this.portBean = portBean;
	}

	public Schemas getSchemaBean() {
		return this.schemaBean;
	}

	public void setSchemaBean(Schemas schemaBean) {
		this.schemaBean = schemaBean;
	}

	public Servers getServerBean() {
		return this.serverBean;
	}

	public void setServerBean(Servers serverBean) {
		this.serverBean = serverBean;
	}

}