package de.btu.openinfra.backend.db.rbac;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

/**
 * Example used:
 * <a href="http://www.jjoe64.com/2014/01/apache-shiro-with-hibernatesql-full.html"/>
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
public class OpenInfraSaltedAuthentificationInfo 
	implements AuthenticationInfo {
	
	private static final long serialVersionUID = 1L;
	
	private final String login;
	private final String password;

	public OpenInfraSaltedAuthentificationInfo(
			String login, 
			String password) {
		this.login = login;
		this.password = password;
	}
	
	@Override
	public Object getCredentials() {
		return password;
	}

	@Override
	public PrincipalCollection getPrincipals() {
		return new SimplePrincipalCollection(login, login);
	}

}
