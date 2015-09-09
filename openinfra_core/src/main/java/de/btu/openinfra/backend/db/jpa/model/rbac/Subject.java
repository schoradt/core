package de.btu.openinfra.backend.db.jpa.model.rbac;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;


/**
 * The persistent class for the subject database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Subject.findAll", query="SELECT s FROM Subject s"),
	@NamedQuery(name="Subject.findByLogin", 
		query="SELECT s "
				+ "FROM Subject s "
				+ "WHERE s.login = :login")
})
//@NamedNativeQueries({
//	@NamedNativeQuery(name="Subject.findByLogin", 
//			query="select *,xmin from subject where login = ?")
//})
public class Subject extends OpenInfraModelObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="created_on")
	private Timestamp createdOn;

	@Column(name="default_language")
	private String defaultLanguage;

	private String description;

	@Column(name="last_login_on")
	private Timestamp lastLoginOn;

	private String login;

	private String mail;

	private String name;

	private String password;

	@Column(name="password_created_on")
	private Timestamp passwordCreatedOn;

	private UUID salt;

	private Integer status;

	@Column(name="updated_on")
	private Timestamp updatedOn;

	//bi-directional many-to-one association to SubjectRole
	@OneToMany(mappedBy="subject")
	private List<SubjectRole> subjectRoles;

	public Subject() {
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getDefaultLanguage() {
		return this.defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastLoginOn() {
		return this.lastLoginOn;
	}

	public void setLastLoginOn(Timestamp lastLoginOn) {
		this.lastLoginOn = lastLoginOn;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getPasswordCreatedOn() {
		return this.passwordCreatedOn;
	}

	public void setPasswordCreatedOn(Timestamp passwordCreatedOn) {
		this.passwordCreatedOn = passwordCreatedOn;
	}

	public UUID getSalt() {
		return this.salt;
	}

	public void setSalt(UUID salt) {
		this.salt = salt;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<SubjectRole> getSubjectRoles() {
		return this.subjectRoles;
	}

	public void setSubjectRoles(List<SubjectRole> subjectRoles) {
		this.subjectRoles = subjectRoles;
	}

	public SubjectRole addSubjectRole(SubjectRole subjectRole) {
		getSubjectRoles().add(subjectRole);
		subjectRole.setSubject(this);

		return subjectRole;
	}

	public SubjectRole removeSubjectRole(SubjectRole subjectRole) {
		getSubjectRoles().remove(subjectRole);
		subjectRole.setSubject(null);

		return subjectRole;
	}

}