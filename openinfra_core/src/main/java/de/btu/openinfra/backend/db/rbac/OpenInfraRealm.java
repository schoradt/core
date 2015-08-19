package de.btu.openinfra.backend.db.rbac;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;

import de.btu.openinfra.backend.db.daos.rbac.SubjectDao;
import de.btu.openinfra.backend.db.jpa.model.rbac.RolePermission;
import de.btu.openinfra.backend.db.jpa.model.rbac.Subject;
import de.btu.openinfra.backend.db.jpa.model.rbac.SubjectRole;

public class OpenInfraRealm extends AuthorizingRealm {
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		Subject s = new SubjectDao().readModel(upt.getUsername());
		return new SimpleAuthenticationInfo(
				new SimplePrincipalCollection(
						s.getLogin(), 
						OpenInfraRealmNames.LOGIN.name()), 
						s.getPassword());
	}
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
		// Retrieve the login from principals (there might be multiple)
		List<String> login = new LinkedList<String>();
		for(Object o : principals.fromRealm(OpenInfraRealmNames.LOGIN.name())) {
			login.add(o.toString());
		}
		
        Subject s = new SubjectDao().readModel(login.get(0));
        
        Set<String> roleNames = new HashSet<String>();
        Set<String> permissions = new HashSet<String>();
        
        for(SubjectRole sr : s.getSubjectRoles()) {
        	roleNames.add(sr.getRoleBean().getName());
        	for(RolePermission rp : sr.getRoleBean().getRolePermissions()) {
        		permissions.add(rp.getPermissionBean().getPermission());
        	}
        }
        		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
	}

}
