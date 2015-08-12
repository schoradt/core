package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the schemas database table.
 * 
 */
@Entity
@Table(schema="meta_data")
@NamedQuery(name="Schemas.findAll", query="SELECT s FROM Schemas s")
public class Schemas implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	private String schema;

	//bi-directional many-to-one association to DatabaseConnection
	@OneToMany(mappedBy="schemaBean")
	private List<DatabaseConnection> databaseConnections;

	public Schemas() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public List<DatabaseConnection> getDatabaseConnections() {
		return this.databaseConnections;
	}

	public void setDatabaseConnections(List<DatabaseConnection> databaseConnections) {
		this.databaseConnections = databaseConnections;
	}

	public DatabaseConnection addDatabaseConnection(DatabaseConnection databaseConnection) {
		getDatabaseConnections().add(databaseConnection);
		databaseConnection.setSchemaBean(this);

		return databaseConnection;
	}

	public DatabaseConnection removeDatabaseConnection(DatabaseConnection databaseConnection) {
		getDatabaseConnections().remove(databaseConnection);
		databaseConnection.setSchemaBean(null);

		return databaseConnection;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}