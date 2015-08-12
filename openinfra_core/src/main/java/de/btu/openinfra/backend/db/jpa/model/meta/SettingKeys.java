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
 * The persistent class for the setting_keys database table.
 * 
 */
@Entity
@Table(schema="meta_data", name="setting_keys")
@NamedQuery(name="SettingKey.findAll", query="SELECT s FROM SettingKeys s")
public class SettingKeys implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	private String key;

	//bi-directional many-to-one association to Setting
	@OneToMany(mappedBy="settingKey")
	private List<Settings> settings;

	public SettingKeys() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<Settings> getSettings() {
		return this.settings;
	}

	public void setSettings(List<Settings> settings) {
		this.settings = settings;
	}

	public Settings addSetting(Settings settings) {
		getSettings().add(settings);
		settings.setSettingKey(this);

		return settings;
	}

	public Settings removeSetting(Settings settings) {
		getSettings().remove(settings);
		settings.setSettingKey(null);

		return settings;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}