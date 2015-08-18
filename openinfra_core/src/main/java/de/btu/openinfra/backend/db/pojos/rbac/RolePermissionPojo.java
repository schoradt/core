package de.btu.openinfra.backend.db.pojos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class RolePermissionPojo extends OpenInfraPojo {

	private RolePojo role;
	private PermissionPojo permission;
	
	public RolePermissionPojo() {}
	
	public RolePermissionPojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}
	
	public RolePojo getRole() {
		return role;
	}
	public void setRole(RolePojo role) {
		this.role = role;
	}
	public PermissionPojo getPermission() {
		return permission;
	}
	public void setPermission(PermissionPojo permission) {
		this.permission = permission;
	}

}