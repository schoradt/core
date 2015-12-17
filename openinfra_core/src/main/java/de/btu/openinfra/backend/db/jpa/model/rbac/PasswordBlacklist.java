package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the password_blacklist database table.
 *
 */
@Entity
@Table(name="password_blacklist")
@NamedQueries({
	@NamedQuery(name="PasswordBlacklist.findAll",
		query="SELECT p FROM PasswordBlacklist p ORDER BY p.id"),
	@NamedQuery(name="PasswordBlacklist.count",
		query="SELECT COUNT(p) FROM PasswordBlacklist p")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="PasswordBlacklist.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM password_blacklist "
                    + "ORDER BY %s ",
                resultClass=PasswordBlacklist.class)
})
public class PasswordBlacklist extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	private String password;

	public PasswordBlacklist() {
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}