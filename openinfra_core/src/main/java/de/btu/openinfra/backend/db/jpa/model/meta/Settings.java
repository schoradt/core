package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the settings database table.
 * 
 */
@Entity
@Table(schema="meta_data")
@NamedQueries({
    @NamedQuery(name="Settings.findAll",
            query="SELECT s FROM Settings s"),
    @NamedQuery(name="Settings.findByKey",
            query="SELECT s FROM Settings s WHERE s.key = :value"),
    @NamedQuery(name="Settings.count",
    	query="SELECT COUNT(s) FROM Settings s")
})
public class Settings implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;

	private String value;

	private String key;

	@Column(name="updated_on")
	private Date updatedOn;

	//bi-directional many-to-one association to Projects
	@OneToMany(mappedBy="setting")
	private List<Projects> projects;

	public Settings() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<Projects> getProjects() {
		return this.projects;
	}

	public void setProjects(List<Projects> projects) {
		this.projects = projects;
	}

	public Projects addProject(Projects project) {
		getProjects().add(project);
		project.setSetting(this);

		return project;
	}

	public Projects removeProject(Projects project) {
		getProjects().remove(project);
		project.setSetting(null);

		return project;
	}

}