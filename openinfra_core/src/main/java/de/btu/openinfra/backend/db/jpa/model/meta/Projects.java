package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the projects database table.
 *
 */
@Entity
@Table(schema="meta_data")
@NamedQueries({
	@NamedQuery(name="Projects.findAll", query="SELECT p FROM Projects p"),
    @NamedQuery(name="Projects.count",
    	query="SELECT COUNT(p) FROM Projects p"),
	@NamedQuery(name="Projects.findByProject",
	    query="SELECT p FROM Projects p WHERE p.projectId = :value")
})
public class Projects extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="is_subproject")
	private Boolean isSubproject;

	@Column(name="project_id")
	private UUID projectId;

	//bi-directional many-to-one association to DatabaseConnection
	@ManyToOne
	@JoinColumn(name="database_connection_id")
	private DatabaseConnection databaseConnection;

	//bi-directional many-to-one association to Projects
    @OneToMany(mappedBy="project")
    private List<Settings> settings;

	public Projects() {
	}

	public Boolean getIsSubproject() {
		return this.isSubproject;
	}

	public void setIsSubproject(Boolean isSubproject) {
		this.isSubproject = isSubproject;
	}

	public UUID getProjectId() {
        return this.projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

	public DatabaseConnection getDatabaseConnection() {
		return this.databaseConnection;
	}

	public void setDatabaseConnection(DatabaseConnection databaseConnection) {
		this.databaseConnection = databaseConnection;
	}

    public List<Settings> getSettings() {
        return settings;
    }

    public void setSettings(List<Settings> settings) {
        this.settings = settings;
    }

    public Settings addSetting(Settings setting) {
        getSettings().add(setting);
        setting.setProject(this);

        return setting;
    }

    public Settings removeSetting(Settings setting) {
        getSettings().remove(setting);
        setting.setProject(null);

        return setting;
    }

}