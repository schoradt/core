package de.btu.openinfra.backend.db.daos.rbac;

import javax.xml.bind.annotation.XmlRootElement;

import de.btu.openinfra.backend.db.pojos.OpenInfraPojo;

@XmlRootElement
public class UserRoleDao extends OpenInfraPojo {

	private RoleDao role;

	private UserDao user;

	public RoleDao getRole() {
		return role;
	}

	public void setRole(RoleDao role) {
		this.role = role;
	}

	public UserDao getUser() {
		return user;
	}

	public void setUser(UserDao user) {
		this.user = user;
	}
	
	


}