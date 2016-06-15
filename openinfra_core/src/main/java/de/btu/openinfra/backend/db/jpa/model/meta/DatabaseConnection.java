package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
		query="SELECT d FROM DatabaseConnection d ORDER BY d.id"),
    @NamedQuery(name="DatabaseConnection.count",
    	query="SELECT COUNT(d) FROM DatabaseConnection d")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="DatabaseConnection.findAllByLocaleAndOrder",
            query="SELECT dc.*, dc.xmin "
                    + "FROM database_connection AS dc "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM servers) AS sq1 "
                    + "ON (dc.server_id = sq1.id) "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM ports) AS sq2 "
                    + "ON (dc.port_id = sq2.id) "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM databases) AS sq3 "
                    + "ON (dc.database_id = sq3.id) "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM schemas) AS sq4 "
                    + "ON (dc.schema_id = sq4.id) "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM credentials) AS sq5 "
                    + "ON (dc.credentials_id = sq5.id) "
                    + "ORDER BY %s ",
                resultClass=DatabaseConnection.class)
})
public class DatabaseConnection extends OpenInfraModelObject
    implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Credentials
	@ManyToOne
	@JoinColumn(name="credentials_id")
	private Credentials credential;

	//bi-directional many-to-one association to Databases
	@ManyToOne
	@JoinColumn(name="database_id")
	private Databases databaseBean;

	//bi-directional many-to-one association to Ports
	@ManyToOne
	@JoinColumn(name="port_id")
	private Ports portBean;

	//bi-directional many-to-one association to Schemas
	@ManyToOne
	@JoinColumn(name="schema_id")
	private Schemas schemaBean;

	//bi-directional many-to-one association to Servers
	@ManyToOne
	@JoinColumn(name="server_id")
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