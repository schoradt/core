/**
 * 
 * This package contains classes of the role-based access control system (RBAC). 
 * These classes build a security layer which should be accessed by REST 
 * resources. RBAC classes use a checkPermission method and bypass the request 
 * to the underlying DAO classes when the authenticated subject provides the
 * required permissions.
 * 
 * This package mostly contains DAO classes. However, the class 'OpenInfraRealm'
 * implements the user management for Apache Shiro.
 * 
 * @author <a href="http://www.b-tu.de">BTU</a> DBIS
 *
 */
package de.btu.openinfra.backend.db.rbac;