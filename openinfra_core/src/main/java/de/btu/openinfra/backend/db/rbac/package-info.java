/**
 * 
 * This package contains classes of the role-based access control system (RBAC). 
 * These classes build a security layer which should be accessed by REST 
 * resources. RBAC classes use a checkPermission method and bypass the request 
 * to the underlying DAO classes when the authenticated subject provides the
 * required permissions.
 * 
 * This package mostly contains RBAC classes. However, the class 
 * 'OpenInfraRealm' implements the user management for Apache Shiro. Moreover, 
 * the class 'ProjectRbac' contains a checkPermission method which is mostly 
 * called by all classes to secure the access of the underlying DAO class.
 * The enumeration 'OpenInfraHttpMethod' maps ordinary HTTP methods to access
 * string used in the checkPermission method.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.db.rbac;