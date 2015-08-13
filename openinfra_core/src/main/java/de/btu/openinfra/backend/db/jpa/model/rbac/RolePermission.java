package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the role_permissions database table.
 * 
 */
@Entity
@Table(name="role_permissions")
@NamedQuery(name="RolePermission.findAll", query="SELECT r FROM RolePermission r")
public class RolePermission implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

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

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
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
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}