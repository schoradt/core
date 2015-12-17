package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the permission database table.
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Permission.findAll",
	        query="SELECT p FROM Permission p ORDER BY p.id"),
	@NamedQuery(name="Permission.count",
		query="SELECT COUNT(p) FROM Permission p")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="Permission.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM permission "
                    + "ORDER BY %s ",
                resultClass=Permission.class)
})
public class Permission extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	private String permission;

	//bi-directional many-to-one association to RolePermission
	@OneToMany(mappedBy="permissionBean")
	private List<RolePermission> rolePermissions;

	public Permission() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPermission() {
		return this.permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public RolePermission addRolePermission(RolePermission rolePermission) {
		getRolePermissions().add(rolePermission);
		rolePermission.setPermissionBean(this);

		return rolePermission;
	}

	public RolePermission removeRolePermission(RolePermission rolePermission) {
		getRolePermissions().remove(rolePermission);
		rolePermission.setPermissionBean(null);

		return rolePermission;
	}

}