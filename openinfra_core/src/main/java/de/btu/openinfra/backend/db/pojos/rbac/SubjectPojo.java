package de.btu.openinfra.backend.db.pojos.rbac;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class SubjectPojo extends OpenInfraPojo {

	private String createdOn;

	private String defaultLanguage;

	private String description;

	private String lastLoginOn;

	private String login;

	private String mail;

	private String name;

	private String password;

	private String passwordCreatedOn;

	/**
	 * The status:
	 * -1 blocked
	 * 0  inactive
	 * 1  active
	 */
	private Integer status;

	private String updatedOn;
	
	private List<RolePojo> roles;
	
	private List<SubjectProjectPojo> projects;
	
	private String webApp;
	
	public SubjectPojo() {}
	
	public SubjectPojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}

	public String getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(String createdOn) {
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

	public String getLastLoginOn() {
		return this.lastLoginOn;
	}

	public void setLastLoginOn(String lastLoginOn) {
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

	public String getPasswordCreatedOn() {
		return this.passwordCreatedOn;
	}

	public void setPasswordCreatedOn(String passwordCreatedOn) {
		this.passwordCreatedOn = passwordCreatedOn;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public List<RolePojo> getRoles() {
		return roles;
	}

	public void setRoles(List<RolePojo> roles) {
		this.roles = roles;
	}

	public List<SubjectProjectPojo> getProjects() {
		return projects;
	}

	public void setProjects(List<SubjectProjectPojo> projects) {
		this.projects = projects;
	}

	public String getWebApp() {
		return webApp;
	}

	public void setWebApp(String webApp) {
		this.webApp = webApp;
	}

}