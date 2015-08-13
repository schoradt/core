package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the password_blacklist database table.
 * 
 */
@Entity
@Table(name="password_blacklist")
@NamedQuery(name="PasswordBlacklist.findAll", query="SELECT p FROM PasswordBlacklist p")
public class PasswordBlacklist implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	private String password;

	public PasswordBlacklist() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}