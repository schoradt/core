package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the role_permissions database table.
 * 
 */
@Entity
@Table(name="role_permissions")
@NamedQueries({
	@NamedQuery(name="RolePermission.findAll", 
			query="SELECT r FROM RolePermission r"),
	@NamedQuery(name="RolePermission.count", 
			query="SELECT COUNT(r) FROM RolePermission r")
})
public class RolePermission extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Permission
	@ManyToOne
	@JoinColumn(name="permission")
	private Permission permissionBean;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role")
	private Role roleBean;

	public RolePermission() {
	}

	public Permission getPermissionBean() {
		return this.permissionBean;
	}

	public void setPermissionBean(Permission permissionBean) {
		this.permissionBean = permissionBean;
	}

	public Role getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}

}