package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the subject_roles database table.
 * 
 */
@Entity
@Table(name="subject_roles")
@NamedQuery(name="SubjectRole.findAll", query="SELECT s FROM SubjectRole s")
public class SubjectRole extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role")
	private Role roleBean;

	//bi-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subject")
	private Subject subjectBean;

	public SubjectRole() {
	}

	public Role getRoleBean() {
		return this.roleBean;
	}

	public void setRoleBean(Role roleBean) {
		this.roleBean = roleBean;
	}

	public Subject getSubjectBean() {
		return this.subjectBean;
	}

	public void setSubjectBean(Subject subjectBean) {
		this.subjectBean = subjectBean;
	}

}