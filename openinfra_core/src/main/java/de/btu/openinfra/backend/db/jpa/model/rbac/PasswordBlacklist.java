package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the password_blacklist database table.
 * 
 */
@Entity
@Table(name="password_blacklist")
@NamedQuery(name="PasswordBlacklist.findAll", query="SELECT p FROM PasswordBlacklist p")
public class PasswordBlacklist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String password;

	public PasswordBlacklist() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}