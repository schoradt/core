package de.btu.openinfra.backend.db.daos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class RolePermissionDao extends OpenInfraPojo {

	private RoleDao role;
	private PermissionDao permission;
	
	public RoleDao getRole() {
		return role;
	}
	public void setRole(RoleDao role) {
		this.role = role;
	}
	public PermissionDao getPermission() {
		return permission;
	}
	public void setPermission(PermissionDao permission) {
		this.permission = permission;
	}

}