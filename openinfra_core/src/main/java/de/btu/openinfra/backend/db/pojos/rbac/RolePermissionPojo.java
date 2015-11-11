package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class RolePermissionPojo extends OpenInfraPojo {

	private UUID role;
	private UUID permission;

	public RolePermissionPojo() {}

	public RolePermissionPojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}

	public UUID getRole() {
		return role;
	}

	public void setRole(UUID role) {
		this.role = role;
	}

	public UUID getPermission() {
		return permission;
	}

	public void setPermission(UUID permission) {
		this.permission = permission;
	}

}