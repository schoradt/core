package de.btu.openinfra.backend.db.pojos.rbac;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectPojo extends OpenInfraPojo {

	private Timestamp createdOn;

	private Locale defaultLanguage;

	private String description;

	private Timestamp lastLoginOn;

	private String login;

	private String mail;

	private String name;

	private String password;

	private Timestamp passwordCreatedOn;

	private Integer status;

	private Timestamp updatedOn;
	
	private List<RolePojo> roles;
	
	public SubjectPojo() {}
	
	public SubjectPojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}

	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Locale getDefaultLanguage() {
		return this.defaultLanguage;
	}

	public void setDefaultLanguage(Locale defaultLanguage) {
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

	public List<RolePojo> getRoles() {
		return roles;
	}

	public void setRoles(List<RolePojo> roles) {
		this.roles = roles;
	}

}