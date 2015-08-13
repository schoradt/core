package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the role_permissions database table.
 * 
 */
@Entity
@Table(name="role_permissions")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

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

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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