package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
			query="SELECT r FROM RolePermission r ORDER BY r.id"),
	@NamedQuery(name="RolePermission.count",
			query="SELECT COUNT(r) FROM RolePermission r")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="RolePermission.findAllByLocaleAndOrder",
            query="SELECT rp.*, rp.xmin "
                    + "FROM role_permissions AS rp "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM role) AS sq1 "
                    + "ON (rp.role_id = sq1.id) "
                    + "LEFT OUTER JOIN ("
                        + "SELECT * FROM permission) AS sq2 "
                    + "ON (rp.permission_id = sq2.id) "
                    + "ORDER BY %s ",
                resultClass=RolePermission.class)
})
public class RolePermission extends OpenInfraModelObject
	implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Permission
	@ManyToOne
	@JoinColumn(name="permission_id")
	private Permission permissionBean;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
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