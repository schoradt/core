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
 * The persistent class for the user_roles database table.
 * 
 */
@Entity
@Table(name="subject_roles")
@NamedQuery(name="SubjectRole.findAll", query="SELECT s FROM Subject s")
public class SubjectRole implements Serializable, OpenInfraModelObject {
	private static final long serialVersionUID = 1L;

	@Id
	private UUID id;
	
	private Integer xmin;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role")
	private Role roleBean;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user")
	private Subject userBean;

	public SubjectRole() {
	}

	@Override
	public UUID getId() {
		return this.id;
	}

	@Override
	public void setId(UUID id) {
		this.id = id;
	}

	public Role getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}

	public Subject getUserBean() {
		return this.userBean;
	}

	public void setUserBean(Subject userBean) {
		this.userBean = userBean;
	}
	
	@Override
	public Integer getXmin() {
		return xmin;
	}

}