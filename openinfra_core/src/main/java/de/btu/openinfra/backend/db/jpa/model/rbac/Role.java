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
 * The persistent class for the role database table.
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r"),
	@NamedQuery(name="Role.count", query="SELECT COUNT(r) FROM Role r")
})
@NamedNativeQueries({
    @NamedNativeQuery(name="Role.findAllByLocaleAndOrder",
            query="SELECT *, xmin "
                    + "FROM role "
                    + "ORDER BY %s ",
                resultClass=Role.class)
})
public class Role extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private String description;

	private String name;

	//bi-directional many-to-one association to RolePermission
	@OneToMany(mappedBy="roleBean")
	private List<RolePermission> rolePermissions;

	//bi-directional many-to-one association to SubjectRole
	@OneToMany(mappedBy="roleBean")
	private List<SubjectRole> subjectRoles;

	public Role() {
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<RolePermission> getRolePermissions() {
		return this.rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public RolePermission addRolePermission(RolePermission rolePermission) {
		getRolePermissions().add(rolePermission);
		rolePermission.setRoleBean(this);

		return rolePermission;
	}

	public RolePermission removeRolePermission(RolePermission rolePermission) {
		getRolePermissions().remove(rolePermission);
		rolePermission.setRoleBean(null);

		return rolePermission;
	}

	public List<SubjectRole> getSubjectRoles() {
		return this.subjectRoles;
	}

	public void setSubjectRoles(List<SubjectRole> subjectRoles) {
		this.subjectRoles = subjectRoles;
	}

	public SubjectRole addSubjectRole(SubjectRole subjectRole) {
		getSubjectRoles().add(subjectRole);
		subjectRole.setRoleBean(this);

		return subjectRole;
	}

	public SubjectRole removeSubjectRole(SubjectRole subjectRole) {
		getSubjectRoles().remove(subjectRole);
		subjectRole.setRoleBean(null);

		return subjectRole;
	}

}