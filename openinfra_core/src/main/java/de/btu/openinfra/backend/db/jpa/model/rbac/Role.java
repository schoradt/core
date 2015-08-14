package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

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

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
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
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}