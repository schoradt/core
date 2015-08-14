package de.btu.openinfra.backend.db.pojos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.jpa.model.OpenInfraModelObject;
import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class UserRolePojo extends OpenInfraPojo {

	private RolePojo role;

	private UserPojo user;
	
	public UserRolePojo() {}
	
	public UserRolePojo(OpenInfraModelObject modelObject) {
		super(modelObject);
	}

	public RolePojo getRole() {
		return role;
	}

	public void setRole(RolePojo role) {
		this.role = role;
	}

	public UserPojo getUser() {
		return user;
	}

	public void setUser(UserPojo user) {
		this.user = user;
	}
	
	


}