package de.btu.openinfra.backend.db.jpa.model.meta;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
            query="SELECT s FROM Settings s INNER JOIN s.settingKey sk WHERE "
                    + " sk = :value"),
    @NamedQuery(name="Settings.count",
    	query="SELECT COUNT(s) FROM Settings s")
})
public class Settings extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String value;

	//bi-directional many-to-one association to SettingKey
    @ManyToOne
    @JoinColumn(name="key")
    private SettingKeys settingKey;

	@Column(name="updated_on")
	private Timestamp updatedOn;
	
	@ManyToOne
	@JoinColumn(name = "project")
	private Projects project;

	public Settings() {
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public SettingKeys getSettingKey() {
        return this.settingKey;
    }

    public void setSettingKey(SettingKeys settingKey) {
        this.settingKey = settingKey;
    }

	public Timestamp getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

}