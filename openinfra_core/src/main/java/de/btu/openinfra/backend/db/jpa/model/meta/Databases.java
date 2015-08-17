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
 * The persistent class for the databases database table.
 * 
 */
@Entity
@Table(schema="meta_data")
@NamedQuery(name="Databases.findAll", query="SELECT d FROM Databases d")
public class Databases implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	private String database;

	//bi-directional many-to-one association to DatabaseConnection
	@OneToMany(mappedBy="databaseBean")
	private List<DatabaseConnection> databaseConnections;

	public Databases() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getDatabase() {
		return this.database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public List<DatabaseConnection> getDatabaseConnections() {
		return this.databaseConnections;
	}

	public void setDatabaseConnections(List<DatabaseConnection> databaseConnections) {
		this.databaseConnections = databaseConnections;
	}

	public DatabaseConnection addDatabaseConnection(DatabaseConnection databaseConnection) {
		getDatabaseConnections().add(databaseConnection);
		databaseConnection.setDatabaseBean(this);

		return databaseConnection;
	}

	public DatabaseConnection removeDatabaseConnection(DatabaseConnection databaseConnection) {
		getDatabaseConnections().remove(databaseConnection);
		databaseConnection.setDatabaseBean(null);

		return databaseConnection;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}