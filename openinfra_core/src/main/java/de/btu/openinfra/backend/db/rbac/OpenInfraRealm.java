package de.btu.openinfra.backend.db.rbac;

import javax.sql.DataSource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;

import de.btu.openinfra.backend.db.daos.rbac.SubjectDao;

public class OpenInfraRealm extends JdbcRealm {

	@Override
	public void setDataSource(DataSource dataSource) {
		System.out.println("hier: " + dataSource );
		super.setDataSource(dataSource);
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken upt = (UsernamePasswordToken)token;
		System.out.println("test");
		SubjectDao s = new SubjectDao();
		System.out.println("hier: " +  upt.getUsername());
		System.out.println("da: " + s.read(upt.getUsername()));
		System.out.println("sondt: " );
		
		return new SimpleAuthenticationInfo(new SimplePrincipalCollection("root", "root"), "root");
		
	}

	
//	@Override
//	protected Set<String> getRoleNamesForUser(Connection conn, String username)
//			throws SQLException {
//		System.out.println("roles");
//		Set<String> roles = new HashSet<String>();
//		try {
//			for(SubjectRole sr : 
//				new SubjectDao().read(username).getSubjectRoles()) {
//				 roles.add(sr.getRoleBean().getName());
//				 System.out.println(sr.getRoleBean().getName());
//			}			
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
//		return roles;
//	}

}
